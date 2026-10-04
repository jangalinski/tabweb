package com.github.jangalinski.tabweb.table

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Default implementation of [TableComposable].
 */
data object TableDsl : TableComposable {
  @Composable
  override fun table(table: Table, modifier: Modifier) {
    table.invoke(modifier)
  }

  @Composable
  override fun table(
    responsive: TableResponsive,
    vcenter: Boolean,
    striped: Boolean,
    stripedColumns: Boolean,
    hover: Boolean,
    bordered: Boolean,
    borderless: Boolean,
    sm: Boolean,
    noWrap: Boolean,
    transparent: Boolean,
    cardTable: Boolean,
    selectable: Boolean,
    stickyHeader: Boolean,
    modifier: Modifier,
    content: @Composable TableScope.() -> Unit,
  ) {
    Table(
      responsive = responsive,
      vcenter = vcenter,
      striped = striped,
      stripedColumns = stripedColumns,
      hover = hover,
      bordered = bordered,
      borderless = borderless,
      sm = sm,
      noWrap = noWrap,
      transparent = transparent,
      cardTable = cardTable,
      selectable = selectable,
      stickyHeader = stickyHeader,
      content = content,
    ).invoke(modifier)
  }

  @Composable
  override fun table(data: TablerTableData, modifier: Modifier) {
    TablerTable(data = data, modifier = modifier)
  }
}
