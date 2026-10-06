package com.github.jangalinski.tabweb.chart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.compose.documentElementById
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.attr
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import kotlin.js.json
import kotlin.random.Random


/**
 * A labeled numeric slice used by [ApexDonutChart].
 *
 * @param label text shown for the slice in the chart legend.
 * @param value numeric value determining the slice size.
 */
data class DonutSlice(
  val label: String,
  val value: Int,
)

/**
 * Renders a simple ApexCharts donut chart inside a Tabler-friendly container.
 *
 * The ApexCharts runtime is expected to be available globally through the library script layer.
 *
 * @param slices labeled numeric slices rendered by the chart.
 * @param modifier additional attributes and styles applied to the chart root.
 * @param heightPx chart height in CSS pixels.
 * @return `Unit` after the chart host has been emitted into the composition.
 */
@Composable
fun ApexDonutChart(
  slices: List<DonutSlice>,
  modifier: Modifier = Modifier,
  heightPx: Int = 280,
) {
  val chartId = remember {
    "apex-donut-${Random.nextInt(Int.MAX_VALUE).toUInt()}"
  }

  Box(modifier = modifier.classNames("w-100")) {
    KDiv(
      modifier = Modifier
        .attr("id", chartId)
        .attr("style", "width: 100%; height: ${heightPx}px;"),
    ) {}
  }

  LaunchedEffect(chartId, slices) {
    val element = documentElementById(chartId) ?: return@LaunchedEffect
    val labels = slices.map { it.label }
    val series = slices.map { it.value }
    val options = json(
      "chart" to json(
        "type" to "donut",
        "toolbar" to json("show" to false),
      ),
      "labels" to labels.toTypedArray(),
      "series" to series.toTypedArray(),
      "legend" to json("show" to true),
      "dataLabels" to json("enabled" to false),
      "stroke" to json("width" to 2),
      "plotOptions" to json(
        "pie" to json(
          "donut" to json(
            "size" to "70%",
          ),
        ),
      ),
    )

    ApexCharts(element, options).render()
  }
}
