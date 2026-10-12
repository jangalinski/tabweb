package com.github.jangalinski.tabweb.gradle.buildlogic.site.navigation

import com.github.jangalinski.tabweb.gradle.buildlogic.site.navigation.schema.Element
import com.github.jangalinski.tabweb.gradle.buildlogic.site.navigation.schema.Section
import com.github.jangalinski.tabweb.gradle.buildlogic.site.navigation.schema.SectionEntry
import com.github.jangalinski.tabweb.gradle.buildlogic.site.navigation.schema.SectionEntryObject
import com.github.jangalinski.tabweb.gradle.buildlogic.site.navigation.schema.TabwebNavigationManifest
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.spec.KotlinFileSpec
import io.toolisticon.kotlin.generation.spec.KotlinFileSpecList
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry
import io.toolisticon.kotlin.generation.spi.context.KotlinCodeGenerationContextBase
import io.toolisticon.kotlin.generation.spi.registry.KotlinCodeGenerationSpiList
import io.toolisticon.kotlin.generation.spi.strategy.KotlinFileSpecListStrategy
import java.nio.file.Files
import java.nio.file.Path
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.put

private const val GENERATED_PACKAGE = "com.github.jangalinski.tabweb.site"
private const val GENERATED_NAME = "GeneratedSiteNavigation"

private val TABWEB_NAVIGATION = ClassName("com.github.jangalinski.tabweb.navigation", "TabwebNavigation")
private val NAVIGATION_ELEMENT = ClassName(
  "com.github.jangalinski.tabweb.navigation",
  "TabwebNavigationElement",
)
private val NAVIGATION_SECTION = ClassName(
  "com.github.jangalinski.tabweb.navigation",
  "TabwebNavigationSection",
)
private val URL = ClassName("com.github.jangalinski.tabweb._foundation", "Url")
private val TABWEB_ICON = ClassName("com.github.jangalinski.tabweb.icon", "TabwebIcon")

private val json = Json { ignoreUnknownKeys = true }

/** Generates the typed site navigation object from a navigation manifest file. */
@OptIn(ExperimentalKotlinPoetApi::class)
class TabwebNavigationGenerator : (Path) -> KotlinFileSpecList {
  private val registry: KotlinCodeGenerationSpiRegistry = KotlinCodeGenerationSpiList(
    TabwebNavigationStrategy(),
  ).registry()

  private val context = TabwebNavigationContext(registry)

  override fun invoke(input: Path): KotlinFileSpecList = KotlinCodeGeneration.generateFiles(
    context = context,
    input = NavigationSource(
      manifest = readManifest(input),
      manifestFile = input.toAbsolutePath().normalize(),
    ),
  )

  private fun readManifest(file: Path): TabwebNavigationManifest = decode(
    file,
    TabwebNavigationManifest.serializer(),
  )
}

private fun <T> decode(
  file: Path,
  serializer: kotlinx.serialization.DeserializationStrategy<T>,
): T {
  require(Files.isRegularFile(file)) { "Navigation file does not exist: $file" }
  return json.decodeFromJsonElement(serializer, normalizeSectionEntryVariants(
    Json.parseToJsonElement(Files.readString(file)),
  ))
}

internal data class NavigationSource(
  val manifest: TabwebNavigationManifest,
  val manifestFile: Path,
)

/** Generation context shared by navigation strategies. */
@OptIn(ExperimentalKotlinPoetApi::class)
class TabwebNavigationContext(
  registry: KotlinCodeGenerationSpiRegistry,
) : KotlinCodeGenerationContextBase<TabwebNavigationContext>(registry) {
  override val contextType = TabwebNavigationContext::class
}

/** Creates the generated navigation data object. */
@OptIn(ExperimentalKotlinPoetApi::class)
internal class TabwebNavigationStrategy : KotlinFileSpecListStrategy<TabwebNavigationContext, NavigationSource>(
  contextType = TabwebNavigationContext::class,
  inputType = NavigationSource::class,
) {
  override fun invoke(
    context: TabwebNavigationContext,
    input: NavigationSource,
  ): KotlinFileSpecList = KotlinFileGeneration.file(input)
}

@OptIn(ExperimentalKotlinPoetApi::class)
private object KotlinFileGeneration {
  fun file(source: NavigationSource): KotlinFileSpecList {
    val generatedObject = TypeSpec.objectBuilder(GENERATED_NAME)
      .addModifiers(KModifier.DATA)
      .addProperty(
        PropertySpec.builder("data", TABWEB_NAVIGATION)
          .initializer(navigationCode(source))
          .build(),
      )
      .build()

    return KotlinFileSpecList.of(
      KotlinFileSpec(
        FileSpec.builder(GENERATED_PACKAGE, GENERATED_NAME)
          .addType(generatedObject)
          .build(),
      ),
    )
  }

  private fun navigationCode(source: NavigationSource): CodeBlock = CodeBlock.builder()
    .add("%T(\n", TABWEB_NAVIGATION)
    .add("  root = %L,\n", listCode(source.manifest.elements) { elementCode(it, source.manifestFile) })
    .add(")")
    .build()

  private fun elementCode(element: Element, manifestFile: Path): CodeBlock = CodeBlock.builder()
    .add("%T(\n", NAVIGATION_ELEMENT)
    .add("  title = %S,\n", element.name)
    .apply {
      element.description?.let { add("  description = %S,\n", it) }
    }
    .add("  route = %T(%S),\n", URL, element.route)
    .apply {
      element.icon?.let { add("  icon = %T.%L,\n", TABWEB_ICON, it) }
      element.badgeRef?.let { add("  badgeRef = %S,\n", it) }
      element.columns?.let { if (it != 1) add("  columns = %L,\n", it) }
    }
    .add(
      "  children = %L,\n",
      listCode(element.elements.orEmpty()) { elementCode(it, manifestFile) },
    )
    .add(
      "  sections = %L,\n",
      listCode(element.sectionEntries.orEmpty().flatMap { sections(it, manifestFile) }) { sectionCode(it, manifestFile) },
    )
    .add(")")
    .build()

  private fun sections(entry: SectionEntry, manifestFile: Path): List<Section> = when (entry) {
    is SectionEntry.SectionVariant -> listOf(entry.value)
    is SectionEntry.ObjectVariant -> sections(entry.value, manifestFile)
  }

  private fun sections(include: SectionEntryObject, manifestFile: Path): List<Section> {
    val includedFile = manifestFile.parent.resolve(include.include).normalize()
    require(includedFile.startsWith(manifestFile.parent.normalize())) {
      "Navigation include '${include.include}' escapes the manifest directory."
    }
    return decode(
      includedFile,
      SectionsDocument.serializer(),
    ).sections
  }

  private fun sectionCode(section: Section, manifestFile: Path): CodeBlock = CodeBlock.builder()
    .add("%T(\n", NAVIGATION_SECTION)
    .add("  title = %S,\n", section.name)
    .add(
      "  elements = %L,\n",
      listCode(section.elements) { elementCode(it, manifestFile) },
    )
    .add(")")
    .build()

  private fun <T> listCode(items: List<T>, itemCode: (T) -> CodeBlock): CodeBlock = CodeBlock.builder()
    .add("listOf(\n")
    .apply { items.forEach { add("  %L,\n", itemCode(it)) } }
    .add(")")
    .build()
}

@Serializable
private data class SectionsDocument(
  val sections: List<Section>,
)

private fun normalizeSectionEntryVariants(element: JsonElement): JsonElement = when (element) {
  is JsonObject -> buildJsonObject {
    element.forEach { (name, value) ->
      if (name == "sections" && "route" in element) {
        put(
          name,
          JsonArray(
            value.jsonArray.map { sectionEntry ->
              val variant = if ("\u0024include" in sectionEntry.jsonObject) {
                SectionEntry.ObjectVariant.serializer().descriptor.serialName
              } else {
                SectionEntry.SectionVariant.serializer().descriptor.serialName
              }
              buildJsonObject {
                put("type", variant)
                put("value", sectionEntry)
              }
            },
          ),
        )
      } else {
        put(name, normalizeSectionEntryVariants(value))
      }
    }
  }

  is JsonArray -> JsonArray(element.map(::normalizeSectionEntryVariants))
  else -> element
}
