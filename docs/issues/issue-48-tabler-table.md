---
sessionId: session-260905-100249-zje1
---

# Requirements

### Overview & Goals

Extend the existing `TablerTable` component from a text-only, innerHTML-workaround renderer into a full, composable-capable Tabler table component — including a dedicated `TablerTableCard`, a rich sealed cell model, and a DSL builder that allows arbitrary Compose content inside cells.

### Current state (already done)

- `TablerTableData` model: columns, rows, `TablerTableCell` (text-only), responsive, no-wrap, sticky-header, row variants.
- `TablerTable(data)` renders a `<table>` via `element.innerHTML` because Compose-Web native table nodes (`Table`, `Tr`, `Td`, …) cause a runtime blank-page crash.
- Live demo on the Tagessieg start page ("Recent matches" card).

### Scope

**In scope**
- Investigate and fix the Compose-Web native table node runtime failure.
- New `TablerTableCard` composable: `.card` + `.card-header` (title / subtitle / actions) + `table.card-table` (no card-body padding gap).
- Sealed `TablerTableCell` model: text, avatar+name, badge/status, tag collection, checkbox.
- Header config for `TablerTableCard`: title, subtitle, optional search field, action descriptors.
- Composable DSL builder: `TablerTable { header { cell(…) }; row { cell { TablerAvatar(…) } } }`.
- Tests for each new variant and the new card.
- Update the Tagessieg example to use `TablerTableCard`.

**Out of scope**
- Interactive sorting, filtering, pagination, row-selection callbacks.
- Custom renderers beyond the sealed cell types.

### User Stories

- As a library consumer, I want to embed a `TablerAvatar` or badge directly in a table cell so that I can show rich data without workaround code.
- As a library consumer, I want a `TablerTableCard` so that the table fills the card to its edges the way Tabler's design prescribes.
- As a library consumer, I want `TablerTable(data)` to remain the simple text-only shortcut so that simple tables need no boilerplate.

### Functional Requirements

1. `TablerTable` must not cause a blank page when rendered in a live Kobweb browser client.
2. `TablerTableCard` must render `.card-header` with title and optional subtitle, then `table.card-table` directly (no `.card-body` wrapper).
3. Sealed `TablerTableCell` must support: `Text`, `AvatarName`, `Badge`, `Tags`, `Checkbox`.
4. DSL builder must allow `@Composable` lambdas as cell content.
5. All new public declarations must have KDocs.
6. Existing `TablerTable(data)` API must remain backward-compatible.

# Technical Design

### Current Implementation

| File | Role |
|---|---|
| `src/jsMain/kotlin/components/TablerTable.kt` | Renders via `element.innerHTML` in a `Div`; avoids native Compose table nodes |
| `src/jsMain/kotlin/models/TablerTableData.kt` | Pure data: `TablerTableData`, `TablerTableColumn`, `TablerTableRow`, `TablerTableCell(text, muted, isRowHeader)`, enums |
| `src/jsMain/kotlin/components/TablerCard.kt` | `.card` → `.card-header` → `.card-body` — does **not** support a table-at-edges layout |
| `src/jsMain/kotlin/styles/ClassNames.kt` | Already has `table = "table card-table table-vcenter"`, `CARD_HEADER`, `cardTitle` |
| `_examples/tagessieg/.../pages/Index.kt` | Uses `TablerTable` inside a regular `card { }` block |
| `src/jsTest/kotlin/components/TablerTableTest.kt` | 6 existing tests covering current text-only API |

### Key Decisions

1. **Fix native Compose-Web table nodes first** — the root cause of the blank-page bug must be understood before the composable-cell DSL is built on top of it.
2. **Two API levels are kept** — `TablerTable(data: TablerTableData)` stays as the text-only fast path; a new `TablerTable { }` DSL composable is the general path.
3. **`TablerTableCard` is separate from `TablerCard`** — it skips `.card-body` and places the table directly inside `.card` after the header, matching Tabler's `card-table` pattern.
4. **Sealed cell model replaces `TablerTableCell`** — the existing data class becomes `TablerTableCell.Text`; other variants are added as new sealed subtypes.

### Data Models

```kotlin
sealed interface TablerTableCell {
  val isRowHeader: Boolean get() = false

  data class Text(
    val value: String,
    val muted: Boolean = false,
    override val isRowHeader: Boolean = false,
  ) : TablerTableCell

  data class AvatarName(val avatar: TablerAvatarData, val name: String) : TablerTableCell
  data class Badge(val label: String, val variant: String? = null) : TablerTableCell
  data class Tags(val tags: List<String>) : TablerTableCell
  data class Checkbox(val checked: Boolean, val label: String? = null) : TablerTableCell
}
```

```kotlin
// New TablerTableCard signature
@Composable
fun TablerTableCard(title: String, subtitle: String? = null, data: TablerTableData)

// DSL overload for TablerTable
@Composable
fun TablerTable(
  responsive: TablerTableResponsive = TablerTableResponsive.ALWAYS,
  noWrap: Boolean = false,
  stickyHeader: Boolean = false,
  block: TablerTableScope.() -> Unit,
)
```

### File Structure

```
src/jsMain/kotlin/
  components/
    TablerTable.kt          ← modify: fix native nodes, add DSL overload
    TablerTableCard.kt      ← new
  models/
    TablerTableData.kt      ← modify: TablerTableCell → sealed interface
  styles/
    ClassNames.kt           ← add cardTable if missing

src/jsTest/kotlin/components/
  TablerTableTest.kt        ← extend: sealed cell types
  TablerTableCardTest.kt    ← new

_examples/tagessieg/src/jsMain/kotlin/pages/
  Index.kt                  ← update: switch to TablerTableCard
```

### Risks

- **Native table node failure** — root cause not yet confirmed; keep innerHTML fallback for `TablerTable(data)` until confirmed fixed.
- **Sealed interface migration** — callers using `TablerTableCell(…)` directly will break; mitigated by renaming to `TablerTableCell.Text`.

# Testing

### Validation Approach

All validation runs via `./gradlew jsBrowserTest` (ChromeHeadless). Each new component and cell variant gets dedicated tests following the pattern in `TablerTableTest.kt`.

### Key Scenarios

| Scenario | File | Assert |
|---|---|---|
| `TablerTableCell.Text` muted renders `text-secondary` | `TablerTableTest` | `contains("text-secondary")` |
| `TablerTableCell.AvatarName` renders avatar HTML + name | `TablerTableTest` | `contains("avatar")`, `contains(name)` |
| `TablerTableCell.Badge` renders badge class + label | `TablerTableTest` | `contains("badge")` |
| `TablerTableCell.Tags` renders all tag spans | `TablerTableTest` | each tag string present |
| `TablerTableCell.Checkbox` renders checkbox input | `TablerTableTest` | `contains("checkbox")` |
| `TablerTableCard` renders `.card-header` with title | `TablerTableCardTest` | `contains("card-header")` |
| `TablerTableCard` with subtitle shows subtitle | `TablerTableCardTest` | `contains(subtitle)` |
| `TablerTableCard` has no `.card-body` | `TablerTableCardTest` | `doesNotContain("card-body")` |
| DSL builder places `TablerAvatar` inside a cell | `TablerTableTest` | `contains("avatar")` |

### Edge Cases

- `TablerTableCell.Tags` with empty list renders an empty cell without error.
- `TablerTableCell.Checkbox(checked = true)` renders the `checked` attribute.
- `TablerTableCard` with no subtitle omits the subtitle element.

# Delivery Steps

### ✓ Step 1: Investigate and fix native Compose-Web table node runtime failure
Native Compose-Web table nodes (`Table`, `Tr`, `Td`, etc.) work correctly in `TablerTable` without causing a blank page.

- Reproduce the blank-page bug by temporarily switching `TablerTable.kt` from innerHTML to native Compose table composables.
- Identify the root cause (likely: outer element type, DOM reconciler constraint, or Kobweb version issue).
- Apply the fix — ensuring the responsive wrapper `Div` is not replaced by a non-div node and native `Table {}` is a direct child.
- Confirm via `./gradlew jsBrowserTest` that all existing tests still pass.

### ✓ Step 2: Replace TablerTableCell data class with sealed interface and update renderer
The `TablerTableCell` type is a sealed interface with `Text`, `AvatarName`, `Badge`, `Tags`, and `Checkbox` variants; existing tests pass and new variant tests are added.

- In `src/jsMain/kotlin/models/TablerTableData.kt`: replace `data class TablerTableCell` with `sealed interface TablerTableCell`; move existing fields into `TablerTableCell.Text`.
- Add `AvatarName`, `Badge`, `Tags`, and `Checkbox` data classes as sealed subtypes.
- Update `TablerTable.kt` renderer to dispatch on the sealed subtypes.
- For `AvatarName` and `Badge`, delegate to the corresponding component renderers or their HTML equivalents.
- Add `ClassNames.badge`, `ClassNames.cardTable` constants to `ClassNames.kt` if not already present.
- Extend `TablerTableTest.kt` with one test per new sealed variant.

### ✓ Step 3: Add TablerTableCard component
`TablerTableCard` is available as a new composable that renders a `.card` with a `.card-header` (title + optional subtitle) directly followed by the responsive table — no `.card-body` gap.

- Create `src/jsMain/kotlin/components/TablerTableCard.kt` with `@Composable fun TablerTableCard(title, subtitle?, data)`.
- Structure: `div.card` → `div.card-header` (H3 title + optional subtitle span) → responsive `div` → `TablerTable(data)` without its own responsive wrapper.
- Create `src/jsTest/kotlin/components/TablerTableCardTest.kt` covering: title present, subtitle present/absent, no `.card-body`, responsive wrapper class present.
- Update `_examples/tagessieg/.../pages/Index.kt`: replace `card(title = "Recent matches") { TablerTable(…) }` with `TablerTableCard(title = "Recent matches", data = …)`.

### ✓ Step 4: Add composable DSL builder for TablerTable
`TablerTable { header { } row { cell { } } }` DSL is available as a composable overload, allowing arbitrary `@Composable` content in cells.

- Add `TablerTableScope`, `TablerTableHeaderScope`, and `TablerTableRowScope` classes (in `models/TablerTableData.kt` or a new `models/TablerTableDsl.kt`).
- Add `@Composable fun TablerTable(responsive, noWrap, stickyHeader, block: TablerTableScope.() -> Unit)` overload in `TablerTable.kt` using native Compose table nodes (fixed in stage 1).
- Keep the existing `TablerTable(data: TablerTableData)` overload unchanged for backward compatibility.
- Add a DSL-based test in `TablerTableTest.kt` that places a `TablerAvatar` inside a cell and asserts `contains("avatar")`.
- Update the Tagessieg example with a table demonstrating the DSL.
