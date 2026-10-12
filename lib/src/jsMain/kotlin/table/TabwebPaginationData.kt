package com.github.jangalinski.tabweb.table

/**
 * Presentational pagination state for a Tabweb table card.
 *
 * The component itself is display-only: the caller is responsible for tracking the active page
 * and slicing the data accordingly. Prefer [PaginatedTabwebTableRows] when the table should
 * own client-side page state and row slicing.
 *
 * @param currentPage 1-based index of the currently visible page
 * @param totalPages  total number of pages available
 * @param pageSize    optional -- rows per page; when provided together with [totalItems] the
 *                    card footer shows a summary built from [TabwebPaginationTexts.summaryTemplate]
 * @param totalItems  optional -- total number of items across all pages; used with [pageSize]
 *                    for the summary text
 * @param texts       visible labels used by the pagination footer
 * @param window      optional page-number display window for large result sets
 */
data class TabwebPaginationData(
  val currentPage: Int,
  val totalPages: Int,
  val pageSize: Int? = null,
  val totalItems: Int? = null,
  val texts: TabwebPaginationTexts = TabwebPaginationTexts(),
  val window: TabwebPaginationWindow = TabwebPaginationWindow(),
) {
  init {
    require(currentPage >= 1) { "currentPage must be >= 1 (got $currentPage)" }
    require(totalPages >= 1) { "totalPages must be >= 1 (got $totalPages)" }
    require(currentPage <= totalPages) { "currentPage ($currentPage) must be <= totalPages ($totalPages)" }
    pageSize?.let { require(it > 0) { "pageSize must be > 0 when set (got $it)" } }
    totalItems?.let { require(it >= 0) { "totalItems must be >= 0 when set (got $it)" } }
  }
}

/**
 * Controls which numbered page links are visible in large pagination footers.
 *
 * By default all page numbers are rendered to preserve the original table-card behaviour.
 * Set [maxVisiblePageNumbers] to keep the footer bounded; the first page, last page,
 * current page, and nearby pages remain visible while skipped ranges are replaced by
 * [TabwebPaginationTexts.ellipsisLabel].
 *
 * @param maxVisiblePageNumbers maximum number of clickable numbered page links to render,
 *                              excluding previous/next buttons and ellipsis placeholders.
 *                              `null` renders every page number.
 * @param siblingCount          number of pages to keep visible on each side of the
 *                              current page when the current page is away from the edges
 * @param boundaryCount         number of pages to keep visible at the start and end
 */
data class TabwebPaginationWindow(
  val maxVisiblePageNumbers: Int? = null,
  val siblingCount: Int = 1,
  val boundaryCount: Int = 1,
) {
  init {
    require(siblingCount >= 0) { "siblingCount must be >= 0 (got $siblingCount)" }
    require(boundaryCount >= 0) { "boundaryCount must be >= 0 (got $boundaryCount)" }
    maxVisiblePageNumbers?.let {
      val minimumVisiblePages = boundaryCount * 2 + siblingCount * 2 + 1
      require(it >= minimumVisiblePages) {
        "maxVisiblePageNumbers must be >= $minimumVisiblePages for siblingCount=$siblingCount " +
          "and boundaryCount=$boundaryCount (got $it)"
      }
    }
  }
}

/**
 * Display text used by a table pagination footer.
 *
 * This keeps pagination presentational text configurable while leaving page calculation
 * and row slicing to [PaginatedTabwebTableRows].
 *
 * @param previousPageLabel visible label for the previous-page link
 * @param nextPageLabel visible label for the next-page link
 * @param ellipsisLabel visible label for skipped page ranges
 * @param summaryTemplate summary text template. `{index}` is replaced by the visible
 *                        item index or range, and `{max}` is replaced by the total item count.
 */
data class TabwebPaginationTexts(
  val previousPageLabel: String = "\u00ab",
  val nextPageLabel: String = "\u00bb",
  val ellipsisLabel: String = "..",
  val summaryTemplate: String = "Showing {index} of {max} entries",
)
