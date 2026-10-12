package com.github.jangalinski.tabweb.gradle.buildlogic.lib.generator

import com.github.jangalinski.tabweb.gradle.buildlogic.lib.PKG_ROOT
import com.github.jangalinski.tabweb.gradle.buildlogic.lib.model.ColorsModel
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry
import io.toolisticon.kotlin.generation.spi.context.KotlinCodeGenerationContextBase

@ExperimentalKotlinPoetApi
class TabwebContext(
  registry: KotlinCodeGenerationSpiRegistry,
  val colors: ColorsModel
) : KotlinCodeGenerationContextBase<TabwebContext>(registry) {
  override val contextType = TabwebContext::class

  val foundationPackage: String = "$PKG_ROOT._foundation"
  val basePackage = PKG_ROOT
}
