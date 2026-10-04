package com.github.jangalinski.tabweb.table

import com.github.jangalinski.tabweb.avatar.Avatar
import com.github.jangalinski.tabweb.badge.Badge

/**
 * Pure configuration for a Tabler table.
 *
 * @param columns visible column headings
 * @param rows table body rows to render
 * @param responsive breakpoint at which horizontal scrolling starts
 * @param noWrap prevents text wrapping in all cells when `true`
 * @param stickyHeader makes the header row stick to the viewport top when scrolling
 * @param expectedDisplayRows optional body-row count to reserve visually with placeholder
 *                            rows when [rows] is shorter
 */
data class TablerTableData(
  val columns: List<TablerTableColumn>,
  val rows: List<TablerTableRow>,
  val responsive: TableResponsive = TableResponsive.ALWAYS,
  val noWrap: Boolean = false,
  val stickyHeader: Boolean = false,
  val expectedDisplayRows: Int? = null,
) {
  /**
   * Creates table data from a [TablerTableRows] source.
   */
  constructor(
    columns: List<TablerTableColumn>,
    rows: TablerTableRows,
    responsive: TableResponsive = TableResponsive.ALWAYS,
    noWrap: Boolean = false,
    stickyHeader: Boolean = false,
  ) : this(
    columns = columns,
    rows = rows.rows,
    responsive = responsive,
    noWrap = noWrap,
    stickyHeader = stickyHeader,
    expectedDisplayRows = rows.expectedDisplayRows,
  )

  init {
    require(columns.isNotEmpty()) { "A Tabler table requires at least one column." }
    require(rows.all { it.cells.size == columns.size }) {
      "Every Tabler table row must have the same number of cells as there are columns."
    }
    require(expectedDisplayRows == null || expectedDisplayRows >= 0) {
      "expectedDisplayRows must be >= 0 when set."
    }
  }
}

/** A visible column heading. */
data class TablerTableColumn(
  val label: String,
  val noWrap: Boolean = false,
)

/** One table row, optionally styled with a Tabler semantic variant. */
data class TablerTableRow(
  val cells: List<TablerTableCell>,
  val variant: TableRowVariant? = null,
)

/**
 * Sealed content model for a single Tabler table cell.
 *
 * Use [Text] for the common plain-text case, or one of the richer subtypes
 * ([AvatarName], [BadgeItem], [Tags], [Checkbox]) for structured content.
 */
sealed interface TablerTableCell {
  /** Whether this cell acts as a row header (`<th scope="row">` instead of `<td>`). */
  val isRowHeader: Boolean get() = false

  /**
   * A plain-text cell, optionally muted and/or acting as a row header.
   */
  data class Text(
    val value: String,
    val muted: Boolean = false,
    override val isRowHeader: Boolean = false,
  ) : TablerTableCell

  /**
   * A cell containing an avatar followed by a display name and optional description.
   */
  data class AvatarName(
    val avatar: Avatar,
    val name: String,
    val description: String? = null,
  ) : TablerTableCell

  /**
   * A cell containing a badge.
   *
   * @param label visible badge text
   * @param variant optional styling variant or class
   */
  data class Badge(
    val label: String,
    val variant: String? = null,
  ) : TablerTableCell

  /**
   * A cell containing a horizontal list of tag spans.
   */
  data class Tags(val tags: List<String>) : TablerTableCell

  /**
   * A cell containing a checkbox input.
   */
  data class Checkbox(
    val checked: Boolean,
    val label: String? = null,
    val onCheckedChange: ((Boolean) -> Unit)? = null,
  ) : TablerTableCell

  companion object {
    /** Helper constructor matching the old TablerTableCell data class. */
    operator fun invoke(value: String, muted: Boolean = false, isRowHeader: Boolean = false): TablerTableCell =
      Text(value = value, muted = muted, isRowHeader = isRowHeader)
  }
}
