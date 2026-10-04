package com.github.jangalinski.tabweb.gradle.buildlogic.lib.generator

import com.github.jangalinski.tabweb.gradle.buildlogic.lib.generator.processor.EnumLazyModifierProzessor
import com.github.jangalinski.tabweb.gradle.buildlogic.lib.model.ColorsModel
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.MemberName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.spec.KotlinFileSpecList
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry
import io.toolisticon.kotlin.generation.spi.registry.KotlinCodeGenerationSpiList

@OptIn(ExperimentalKotlinPoetApi::class)
class TabwebLibGenerator(
  colors: ColorsModel
) : () -> KotlinFileSpecList {
  companion object {
    val MODIFIER_TYPE = ClassName("com.varabyte.kobweb.compose.ui", "Modifier")
    val MODIFIER_CLASS_NAMES = MemberName("com.varabyte.kobweb.compose.ui.modifiers", "classNames")
  }

  val registry: KotlinCodeGenerationSpiRegistry = KotlinCodeGenerationSpiList(
    TablerColorCssStrategy(),
    TabwebConstantsStrategy(),
    EnumLazyModifierProzessor()
  ).registry()

  val context: TablerContext = TablerContext(
    registry = registry,
    colors = colors
  )

  override operator fun invoke(): KotlinFileSpecList = KotlinCodeGeneration.generateFiles(context = context, input = Unit)
}
