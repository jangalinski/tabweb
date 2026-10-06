package com.github.jangalinski.tabweb.site.pages

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb.Tabweb.barChart
import com.github.jangalinski.tabweb.Tabweb.cardDeck
import com.github.jangalinski.tabweb._foundation.TablerColors
import com.github.jangalinski.tabweb._foundation.TabwebDirection
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.css.GridWidth
import com.github.jangalinski.tabweb.chart.*
import com.github.jangalinski.tabweb.chart.BarChart.Companion.BarGrouping
import com.github.jangalinski.tabweb.chart.BarChart.Companion.BarSeries
import com.github.jangalinski.tabweb.element.Tooltip
import com.github.jangalinski.tabweb.site.SiteRoutes
import com.github.jangalinski.tabweb.site.siteLayoutData
import com.github.jangalinski.tabweb.site.sitePageMeta
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

private val barCategories = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

private fun barSeries(name: String, vararg values: Int): BarSeries =
  BarSeries(name, values.map { it as Number })

private fun barComponent(
  series: List<BarSeries>,
  grouping: BarGrouping = BarGrouping.GROUPED,
  orientation: TabwebDirection.Orientation = TabwebDirection.VERTICAL,
  colors: List<TablerColors> = listOf(TablerColors.GREEN),
): BarChart = BarChart(
  categories = barCategories,
  series = series,
  grouping = grouping,
  orientation = orientation,
  colors = colors,
)

@InitRoute
fun initBarChartPage(ctx: InitRouteContext) {
  ctx.data.add(sitePageMeta("Bar charts", "The first chart family to receive a typed ApexCharts specification."))
  ctx.data.add(siteLayoutData(SiteRoutes.BarCharts))
}

/**
 * Shows the bar-chart variants that will become the first typed chart API.
 *
 * The examples currently use the site chart host as a rendering reference. They are deliberately
 * kept together so the page can migrate to the library-owned bar-chart specification once it is
 * implemented.
 */
@Page(routeOverride = SiteRoutes.BarCharts)
@Composable
fun BarChartPage() {
  cardDeck {
    col(width = GridWidth.FULL) {
      KDiv {
        H1 { Text("Bar chart specs") }
        P {
          Text(
            "These examples define the first typed ApexCharts surface: categories, series, " +
              "orientation, grouping, stacking, labels, and chart height. Tabler supplies the " +
              "series colors through the typed Tabler palette.",
          )
        }
      }
    }
    card(width = GridWidth.HALF, modifier = Tooltip("Basic bar chart").modifier) {
      header(title = "Basic", subtitle = "One series compared across categories.")
      body {
        barChart(
          categories = barCategories,
          series = listOf(barSeries("Completed", 12, 18, 16, 24, 21, 29, 33)),
          colors = listOf(TablerColors.GREEN),
          dataLabels = true
        )
      }
    }

    card(width = GridWidth.HALF) {
      header(title = "Horizontal", subtitle = "The same series with horizontal bars.")
      body {
        barComponent(
          listOf(barSeries("Completed", 12, 18, 16, 24, 21, 29, 33)),
          orientation = TabwebDirection.HORIZONTAL,
          colors = listOf(TablerColors.ORANGE),
        )()
      }
    }

    card(width = GridWidth.HALF) {
      header(title = "Grouped", subtitle = "Two series shown side by side.")
      body {
        barComponent(
          listOf(
            barSeries("Completed", 12, 18, 16, 24, 21, 29, 33),
            barSeries("Reopened", 5, 7, 6, 10, 8, 11, 9),
          ),
          colors = listOf(TablerColors.TEAL, TablerColors.PURPLE),
        )()
      }
    }

    card(width = GridWidth.HALF) {
      header(title = "Stacked", subtitle = "Series combined into each category total.")
      body {
        barComponent(
          listOf(
            barSeries("Completed", 12, 18, 16, 24, 21, 29, 33),
            barSeries("Reopened", 5, 7, 6, 10, 8, 11, 9),
          ),
          grouping = BarGrouping.STACKED,
          colors = listOf(TablerColors.GREEN, TablerColors.ORANGE),
        )()
      }
    }
  }
}
