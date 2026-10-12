package com.github.jangalinski.tabweb.button

import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Maps button options to the Tabweb CSS classes used by its renderers.
 */
internal data object ButtonCss {
  val button = cssClass("btn")
  val buttonList = cssClass("btn-list")
  val iconOnly = cssClass("btn-icon")
  val loading = cssClass("btn-loading")

  private val colors by lazy { ButtonColor.entries.associateWith { cssClass("btn-${it.cssName()}") } }
  private val sizes by lazy {
    mapOf(
      ButtonSize.SMALL to cssClass("btn-sm"),
      ButtonSize.MEDIUM to Modifier,
      ButtonSize.LARGE to cssClass("btn-lg"),
      ButtonSize.EXTRA_LARGE to cssClass("btn-xl"),
    )
  }
  private val styles by lazy {
    mapOf(
      ButtonStyle.SOLID to Modifier,
      ButtonStyle.OUTLINE to cssClass("btn-outline"),
      ButtonStyle.GHOST to cssClass("btn-ghost"),
      ButtonStyle.LINK to cssClass("btn-link"),
    )
  }
  private val shapes by lazy {
    mapOf(
      ButtonShape.DEFAULT to Modifier,
      ButtonShape.PILL to cssClass("btn-pill"),
      ButtonShape.SQUARE to cssClass("btn-square"),
    )
  }

  fun color(color: ButtonColor): Modifier = colors.getValue(color)

  fun size(size: ButtonSize): Modifier = sizes.getValue(size)

  fun style(style: ButtonStyle): Modifier = styles.getValue(style)

  fun shape(shape: ButtonShape): Modifier = shapes.getValue(shape)
}
