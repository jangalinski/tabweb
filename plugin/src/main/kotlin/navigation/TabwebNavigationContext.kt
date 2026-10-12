package com.github.jangalinski.tabweb.plugin.navigation

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry
import io.toolisticon.kotlin.generation.spi.context.KotlinCodeGenerationContextBase

/**
 * Generation context shared by navigation strategies.
 */
@OptIn(ExperimentalKotlinPoetApi::class)
class TabwebNavigationContext(
    registry: KotlinCodeGenerationSpiRegistry,
) : KotlinCodeGenerationContextBase<TabwebNavigationContext>(registry) {
  override val contextType = TabwebNavigationContext::class
}
