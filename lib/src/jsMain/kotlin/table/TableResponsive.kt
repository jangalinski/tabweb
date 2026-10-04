package com.github.jangalinski.tabweb.table

import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Breakpoint at which a table enables horizontal scrolling.
 */
enum class TableResponsive(internal val modifier: Modifier) {
  NONE(Modifier),
  ALWAYS(cssClass("table-responsive")),
  SM(cssClass("table-responsive-sm")),
  MD(cssClass("table-responsive-md")),
  LG(cssClass("table-responsive-lg")),
  XL(cssClass("table-responsive-xl")),
  XXL(cssClass("table-responsive-xxl"));

  companion object {
    val SMALL get() = SM
    val MEDIUM get() = MD
    val LARGE get() = LG
    val EXTRA_LARGE get() = XL
  }
}

/**
 * Backward compatibility alias for [TableResponsive].
 */
typealias TablerTableResponsive = TableResponsive
