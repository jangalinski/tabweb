package com.github.jangalinski.tabweb.table

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.compose.*
import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.github.jangalinski.tabweb._foundation.css.plus
import com.github.jangalinski.tabweb.card.CardCss
import com.github.jangalinski.tabweb.card.DefaultCardScope
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.attr

/**
 * Renders a Tabler card that displays a data table flush against the card edges.
 *
 * Unlike standard cards, this composable omits the `.card-body` wrapper and places the
 * responsive table directly inside the `.card`, matching Tabler's `card-table` pattern.
 * This avoids the extra padding gap that would appear if the table were placed inside a
 * normal card body.
 *
 * When [pagination] is provided a `.card-footer` is rendered below the table.
 *
 * @param title        headline shown in the `.card-header`
 * @param subtitle     optional secondary line shown below the title inside the card header
 * @param data         pure-data table configuration forwarded to [TablerTable]
 * @param pagination   optional pagination state; omit to hide the footer entirely
 * @param onPageChange optional callback invoked with the 1-based target page number when
 *                     the user clicks a pagination link
 */
@Composable
fun TablerTableCard(
  title: String,
  subtitle: String? = null,
  data: TablerTableData,
  pagination: TablerPaginationData? = null,
  onPageChange: ((Int) -> Unit)? = null,
  modifier: Modifier = Modifier,
) {
  KDiv(modifier = CardCss.CARD + modifier) {
    DefaultCardScope.header(title = title, subtitle = subtitle)
    Table(
      responsive = data.responsive,
      noWrap = data.noWrap,
      stickyHeader = data.stickyHeader,
      cardTable = true,
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
    }.invoke(Modifier)
    if (pagination != null) {
      PaginationFooter(pagination = pagination, onPageChange = onPageChange)
    }
  }
}

/**
 * Renders a table card from a row source that may own table behaviour such as pagination.
 *
 * @param title headline shown in the `.card-header`
 * @param subtitle optional secondary line shown below the title inside the card header
 * @param columns visible column headings
 * @param rows row source to render; paginated sources also provide footer state
 * @param responsive breakpoint at which horizontal scrolling starts
 * @param noWrap prevents text wrapping in all cells when `true`
 * @param stickyHeader makes the header row stick to the viewport top when scrolling
 */
@Composable
fun TablerTableCard(
  title: String,
  subtitle: String? = null,
  columns: List<TablerTableColumn>,
  rows: TablerTableRows,
  responsive: TableResponsive = TableResponsive.ALWAYS,
  noWrap: Boolean = false,
  stickyHeader: Boolean = false,
  modifier: Modifier = Modifier,
) {
  TablerTableCard(
    title = title,
    subtitle = subtitle,
    data = TablerTableData(
      columns = columns,
      rows = rows,
      responsive = responsive,
      noWrap = noWrap,
      stickyHeader = stickyHeader,
    ),
    pagination = rows.pagination,
    onPageChange = rows::goToPage,
    modifier = modifier,
  )
}

/**
 * Renders a table card using the composable DSL for the table content.
 */
@Composable
fun TablerTableCard(
  title: String,
  subtitle: String? = null,
  responsive: TableResponsive = TableResponsive.ALWAYS,
  noWrap: Boolean = false,
  stickyHeader: Boolean = false,
  modifier: Modifier = Modifier,
  block: @Composable TableScope.() -> Unit,
) {
  KDiv(modifier = CardCss.CARD + modifier) {
    DefaultCardScope.header(title = title, subtitle = subtitle)
    Table(
      responsive = responsive,
      noWrap = noWrap,
      stickyHeader = stickyHeader,
      cardTable = true,
      content = block,
    ).invoke(Modifier)
  }
}

@Composable
private fun PaginationFooter(
  pagination: TablerPaginationData,
  onPageChange: ((Int) -> Unit)?,
) {
  KDiv(modifier = CardCss.CARD_FOOTER + cssClass("d-flex") + cssClass("align-items-center")) {
    val pageSize = pagination.pageSize
    val totalItems = pagination.totalItems
    if (pageSize != null && totalItems != null) {
      val firstItem = if (totalItems == 0) 0 else (pagination.currentPage - 1) * pageSize + 1
      val lastItem = minOf(pagination.currentPage * pageSize, totalItems)
      val index = if (firstItem == lastItem) firstItem.toString() else "$firstItem to $lastItem"
      KP(modifier = cssClass("m-0") + TableCss.TEXT_SECONDARY) {
        KText(pagination.texts.summaryTemplate.replace("{index}", index).replace("{max}", totalItems.toString()))
      }
    }
    KUl(modifier = cssClass("pagination") + cssClass("m-0") + cssClass("ms-auto")) {
      PaginationItem(
        page = pagination.currentPage - 1,
        label = pagination.texts.previousPageLabel,
        disabled = pagination.currentPage <= 1,
        onPageChange = onPageChange,
      )
      paginationTokens(pagination).forEach { token ->
        when (token) {
          PaginationToken.Ellipsis -> PaginationEllipsis(pagination.texts.ellipsisLabel)
          is PaginationToken.Page -> PaginationItem(
            page = token.page,
            label = token.page.toString(),
            active = token.page == pagination.currentPage,
            onPageChange = onPageChange,
          )
        }
      }
      PaginationItem(
        page = pagination.currentPage + 1,
        label = pagination.texts.nextPageLabel,
        disabled = pagination.currentPage >= pagination.totalPages,
        onPageChange = onPageChange,
      )
    }
  }
}

private sealed interface PaginationToken {
  data class Page(val page: Int) : PaginationToken
  data object Ellipsis : PaginationToken
}

private fun paginationTokens(pagination: TablerPaginationData): List<PaginationToken> {
  val maxVisiblePageNumbers = pagination.window.maxVisiblePageNumbers
    ?: return (1..pagination.totalPages).map(PaginationToken::Page)

  if (pagination.totalPages <= maxVisiblePageNumbers) {
    return (1..pagination.totalPages).map(PaginationToken::Page)
  }

  val boundaryCount = pagination.window.boundaryCount
  val siblingCount = pagination.window.siblingCount
  val visiblePages = mutableSetOf<Int>()

  (1..boundaryCount).forEach { page ->
    if (page in 1..pagination.totalPages) visiblePages += page
  }
  ((pagination.totalPages - boundaryCount + 1)..pagination.totalPages).forEach { page ->
    if (page in 1..pagination.totalPages) visiblePages += page
  }
  val currentWindowSize = siblingCount * 2 + 1
  var currentWindowStart = pagination.currentPage - siblingCount
  var currentWindowEnd = pagination.currentPage + siblingCount
  if (currentWindowStart < 1) {
    currentWindowEnd += 1 - currentWindowStart
    currentWindowStart = 1
  }
  if (currentWindowEnd > pagination.totalPages) {
    currentWindowStart -= currentWindowEnd - pagination.totalPages
    currentWindowEnd = pagination.totalPages
  }
  currentWindowStart = currentWindowStart.coerceAtLeast(1)
  currentWindowEnd = currentWindowEnd.coerceAtMost(pagination.totalPages)
  if (currentWindowEnd - currentWindowStart + 1 > currentWindowSize) {
    currentWindowEnd = currentWindowStart + currentWindowSize - 1
  }
  (currentWindowStart..currentWindowEnd).forEach { page ->
    if (page in 1..pagination.totalPages) visiblePages += page
  }

  return visiblePages.sorted()
    .fold(mutableListOf<PaginationToken>()) { tokens, page ->
      val previousPage = (tokens.lastOrNull() as? PaginationToken.Page)?.page
      if (previousPage != null && page - previousPage > 1) {
        tokens += PaginationToken.Ellipsis
      }
      tokens += PaginationToken.Page(page)
      tokens
    }
}

@Composable
private fun PaginationEllipsis(label: String) {
  KLi(modifier = cssClass("page-item") + cssClass("disabled")) {
    KAnchor(
      href = "#",
      modifier = cssClass("page-link")
        .then(Modifier.attr("tabindex", "-1"))
        .then(Modifier.attr("aria-disabled", "true")),
      onClickAction = {},
    ) {
      KText(label)
    }
  }
}

@Composable
private fun PaginationItem(
  page: Int,
  label: String,
  disabled: Boolean = false,
  active: Boolean = false,
  onPageChange: ((Int) -> Unit)?,
) {
  val stateModifier = when {
    disabled -> cssClass("disabled")
    active -> cssClass("active")
    else -> Modifier
  }
  KLi(modifier = cssClass("page-item") + stateModifier) {
    KAnchor(
      href = "#",
      modifier = cssClass("page-link")
        .then(if (disabled) Modifier.attr("tabindex", "-1") else Modifier)
        .then(if (disabled) Modifier.attr("aria-disabled", "true") else Modifier.attr("data-page", page.toString())),
      onClickAction = if (disabled) ({}) else ({ onPageChange?.invoke(page) }),
    ) {
      KText(label)
    }
  }
}
