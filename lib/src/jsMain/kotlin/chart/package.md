# Package com.github.jangalinski.tabweb.chart

Small chart wrappers that integrate browser-side JavaScript chart libraries with Kobweb pages while retaining a Kotlin-first API.

Charts expose typed component interfaces whose properties declare the supported values and defaults.
The `BarChart` component models categories, typed `BarSeries`, orientation, grouping, sizing,
labels, radius, and a `TablerColors` palette. Its companion creates anonymous implementations,
matching the other Tabweb components.

Chart dimensions use Compose Web's `CSSLengthValue` (for example, `240.px`)
and are passed through to ApexCharts as CSS length strings.

`BarChart(...)()` is the canonical renderable component-instance path, while
`ChartComposable.barChart(...)` is the scoped DSL entry point. Both create/use the same component
shape and delegate to the same internal ApexCharts host, which owns the DOM container, creates the
browser-side chart after composition, and destroys it on disposal. This keeps chart values reusable
and composable inside cards without leaking JavaScript handles or Compose state into the component
properties.

Chart colors are passed as `var(--tblr-...)` values from the generated Tabler color vocabulary. This
keeps ApexCharts' SVG fills aligned with the active Tabler theme while still making the palette
explicit enough for the ApexCharts runtime.

See the [Tabler Chart plugin](https://docs.tabler.io/ui/plugins/chart).
