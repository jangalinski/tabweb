package com.github.jangalinski.tabweb.table

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.compose.KSpan
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.github.jangalinski.tabweb._foundation.css.plus
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Renders a data-driven Tabler table with responsive, no-wrap, and sticky-header options.
 */
@Composable
fun TablerTable(
  data: TablerTableData,
  modifier: Modifier = Modifier,
) {
  Table(
    responsive = data.responsive,
    noWrap = data.noWrap,
    stickyHeader = data.stickyHeader,
  ) {
    header(sticky = data.stickyHeader) {
      data.columns.forEach { column ->
        cell(text = column.label, noWrap = column.noWrap)
      }
    }
    body {
      data.rows.forEach { row ->
        row(variant = row.variant) {
          row.cells.forEach { cell ->
            when (cell) {
              is TablerTableCell.Text -> cell(
                text = cell.value,
                muted = cell.muted,
                isRowHeader = cell.isRowHeader,
              )
              is TablerTableCell.AvatarName -> avatar(
                avatar = cell.avatar,
                name = cell.name,
                description = cell.description,
              )
              is TablerTableCell.Badge -> {
                cell {
                  val bgMod = if (cell.variant != null) cssClass(cell.variant) else Modifier
                  KSpan(modifier = cssClass("badge") + bgMod) {
                    KText(cell.label)
                  }
                }
              }
              is TablerTableCell.Tags -> tags(cell.tags)
              is TablerTableCell.Checkbox -> checkbox(
                checked = cell.checked,
                label = cell.label,
                onCheckedChange = cell.onCheckedChange,
              )
            }
          }
        }
      }
      val placeholderRows = ((data.expectedDisplayRows ?: 0) - data.rows.size).coerceAtLeast(0)
      repeat(placeholderRows) {
        row(modifier = TableCss.TABLE_PLACEHOLDER_ROW) {
          repeat(data.columns.size) {
            cell { KText("\u00a0") }
          }
        }
      }
    }
  }.invoke(modifier)
}

/**
 * Composable DSL overload of [TablerTable] that accepts arbitrary composable cell content.
 *
 * Use this overload when cells need to embed existing components such as [Avatar],
 * badges, or links. For plain-text tables the [TablerTable] data overload is simpler.
 *
 * Example:
 * ```kotlin
 * TablerTable {
 *   header { cell("Name"); cell("Status") }
 *   row {
 *     cell { avatar(...) }
 *     cell { KText("Active") }
 *   }
 * }
 * ```
 *
 * @param responsive breakpoint at which horizontal scrolling starts
 * @param noWrap     prevents text wrapping in all cells when `true`
 * @param stickyHeader makes the header row stick to the viewport top when scrolling
 * @param block      DSL builder that declares the header and rows
 */
@Composable
fun TablerTable(
  responsive: TableResponsive = TableResponsive.ALWAYS,
  noWrap: Boolean = false,
  stickyHeader: Boolean = false,
  modifier: Modifier = Modifier,
  block: @Composable TableScope.() -> Unit,
) {
  Table(
    responsive = responsive,
    noWrap = noWrap,
    stickyHeader = stickyHeader,
    content = block,
  ).invoke(modifier)
}
