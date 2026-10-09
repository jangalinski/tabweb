package com.github.jangalinski.tabweb.gradle.buildlogic.site.navigation

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.spec.KotlinFileSpecList
import io.toolisticon.kotlin.generation.spec.KotlinFileSpec
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry
import io.toolisticon.kotlin.generation.spi.context.KotlinCodeGenerationContextBase
import io.toolisticon.kotlin.generation.spi.registry.KotlinCodeGenerationSpiList
import io.toolisticon.kotlin.generation.spi.strategy.KotlinFileSpecListStrategy

private const val GENERATED_PACKAGE = "com.github.jangalinski.tabweb.site"
private const val GENERATED_NAME = "GeneratedSiteNavigation"

private val TABLER_NAVIGATION = ClassName("com.github.jangalinski.tabweb.navigation", "TablerNavigation")
private val NAVIGATION_ELEMENT = ClassName(
  "com.github.jangalinski.tabweb.navigation",
  "TablerNavigationElement",
)
private val NAVIGATION_SECTION = ClassName(
  "com.github.jangalinski.tabweb.navigation",
  "TablerNavigationSection",
)
private val URL = ClassName("com.github.jangalinski.tabweb._foundation", "Url")
private val TABLER_ICON = ClassName("com.github.jangalinski.tabweb.icon", "TablerIcon")

/** Generates the typed site navigation object from a navigation manifest. */
@OptIn(ExperimentalKotlinPoetApi::class)
class TabwebNavigationGenerator : (TablerNavigation) -> KotlinFileSpecList {
  private val registry: KotlinCodeGenerationSpiRegistry = KotlinCodeGenerationSpiList(
    TabwebNavigationStrategy(),
  ).registry()

  private val context = TabwebNavigationContext(registry)

  override fun invoke(input: TablerNavigation): KotlinFileSpecList =
    KotlinCodeGeneration.generateFiles(context = context, input = input)
}

/** Generation context shared by navigation strategies. */
@OptIn(ExperimentalKotlinPoetApi::class)
class TabwebNavigationContext(
  registry: KotlinCodeGenerationSpiRegistry,
) : KotlinCodeGenerationContextBase<TabwebNavigationContext>(registry) {
  override val contextType = TabwebNavigationContext::class
}

/** Creates the generated navigation data object. */
@OptIn(ExperimentalKotlinPoetApi::class)
class TabwebNavigationStrategy : KotlinFileSpecListStrategy<TabwebNavigationContext, TablerNavigation>(
  contextType = TabwebNavigationContext::class,
  inputType = TablerNavigation::class,
) {
  override fun invoke(
    context: TabwebNavigationContext,
    input: TablerNavigation,
  ): KotlinFileSpecList = KotlinFileGeneration.file(input)
}

@OptIn(ExperimentalKotlinPoetApi::class)
private object KotlinFileGeneration {
  fun file(navigation: TablerNavigation): KotlinFileSpecList {
    val generatedObject = TypeSpec.objectBuilder(GENERATED_NAME)
      .addModifiers(KModifier.DATA)
      .addProperty(
        PropertySpec.builder("data", TABLER_NAVIGATION)
          .initializer(navigationCode(navigation))
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

  private fun navigationCode(navigation: TablerNavigation): CodeBlock = CodeBlock.builder()
    .add("%T(\n", TABLER_NAVIGATION)
    .add("  root = %L,\n", listCode(navigation.root) { elementCode(it) })
    .add(")")
    .build()

  private fun elementCode(element: TablerNavigationElement): CodeBlock = CodeBlock.builder()
    .add("%T(\n", NAVIGATION_ELEMENT)
    .add("  title = %S,\n", element.title)
    .apply {
      element.description?.let { add("  description = %S,\n", it) }
    }
    .add("  route = %T(%S),\n", URL, element.route)
    .apply {
      element.icon?.let { add("  icon = %T.%L,\n", TABLER_ICON, it) }
      element.badgeRef?.let { add("  badgeRef = %S,\n", it) }
      if (element.columns != 1) add("  columns = %L,\n", element.columns)
    }
    .add("  children = %L,\n", listCode(element.children) { elementCode(it) })
    .add("  sections = %L,\n", listCode(element.sections) { sectionCode(it) })
    .add(")")
    .build()

  private fun sectionCode(section: TablerNavigationSection): CodeBlock = CodeBlock.builder()
    .add("%T(\n", NAVIGATION_SECTION)
    .add("  title = %S,\n", section.title)
    .add("  elements = %L,\n", listCode(section.elements) { elementCode(it) })
    .add(")")
    .build()

  private fun <T> listCode(items: List<T>, itemCode: (T) -> CodeBlock): CodeBlock =
    CodeBlock.builder()
      .add("listOf(\n")
      .apply { items.forEach { add("  %L,\n", itemCode(it)) } }
      .add(")")
      .build()
}
