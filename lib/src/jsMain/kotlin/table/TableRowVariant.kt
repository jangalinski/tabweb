package com.github.jangalinski.tabweb.table

import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Tabler semantic background variants for table rows.
 */
enum class TableRowVariant(internal val modifier: Modifier) {
  PRIMARY(cssClass("table-primary")),
  SECONDARY(cssClass("table-secondary")),
  SUCCESS(cssClass("table-success")),
  DANGER(cssClass("table-danger")),
  WARNING(cssClass("table-warning")),
  INFO(cssClass("table-info")),
  LIGHT(cssClass("table-light")),
  DARK(cssClass("table-dark")),
  ACTIVE(cssClass("table-active")),
}

/**
 * Backward compatibility alias for [TableRowVariant].
 */
typealias TablerTableRowVariant = TableRowVariant
