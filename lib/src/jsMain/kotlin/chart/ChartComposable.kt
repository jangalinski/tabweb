package com.github.jangalinski.tabweb.chart

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TablerColors
import com.github.jangalinski.tabweb._foundation.TabwebComposable
import com.github.jangalinski.tabweb._foundation.TabwebDirection.Orientation
import com.github.jangalinski.tabweb._foundation.TabwebDirection.VERTICAL
import com.github.jangalinski.tabweb.chart.BarChart.Companion.BarSeries
import com.varabyte.kobweb.compose.ui.Modifier
import org.jetbrains.compose.web.css.CSSLengthValue
import org.jetbrains.compose.web.css.px

/**
 * Provides page-level composable entry points for chart components.
 *
 * The functions create the same typed component instances available through the chart companion
 * factories and then invoke those instances.
 */
interface ChartComposable : TabwebComposable {
  /**
   * Creates and renders a typed bar chart.
   *
   * @param categories category labels shared by every series.
   * @param series typed series aligned with [categories].
   * @param orientation bar orientation.
   * @param grouping grouping treatment for multiple series.
   * @param height chart height passed to ApexCharts.
   * @param dataLabels whether values are rendered on the bars.
   * @param borderRadius border radius applied to bars.
   * @param colors Tabler colors assigned to the series in order.
   * @param modifier additional attributes and styles applied to the chart root.
   * @return `Unit` after the chart has been emitted into the current composition.
   */
  @Composable
  fun barChart(
    categories: List<String>,
    series: List<BarSeries>,
    orientation: Orientation = VERTICAL,
    grouping: BarChart.Companion.BarGrouping = BarChart.Companion.BarGrouping.GROUPED,
    height: CSSLengthValue = 240.px,
    dataLabels: Boolean = false,
    borderRadius: Int = 3,
    colors: List<TablerColors> = listOf(
      TablerColors.BLUE,
      TablerColors.GREEN,
      TablerColors.AZURE,
      TablerColors.PURPLE,
      TablerColors.ORANGE,
    ),
    modifier: Modifier = Modifier,
  )
}
