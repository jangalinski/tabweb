package com.github.jangalinski.tabweb.plugin.navigation

import com.github.jangalinski.tabweb.plugin.TabwebPlugin.Companion.JSON
import com.github.jangalinski.tabweb.plugin.navigation.schema.*
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.spec.KotlinFileSpecList
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry
import io.toolisticon.kotlin.generation.spi.registry.KotlinCodeGenerationSpiList
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import java.nio.file.Files
import java.nio.file.Path


/**
 * Generates a typed navigation object from a navigation manifest file.
 */
@OptIn(ExperimentalKotlinPoetApi::class)
class TabwebNavigationGenerator(
  private val packageName: String,
  private val objectName: String,
) : (Path) -> KotlinFileSpecList {

  companion object {
    internal const val PKG_NAVIGATION = "com.github.jangalinski.tabweb.navigation"
    internal val TABWEB_NAVIGATION = ClassName(PKG_NAVIGATION, "TabwebNavigation")
    internal val NAVIGATION_ELEMENT = ClassName(
      PKG_NAVIGATION,
      "TabwebNavigationElement",
    )
    internal val NAVIGATION_ELEMENTS = ClassName("kotlin.collections", "List")
      .parameterizedBy(NAVIGATION_ELEMENT)
    internal val ITERATOR = ClassName("kotlin.collections", "Iterator")
      .parameterizedBy(NAVIGATION_ELEMENT)
    internal val NAVIGATION_SECTION = ClassName(
      PKG_NAVIGATION,
      "TabwebNavigationSection",
    )
    internal val URL = ClassName("com.github.jangalinski.tabweb._foundation", "Url")
    internal val TABWEB_ICON = ClassName("com.github.jangalinski.tabweb.icon", "TabwebIcon")
  }

  private val registry: KotlinCodeGenerationSpiRegistry = KotlinCodeGenerationSpiList(
    TabwebNavigationStrategy(),
  ).registry()

  private val context = TabwebNavigationContext(registry)

  override fun invoke(input: Path): KotlinFileSpecList = KotlinCodeGeneration.generateFiles(
    context = context,
    input = NavigationSource(
      manifest = readManifest(input),
      manifestFile = input.toAbsolutePath().normalize(),
      packageName = packageName,
      objectName = objectName,
    ),
  )

  private fun readManifest(file: Path): TabwebNavigationManifest = decode(
    file,
    TabwebNavigationManifest.serializer(),
  )
}

internal fun <T> decode(
  file: Path,
  serializer: DeserializationStrategy<T>,
): T {
  require(Files.isRegularFile(file)) { "Navigation file does not exist: $file" }
  return JSON.decodeFromJsonElement(
    serializer, normalizeSectionEntryVariants(
      Json.parseToJsonElement(Files.readString(file)),
    )
  )
}

@Serializable
internal data class SectionsDocument(
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
