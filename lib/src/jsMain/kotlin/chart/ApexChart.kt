package com.github.jangalinski.tabweb.chart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.github.jangalinski.tabweb._foundation.TabwebComponent
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.ariaLabel
import com.varabyte.kobweb.compose.ui.modifiers.attr
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.role
import kotlinx.browser.document
import kotlin.random.Random

interface ApexChart : TabwebComponent {

  val label : String

  val options: dynamic


  /**
   * Renders this bar chart through the shared ApexCharts host.
   *
   * @param modifier additional attributes and styles applied to the chart root.
   * @return `Unit` after the chart host has been emitted into the composition.
   */
  @Composable
  override fun invoke(modifier: Modifier) {
    val chartId = remember {
      "apex-chart-${Random.nextInt(Int.MAX_VALUE).toUInt()}"
    }

    KDiv(
      modifier = Modifier.classNames("chart-lg", "position-relative", "w-100")
        .then(Modifier.id(chartId))
        .then(Modifier.ariaLabel(label))
        .then(Modifier.role("img"))
        .then(modifier)
    )

    DisposableEffect(chartId, this) {
      val element = document.getElementById(chartId)
        ?: error("Chart container '$chartId' was not found")
      check(js("typeof ApexCharts !== 'undefined'") as Boolean) {
        "ApexCharts is not loaded; check the Tabler CDN script"
      }
      val chart = ApexCharts(element, options).apply {
        render()
      }
      onDispose { chart.destroy() }
    }
  }
}

/**
 * Represents the external ApexCharts JS.
 */
internal external class ApexCharts(element: dynamic, options: dynamic) {
  fun render(): dynamic
  fun destroy()
}
