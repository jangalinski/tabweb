# Requirements

### Overview & Goals

Add Tabler pagination to `TablerTableCard` so large data sets can be browsed page by page. The pagination lives in a `.card-footer` below the table, exactly as shown in the Tabler admin preview (Interface → Tables → Employee table). The component is **presentational only** — the caller controls which page is active and handles data slicing.

### Scope

**In scope**
- `TablerPaginationData` model: currentPage, totalPages, optional pageSize + totalItems.
- `TablerTableCard` gains optional `pagination` parameter and an `onPageChange` callback.
- `.card-footer` with flex layout: left = "Showing X to Y of Z entries", right = `ul.pagination` with prev/next chevrons and numbered page links.
- Active page link gets `page-item active`; prev disabled on page 1, next disabled on last page.
- `ClassNames` constants for all new CSS classes (`cardFooter`, `pagination`, `pageItem`, `pageLink`, `pageItemActive`, `pageItemDisabled`).
- Tests in `TablerTableCardTest.kt`: footer present, footer absent, active page, disabled buttons, summary text.
- Tagessieg example updated to show a paginated table.

**Out of scope**
- Standalone `TablerPagination` composable independent of tables — deferred.
- Client-side or server-side actual data slicing.
- Ellipsis / truncated page ranges.

### Reference HTML (from Tabler admin preview)

```html
<div class="card-footer">
  <div class="row g-2 justify-content-center justify-content-sm-between">
    <div class="col-auto d-flex align-items-center">
      <p class="m-0 text-secondary">Showing <strong>1 to 8</strong> of <strong>16 entries</strong></p>
    </div>
    <div class="col-auto">
      <ul class="pagination m-0 ms-auto">
        <li class="page-item disabled">
          <a class="page-link" href="#" tabindex="-1" aria-disabled="true">«</a>
        </li>
        <li class="page-item"><a class="page-link" href="#">1</a></li>
        <li class="page-item active"><a class="page-link" href="#">2</a></li>
        <li class="page-item"><a class="page-link" href="#">3</a></li>
        <li class="page-item">
          <a class="page-link" href="#">»</a>
        </li>
      </ul>
    </div>
  </div>
</div>
```

# Technical Design

### Data Model

```kotlin
/**
 * Pagination state for a [TablerTableCard].
 *
 * @param currentPage  1-based index of the currently visible page
 * @param totalPages   total number of pages
 * @param pageSize     optional — rows per page, used for the "Showing X to Y" summary
 * @param totalItems   optional — total item count, used for the "of Z entries" summary
 */
data class TablerPaginationData(
  val currentPage: Int,
  val totalPages: Int,
  val pageSize: Int? = null,
  val totalItems: Int? = null,
)
```

### Updated `TablerTableCard` signature

```kotlin
@Composable
fun TablerTableCard(
  title: String,
  subtitle: String? = null,
  data: TablerTableData,
  pagination: TablerPaginationData? = null,
  onPageChange: ((page: Int) -> Unit)? = null,
)
```

### New ClassNames constants

```
cardFooter      = "card-footer"
pagination      = "pagination m-0 ms-auto"
pageItem        = "page-item"
pageItemActive  = "page-item active"
pageItemDisabled = "page-item disabled"
pageLink        = "page-link"
rowG2           = "row g-2 justify-content-center justify-content-sm-between"
colAutoFlex     = "col-auto d-flex align-items-center"
colAuto         = "col-auto"
```

### File Structure

```
src/jsMain/kotlin/
  models/
    TablerPaginationData.kt    ← new
  components/
    TablerTableCard.kt         ← extend: add pagination parameter + card-footer rendering
  styles/
    ClassNames.kt              ← add pagination-related constants

src/jsTest/kotlin/components/
  TablerTableCardTest.kt       ← extend: pagination test cases

site/tagessieg/src/jsMain/kotlin/pages/
  Index.kt                    ← update: add paginated table example
```

# Delivery Steps

### ✓ Step 1: Add TablerPaginationData model and ClassNames constants
`TablerPaginationData` is available in `models/TablerPaginationData.kt` and all pagination CSS class constants are added to `ClassNames.kt`.

- Create `src/jsMain/kotlin/models/TablerPaginationData.kt` with `data class TablerPaginationData(currentPage, totalPages, pageSize?, totalItems?)` and KDocs.
- Add to `ClassNames.kt`: `cardFooter`, `pagination`, `pageItem`, `pageItemActive`, `pageItemDisabled`, `pageLink`, `rowG2`, `colAutoFlex`, `colAuto` with KDocs.
- Verify compile only (no test changes yet).

### * Step 2: Extend TablerTableCard to render pagination footer
`TablerTableCard` renders a `.card-footer` when `pagination != null`, matching the Tabler reference HTML exactly.

- Add `pagination: TablerPaginationData? = null` and `onPageChange: ((Int) -> Unit)? = null` parameters to `TablerTableCard`.
- Render `.card-footer` using `renderPaginationFooter(pagination, onPageChange)` internal helper that generates the summary paragraph and `ul.pagination` list.
- For prev/next use `«`/`»` HTML entities.
- `TablerCards.kt` `tableCard(...)` DSL scope also gets the two new optional parameters forwarded.

###   Step 3: Add tests for pagination
All pagination scenarios in `TablerTableCardTest.kt` pass.

- No footer when `pagination = null` — `doesNotContain("card-footer")`.
- Footer present when `pagination` is set — `contains("card-footer")`.
- Active page link — `contains("page-item active")`.
- Disabled prev on page 1 — `contains("page-item disabled")`.
- Next enabled on non-last page — does NOT contain a second `page-item disabled`.
- Disabled next on last page — two `page-item disabled` present.
- Summary text — `contains("Showing")`, `contains("entries")` when pageSize+totalItems set.
- No summary text when pageSize/totalItems absent.

###   Step 4: Update Tagessieg example and run full test suite
All tests pass and the example shows a paginated table.

- Add a paginated `tableCard` to `Index.kt` (e.g. "Recent matches" card with pagination: page 1 of 3, pageSize 5, totalItems 13).
- Run `./gradlew jsBrowserTest` → all tests green.
- Run `./gradlew :tagessieg:compileKotlinJs` → BUILD SUCCESSFUL.
