package com.github.jangalinski.tabweb.table

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebComponent
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.compose.KTable
import com.github.jangalinski.tabweb._foundation.css.plus
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Tabler table component for laying out rows and columns of data.
 */
interface Table : TabwebComponent {
  val responsive: TableResponsive get() = TableResponsive.NONE
  val vcenter: Boolean get() = false
  val striped: Boolean get() = false
  val stripedColumns: Boolean get() = false
  val hover: Boolean get() = false
  val bordered: Boolean get() = false
  val borderless: Boolean get() = false
  val sm: Boolean get() = false
  val noWrap: Boolean get() = false
  val transparent: Boolean get() = false
  val cardTable: Boolean get() = false
  val selectable: Boolean get() = false
  val stickyHeader: Boolean get() = false
  val content: @Composable TableScope.() -> Unit

  @Composable
  override fun invoke(modifier: Modifier) {
    val tableModifier = TableCss.TABLE +
      (if (cardTable) TableCss.TABLE_CARD else Modifier) +
      (if (vcenter) TableCss.TABLE_VCENTER else Modifier) +
      (if (striped) TableCss.TABLE_STRIPED else Modifier) +
      (if (stripedColumns) TableCss.TABLE_STRIPED_COLUMNS else Modifier) +
      (if (hover) TableCss.TABLE_HOVER else Modifier) +
      (if (bordered) TableCss.TABLE_BORDERED else Modifier) +
      (if (borderless) TableCss.TABLE_BORDERLESS else Modifier) +
      (if (sm) TableCss.TABLE_SM else Modifier) +
      (if (noWrap) TableCss.TABLE_NOWRAP else Modifier) +
      (if (transparent) TableCss.TABLE_TRANSPARENT else Modifier) +
      (if (selectable) TableCss.TABLE_SELECTABLE else Modifier) +
      (if (stickyHeader) TableCss.TABLE_STICKY_HEADER else Modifier) +
      modifier

    val renderTable: @Composable () -> Unit = {
      KTable(modifier = tableModifier) {
        DefaultTableScope.content()
      }
    }

    if (responsive != TableResponsive.NONE) {
      KDiv(modifier = responsive.modifier) {
        renderTable()
      }
    } else {
      renderTable()
    }
  }

  companion object {
    /**
     * Creates a [Table] from a DSL builder block.
     */
    operator fun invoke(
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
      content: @Composable TableScope.() -> Unit = {},
    ): Table = object : Table {
      override val responsive: TableResponsive = responsive
      override val vcenter: Boolean = vcenter
      override val striped: Boolean = striped
      override val stripedColumns: Boolean = stripedColumns
      override val hover: Boolean = hover
      override val bordered: Boolean = bordered
      override val borderless: Boolean = borderless
      override val sm: Boolean = sm
      override val noWrap: Boolean = noWrap
      override val transparent: Boolean = transparent
      override val cardTable: Boolean = cardTable
      override val selectable: Boolean = selectable
      override val stickyHeader: Boolean = stickyHeader
      override val content: @Composable TableScope.() -> Unit = content
    }
  }
}
