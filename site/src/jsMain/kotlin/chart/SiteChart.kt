package com.github.jangalinski.tabweb.site.chart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.github.jangalinski.tabweb._app.LocalTablerAppState
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.attr
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import kotlinx.browser.document

private external class ApexCharts(element: dynamic, options: dynamic) {
  fun render(): dynamic
  fun destroy()
}

/**
 * Renders a chart in the site demos without adding a card wrapper.
 *
 * Recreates the ApexCharts instance when the theme changes and destroys it when the host leaves
 * the composition.
 *
 * @param id unique DOM id for this chart on the page.
 * @param label accessible description of the chart.
 * @param options fresh ApexCharts options for each chart instance.
 */
@Composable
fun SiteChart(id: String, label: String, options: () -> dynamic) {
  val theme = LocalTablerAppState.current.settings.theme
  KDiv(
    modifier = Modifier.classNames("chart-lg", "position-relative")
      .attr("id", id)
      .attr("role", "img")
      .attr("aria-label", label),
  )

  DisposableEffect(id, theme) {
    val element = document.getElementById(id) ?: error("Chart container '$id' was not found")
    check(js("typeof ApexCharts !== 'undefined'") as Boolean) {
      "ApexCharts is not loaded; check the Tabler CDN script"
    }
    val chart = ApexCharts(element, options())
    chart.render()
    onDispose { chart.destroy() }
  }
}
