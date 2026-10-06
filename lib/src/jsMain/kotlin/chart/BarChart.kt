package com.github.jangalinski.tabweb.chart

import com.github.jangalinski.tabweb._foundation.DynamicJson
import com.github.jangalinski.tabweb._foundation.TablerColors
import com.github.jangalinski.tabweb._foundation.TabwebDirection
import com.github.jangalinski.tabweb._foundation.TabwebDirection.Orientation
import com.github.jangalinski.tabweb._foundation.TabwebDirection.VERTICAL
import org.jetbrains.compose.web.css.CSSLengthValue
import org.jetbrains.compose.web.css.px
import kotlin.js.json


/**
 * Creates a typed bar-chart component instance.
 *
 * @param categories category labels shared by every series.
 * @param series series aligned with [categories].
 * @param orientation bar orientation passed to ApexCharts.
 * @param grouping bar grouping passed to ApexCharts.
 * @param height height passed to ApexCharts for the chart container.
 * @param dataLabels whether values should be rendered on the bars.
 * @param borderRadius border radius applied to bars.
 * @param colors tabler colors assigned to the series in order.
 */
data class BarChart(
  val categories: List<String>,
  val series: List<BarSeries>,
  val orientation: Orientation = VERTICAL,
  val grouping: BarGrouping = BarGrouping.GROUPED,
  val height: CSSLengthValue = 240.px,
  val dataLabels: Boolean = false,
  val borderRadius: Int = 3,
  val colors: List<TablerColors> = DEFAULT_COLORS
) : ApexChart {
  companion object {

    /**
     * Selects how multiple bar series are drawn within each category.
     *
     * `GROUPED` places series beside one another; `STACKED` combines their values
     * into a single category bar.
     */
    enum class BarGrouping {
      GROUPED,
      STACKED,
    }

    /**
     * A typed series of values aligned by index with [BarChart.categories].
     *
     * @property name label shown in the chart legend.
     * @property values numeric values rendered for the corresponding categories.
     */
    data class BarSeries(
      val name: String,
      val values: List<Number>
    ) : DynamicJson {
      override val json: dynamic by lazy {
        json(
          "name" to name,
          "data" to values.toTypedArray(),
        )
      }
    }

    private val DEFAULT_COLORS = listOf(
      TablerColors.BLUE,
      TablerColors.GREEN,
      TablerColors.AZURE,
      TablerColors.PURPLE,
      TablerColors.ORANGE,
    )
  }

  override val label: String = "Bar chart"
  override val options: dynamic by lazy {
    require(series.all { it.values.size == categories.size }) {
      "Every bar series must have one value per category."
    }
    require(borderRadius >= 0) { "Bar border radius must not be negative." }

    json(
      "chart" to json(
        "type" to "bar",
        "fontFamily" to "inherit",
        "height" to height.toString(),
        "parentHeightOffset" to 0,
        "stacked" to (grouping == BarGrouping.STACKED),
        "toolbar" to json("show" to false),
        "animations" to json("enabled" to false),
      ),
      "series" to series.map { it.json }.toTypedArray(),
      "xaxis" to json("categories" to categories.toTypedArray()),
      "plotOptions" to json(
        "bar" to json(
          "horizontal" to (orientation == TabwebDirection.HORIZONTAL),
          "borderRadius" to borderRadius,
        ),
      ),
      "dataLabels" to json("enabled" to dataLabels),
      "legend" to json("show" to (series.size > 1), "position" to "bottom"),
      "grid" to json("strokeDashArray" to 4),
      "colors" to colors.map { "var(--tblr-${it.value})" }.toTypedArray(),
    )
  }
}
