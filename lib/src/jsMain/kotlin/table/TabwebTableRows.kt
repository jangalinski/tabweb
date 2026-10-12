package com.github.jangalinski.tabweb.table

import androidx.compose.runtime.*

/**
 * Row source for data-driven Tabweb tables.
 *
 * Implementations may expose all rows directly or decorate a larger collection with
 * client-side behaviour such as pagination. The [rows] property is always the list that
 * should be rendered for the current view.
 */
interface TabwebTableRows {
  /** Rows currently visible in the table body. */
  val rows: List<TabwebTableRow>

  /** Total number of logical rows in the underlying source. */
  val totalRows: Int

  /**
   * Number of body rows the table should reserve visually.
   *
   * When this is larger than [rows], the table renderer adds empty placeholder rows so
   * the table height stays stable on short pages.
   */
  val expectedDisplayRows: Int? get() = null

  /** Pagination state for this row source, or `null` when the rows are not paginated. */
  val pagination: TabwebPaginationData? get() = null

  /** Moves this row source to [page] when supported. Static row sources ignore this. */
  fun goToPage(page: Int) {
  }
}

/** Static [TabwebTableRows] implementation that renders the provided rows as-is. */
data class StaticTabwebTableRows(
  override val rows: List<TabwebTableRow>,
) : TabwebTableRows {
  override val totalRows: Int get() = rows.size
}

/**
 * Client-side paginated [TabwebTableRows] view over a complete row list.
 *
 * Use [rememberPaginatedTableRows] from composable code so the current page survives
 * recomposition and page-link clicks update the visible row slice.
 */
class PaginatedTabwebTableRows internal constructor(
  private val allRows: List<TabwebTableRow>,
  private val pageSize: Int,
  private val currentPage: Int,
  private val texts: TabwebPaginationTexts,
  private val window: TabwebPaginationWindow,
  private val onPageSelected: (Int) -> Unit,
) : TabwebTableRows {
  private val totalPages = maxOf(1, (allRows.size + pageSize - 1) / pageSize)

  override val rows: List<TabwebTableRow> = allRows
    .drop((currentPage - 1) * pageSize)
    .take(pageSize)

  override val totalRows: Int get() = allRows.size

  override val expectedDisplayRows: Int get() = pageSize

  override val pagination: TabwebPaginationData = TabwebPaginationData(
    currentPage = currentPage,
    totalPages = totalPages,
    pageSize = pageSize,
    totalItems = allRows.size,
    texts = texts,
    window = window,
  )

  override fun goToPage(page: Int) {
    onPageSelected(page.coerceIn(1, totalPages))
  }
}

/**
 * Remembers a paginated view of table rows.
 */
@Composable
fun rememberPaginatedTableRows(
  rows: List<TabwebTableRow>,
  pageSize: Int,
  initialPage: Int = 1,
  texts: TabwebPaginationTexts = TabwebPaginationTexts(),
  window: TabwebPaginationWindow = TabwebPaginationWindow(),
): TabwebTableRows {
  var currentPage by remember(rows, pageSize) { mutableStateOf(initialPage) }
  return remember(rows, pageSize, currentPage, texts, window) {
    PaginatedTabwebTableRows(
      allRows = rows,
      pageSize = pageSize,
      currentPage = currentPage,
      texts = texts,
      window = window,
      onPageSelected = { currentPage = it },
    )
  }
}

/** Wraps a plain row list as static [TabwebTableRows]. */
fun List<TabwebTableRow>.asTabwebTableRows(): TabwebTableRows = StaticTabwebTableRows(this)
