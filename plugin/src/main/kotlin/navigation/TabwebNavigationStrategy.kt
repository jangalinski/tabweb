package com.github.jangalinski.tabweb.plugin.navigation

import com.github.jangalinski.tabweb.plugin.navigation.schema.Element
import com.github.jangalinski.tabweb.plugin.navigation.schema.Section
import com.github.jangalinski.tabweb.plugin.navigation.schema.SectionEntry
import com.github.jangalinski.tabweb.plugin.navigation.schema.SectionEntryObject
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.buildCodeBlock
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildFun
import io.toolisticon.kotlin.generation.spec.KotlinFileSpecList
import io.toolisticon.kotlin.generation.spec.toFileSpec
import io.toolisticon.kotlin.generation.spi.strategy.KotlinFileSpecListStrategy
import java.nio.file.Path
import kotlin.collections.flatMap
import kotlin.collections.orEmpty

/**
 * Creates the generated navigation data object.
 */
@OptIn(ExperimentalKotlinPoetApi::class)
internal class TabwebNavigationStrategy : KotlinFileSpecListStrategy<TabwebNavigationContext, NavigationSource>(
  contextType = TabwebNavigationContext::class,
  inputType = NavigationSource::class,
) {

  override fun invoke(context: TabwebNavigationContext, input: NavigationSource, ): KotlinFileSpecList {
    val generatedNavigation = KotlinCodeGeneration.buildObject(className = input.className) {
      addModifiers(KModifier.DATA)
      addSuperinterface(TabwebNavigationGenerator.TABWEB_NAVIGATION)

      addProperty(name = "elements", type = TabwebNavigationGenerator.NAVIGATION_ELEMENTS) {
        addModifiers(KModifier.PRIVATE)
        initializer(
          buildCodeBlock {
            add("listOf(\n")
            input.manifest.elements.forEach { element ->
              add(elementCode(element, input.manifestFile))
              add(",\n")
            }
            add(")")
          }
        )
      }

      addFunction(
        buildFun("iterator") {
          addModifiers(KModifier.OVERRIDE)
          returns(TabwebNavigationGenerator.ITERATOR)
          addStatement("return elements.iterator()")
        }
      )
    }

    return KotlinFileSpecList.of(generatedNavigation.toFileSpec())
  }


  private fun elementCode(element: Element, manifestFile: Path): CodeBlock = CodeBlock.builder()
    .add("%T(\n", TabwebNavigationGenerator.NAVIGATION_ELEMENT)
    .add("  title = %S,\n", element.name)
    .apply {
      element.description?.let { add("  description = %S,\n", it) }
    }
    .add("  route = %T(%S),\n", TabwebNavigationGenerator.URL, element.route)
    .apply {
      element.icon?.let { add("  icon = %T.%L,\n", TabwebNavigationGenerator.TABWEB_ICON, it) }
      element.badgeRef?.let { add("  badgeRef = %S,\n", it) }
      element.columns?.let { if (it != 1) add("  columns = %L,\n", it) }
    }
    .add("  children = %L,\n", listCode(element.elements.orEmpty()) { elementCode(it, manifestFile) })
    .add(
      "  sections = %L,\n",
      listCode(element.sectionEntries.orEmpty().flatMap { sections(it, manifestFile) }) {
        sectionCode(it, manifestFile)
      },
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
    return decode(includedFile, SectionsDocument.serializer()).sections
  }

  private fun sectionCode(section: Section, manifestFile: Path): CodeBlock = CodeBlock.builder()
    .add("%T(\n", TabwebNavigationGenerator.NAVIGATION_SECTION)
    .add("  title = %S,\n", section.name)
    .add("  elements = %L,\n", listCode(section.elements) { elementCode(it, manifestFile) })
    .add(")")
    .build()

  private fun <T> listCode(items: List<T>, itemCode: (T) -> CodeBlock): CodeBlock = CodeBlock.builder()
    .add("listOf(\n")
    .apply { items.forEach { add("  %L,\n", itemCode(it)) } }
    .add(")")
    .build()
}
