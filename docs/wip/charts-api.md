# Charts API WIP

Status: design sketch. Revisit before implementing [issue #6](https://github.com/jangalinski/tabweb/issues/6).

## Context

The repository has a chart package, an existing ApexCharts gallery in
`site/src/jsMain/kotlin/pages/ChartPage.kt`, and a focused bar-chart workbench
in `BarChartPage.kt`. Issue #6 asks for ApexCharts bar-chart support.

The API should follow the established Tabweb component/facade pattern while
keeping the imperative ApexCharts lifecycle behind one internal host.

## Public shape

The component interface is the typed specification and the renderable
component. It declares the supported values and defaults directly, like other
Tabweb components:

```kotlin
interface BarChart : TabwebComponent {
  val categories: List<String>
  val series: List<BarSeries>
  val orientation: TabwebDirection.Orientation
  val grouping: BarGrouping
  val height: CSSLengthValue
  val dataLabels: Boolean
  val borderRadius: Int
  val colors: List<TablerColors>

  companion object {
    operator fun invoke(
      categories: List<String>,
      series: List<BarSeries>,
      orientation: TabwebDirection.Orientation = TabwebDirection.VERTICAL,
      grouping: BarGrouping = BarGrouping.GROUPED,
      height: CSSLengthValue = 240.px,
      dataLabels: Boolean = false,
      borderRadius: Int = 3,
      colors: List<TablerColors> = defaultColors,
    ): BarChart
  }
}
```

The two supported call styles create/use the same component shape:

```kotlin
BarChart(categories, series, grouping = BarGrouping.STACKED)()

barChart(
  categories = categories,
  series = series,
  grouping = BarGrouping.STACKED,
)
```

`ChartComposable` and `ChartDsl` provide the scoped function, and `Tabweb`
delegates it as the application facade. There is no separate public
`ApexBarChartSpec`: a secondary data/transport model can be introduced later
if generation or serialization requires one.

## Initial typed scope

Start with the options demonstrated by the workbench:

- `BarSeries(name, values)`;
- vertical or horizontal orientation;
- grouped or stacked series;
- typed CSS length height, such as `240.px`;
- data labels;
- border radius;
- typed Tabler colors.

Defer annotations, responsive overrides, arbitrary Apex event callbacks, and
specialized options until real examples justify them.

## Colors and sizing

Tabler's vendor stylesheet defines the `--tblr-chart-1` through
`--tblr-chart-5` tokens, but the current ApexCharts runtime emits SVG bar fills
from its chart options. `BarChart` therefore accepts generated `TablerColors`
and emits `var(--tblr-...)` values. The palette remains theme-aware while the
bar fills are explicitly configured.

`CSSLengthValue` keeps dimensions typed and accepts Compose Web values such as
`240.px`. The host supplies the chart container and width while the component
accepts a root `Modifier` for layout integration.

## Lifecycle boundary

The internal ApexCharts host owns the browser instance. It must:

- create the chart after its container exists;
- dispose the chart when the component leaves composition;
- support multiple charts on one page;
- remain safe during card and route recomposition;
- recreate or update charts when relevant theme/configuration changes.

The current first slice recreates on component identity changes. Update-in-place
behavior can be added after the typed surface is proven. JavaScript handles,
DOM references, and Compose state do not belong in `BarChart` properties.

## Site gallery migration

`ChartPage` remains the broad visual reference. `BarChartPage` is the focused
acceptance workbench and should progressively exercise the library component
and facade paths. Unsupported chart families may remain dynamic in the gallery
until their own typed component APIs are justified.

Cards remain page composition, not chart responsibility:

```kotlin
card {
  header(title = "Completed work")
  body {
    barChart(categories, series)
  }
}
```

## Next decisions

1. Which additional bar options are justified by the gallery?
2. Should theme changes initially recreate charts or call `updateOptions`?
3. Which gallery examples are acceptance fixtures for bar and donut support?
4. When should donut move to the same component/host architecture?

The guiding constraint is: one typed component contract, one renderer/host,
and a facade/DSL that constructs the same component instance.
