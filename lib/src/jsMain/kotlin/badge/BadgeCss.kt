package com.github.jangalinski.tabweb.badge

import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Maps the badge model to the Tabweb CSS classes used by its renderers.
 */
internal data object BadgeCss {
  val badge = cssClass("badge")
  val iconOnly = cssClass("badge-icononly")
  val badgeList = cssClass("badge-list")
  val outline = cssClass("badge-outline")
  private val shapes by lazy {
    mapOf(
      BadgeShape.DEFAULT to Modifier,
      BadgeShape.PILL to cssClass("badge-pill"),
    )
  }

  fun outlineText(color: BackgroundColor) = cssClass("text-${color.value.removePrefix("bg-").removeSuffix("-lt")}")

  fun shape(shape: BadgeShape): Modifier = shapes.getValue(shape)
}
