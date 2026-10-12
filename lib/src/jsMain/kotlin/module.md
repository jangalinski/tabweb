# Module tabweb

`tabweb` provides Tabler-inspired UI concepts for Kotlin/JS Kobweb applications.

The library contributes Tabler CSS, Tabler Icons CSS, Tabler JavaScript, and ApexCharts through Kobweb library metadata. Applications can use the components without copying those assets into each site.

Public UI APIs are organized by Tabler concept, for example `navbar`, `card`, `table`, and `avatar`. Application integration belongs to `_app`; shared DOM, semantic, and CSS infrastructure belongs to `_foundation`.

For tables, use `TabwebTable(data: TabwebTableData)` for structured data, or `TabwebTable { header { } row { cell { } } }` when cells require arbitrary composable content.
