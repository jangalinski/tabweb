package com.github.jangalinski.tabweb.table

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebComposable
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Provides page-level composable entry points for the table concept.
 */
interface TableComposable : TabwebComposable {
  /**
   * Renders a [Table] instance.
   */
  @Composable
  fun table(table: Table, modifier: Modifier = Modifier)

  /**
   * Creates and renders a [Table] from a DSL builder block.
   */
  @Composable
  fun table(
    responsive: TableResponsive = TableResponsive.NONE,
    vcenter: Boolean = false,
    striped: Boolean = false,
    stripedColumns: Boolean = false,
    hover: Boolean = false,
    bordered: Boolean = false,
    borderless: Boolean = false,
    sm: Boolean = false,
    noWrap: Boolean = false,
    transparent: Boolean = false,
    cardTable: Boolean = false,
    selectable: Boolean = false,
    stickyHeader: Boolean = false,
    modifier: Modifier = Modifier,
    content: @Composable TableScope.() -> Unit,
  )

  /**
   * Creates and renders a [Table] from a [TablerTableData] configuration model.
   */
  @Composable
  fun table(
    data: TablerTableData,
    modifier: Modifier = Modifier,
  )
}
