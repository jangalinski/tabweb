package com.github.jangalinski.tabweb.chart

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TablerColors
import com.github.jangalinski.tabweb._foundation.TabwebComponentDsl
import com.github.jangalinski.tabweb._foundation.TabwebDirection.Orientation
import com.github.jangalinski.tabweb.chart.BarChart.Companion.BarSeries
import com.varabyte.kobweb.compose.ui.Modifier
import org.jetbrains.compose.web.css.CSSLengthValue

/**
 * The chart DSL implementation delegated through [com.github.jangalinski.tabweb.Tabweb].
 */
internal data object ChartDsl : TabwebComponentDsl, ChartComposable {

  /**
   * Creates and renders a [BarChart] from the supplied typed values.
   *
   * @param categories labels shared by every series.
   * @param series typed series aligned with [categories].
   * @param orientation orientation of the bars.
   * @param grouping grouping treatment for multiple series.
   * @param height chart height passed to ApexCharts.
   * @param dataLabels whether values are rendered on the bars.
   * @param borderRadius border radius applied to the bars.
   * @param colors Tabler colors assigned to the series in order.
   * @param modifier additional attributes and styles applied to the chart root.
   * @return `Unit` after the chart has been emitted into the current composition.
   */
  @Composable
  override fun barChart(
    categories: List<String>,
    series: List<BarSeries>,
    orientation: Orientation,
    grouping: BarChart.Companion.BarGrouping,
    height: CSSLengthValue,
    dataLabels: Boolean,
    borderRadius: Int,
    colors: List<TablerColors>,
    modifier: Modifier,
  ) {
    BarChart(
      categories = categories,
      series = series,
      orientation = orientation,
      grouping = grouping,
      height = height,
      dataLabels = dataLabels,
      borderRadius = borderRadius,
      colors = colors,
    )(modifier)
  }

}
