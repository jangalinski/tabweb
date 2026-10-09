package com.github.jangalinski.tabweb.site.pages

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb.Tabweb.cardDeck
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.css.GridWidth
import com.github.jangalinski.tabweb.site.SiteRoutes
import com.github.jangalinski.tabweb.site.chart.SiteChart
import com.varabyte.kobweb.core.Page
import kotlin.js.json
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

private data class ChartExample(
  val id: String,
  val title: String,
  val description: String,
  val width: GridWidth = GridWidth.MD_HALF_LG_THIRD,
  val options: () -> dynamic,
)

private data class ChartSection(
  val title: String,
  val description: String,
  val examples: List<ChartExample>,
)

private val days = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
private val months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun")
private val hours = arrayOf("08:00", "10:00", "12:00", "14:00", "16:00", "18:00")
private val palette = arrayOf(
  "var(--tblr-primary)",
  "var(--tblr-teal)",
  "var(--tblr-orange)",
  "var(--tblr-purple)",
  "var(--tblr-azure)",
)

private fun series(name: String, vararg values: Int): dynamic =
  json("name" to name, "data" to values.toTypedArray())

private fun cartesian(
  type: String,
  categories: Array<String>,
  series: Array<dynamic>,
  curve: String = "straight",
  stacked: Boolean = false,
  horizontal: Boolean = false,
  dataLabels: Boolean = false,
): dynamic = json(
  "chart" to json(
    "type" to type,
    "fontFamily" to "inherit",
    "height" to 240,
    "parentHeightOffset" to 0,
    "stacked" to stacked,
    "toolbar" to json("show" to false),
    "animations" to json("enabled" to false),
  ),
  "series" to series,
  "xaxis" to json("categories" to categories),
  "stroke" to json("width" to 2, "curve" to curve),
  "plotOptions" to json("bar" to json("horizontal" to horizontal, "borderRadius" to 3)),
  "dataLabels" to json("enabled" to dataLabels),
  "legend" to json("show" to (series.size > 1), "position" to "bottom"),
  "grid" to json("strokeDashArray" to 4),
  "colors" to palette,
)

private fun circular(type: String, labels: Array<String>, values: Array<Int>): dynamic = json(
  "chart" to json(
    "type" to type,
    "fontFamily" to "inherit",
    "height" to 240,
    "animations" to json("enabled" to false),
  ),
  "series" to values,
  "labels" to labels,
  "colors" to palette,
  "legend" to json("show" to (type != "radialBar"), "position" to "bottom"),
)

private fun xyPoint(x: Number, y: Number): dynamic = json("x" to x, "y" to y)
private fun namedPoint(x: String, y: Number): dynamic = json("x" to x, "y" to y)

private val sections = listOf(
  ChartSection(
    "Overview",
    "A few common chart compositions in cards.",
    listOf(
      ChartExample("active-users", "Active users", "Daily activity across three devices.", GridWidth.FULL) {
        cartesian("line", days, arrayOf(
          series("Desktop", 48, 62, 58, 75, 83, 72, 91),
          series("Mobile", 36, 45, 43, 54, 65, 60, 72),
          series("Tablet", 12, 14, 18, 16, 21, 24, 26),
        ), curve = "smooth")
      },
      ChartExample("site-traffic", "Site traffic", "Total and new visitors through the week.", GridWidth.FULL) {
        cartesian("line", days, arrayOf(
          series("Total visits", 81, 94, 86, 104, 117, 122, 135),
          series("Unique visitors", 57, 65, 60, 74, 85, 83, 92),
          series("New users", 20, 26, 22, 30, 37, 34, 42),
        ), curve = "smooth")
      },
      ChartExample("social-referrals", "Social referrals", "Traffic arriving from social channels.", GridWidth.FULL) {
        cartesian("line", days, arrayOf(
          series("Community", 26, 35, 30, 40, 46, 51, 58),
          series("Shared links", 18, 21, 25, 27, 31, 29, 35),
          series("Campaigns", 8, 12, 10, 18, 16, 22, 25),
        ), curve = "smooth")
      },
      ChartExample("sprint-velocity", "Sprint velocity", "Points completed per sprint.", GridWidth.FULL) {
        cartesian("bar", arrayOf("Sprint 1", "Sprint 2", "Sprint 3", "Sprint 4", "Sprint 5"),
          arrayOf(series("Points", 24, 31, 27, 38, 42)))
      },
    ),
  ),
  ChartSection(
    "Line charts",
    "See how values change over a sequence of dates or categories.",
    listOf(
      ChartExample("smooth-line", "Line chart", "A smooth daily trend.") {
        cartesian("line", days, arrayOf(series("Resolved", 12, 18, 16, 24, 21, 29, 33)), curve = "smooth")
      },
      ChartExample("straight-line", "Straight line", "Connect each sample directly.") {
        cartesian("line", days, arrayOf(series("Resolved", 12, 18, 16, 24, 21, 29, 33)))
      },
      ChartExample("stepped-line", "Stepped line", "Show values changing in discrete steps.") {
        cartesian("line", days, arrayOf(series("Resolved", 12, 18, 16, 24, 21, 29, 33)), curve = "stepline")
      },
      ChartExample("labeled-line", "Data labels", "Show each observation alongside the line.") {
        cartesian("line", days, arrayOf(series("Resolved", 12, 18, 16, 24, 21, 29, 33)), dataLabels = true)
      },
      ChartExample("issue-activity", "Issue activity", "Compare opened and closed issues.", GridWidth.LG_TWO_THIRDS) {
        cartesian("line", days, arrayOf(
          series("Opened", 11, 15, 14, 20, 18, 23, 27),
          series("Closed", 7, 12, 16, 15, 19, 21, 24),
        ))
      },
      ChartExample("revenue-plans", "Revenue by plan", "Compare recurring revenue by tier.") {
        cartesian("line", months, arrayOf(
          series("Basic", 12, 14, 17, 18, 21, 23),
          series("Plus", 26, 29, 34, 39, 41, 46),
          series("Business", 42, 45, 49, 52, 59, 65),
        ))
      },
      ChartExample("signup-channels", "Signups by channel", "Organic and referred signups.") {
        val config = cartesian("line", months, arrayOf(
          series("Organic", 31, 36, 32, 43, 46, 52),
          series("Referral", 13, 17, 20, 19, 25, 30),
        ))
        config.stroke.dashArray = arrayOf(0, 5)
        config
      },
      ChartExample("cpu-load", "CPU load", "Compare two workloads through the day.") {
        cartesian("line", hours, arrayOf(
          series("Web", 34, 42, 57, 49, 63, 51),
          series("Jobs", 22, 31, 27, 39, 46, 35),
        ), curve = "smooth")
      },
      ChartExample("temperature", "Average temperature", "Compare monthly readings.", GridWidth.LG_HALF) {
        cartesian("line", months, arrayOf(
          series("City A", 8, 11, 16, 21, 25, 27),
          series("City B", 4, 7, 12, 17, 20, 23),
        ), curve = "smooth")
      },
      ChartExample("instances", "Running instances", "Track changes across a workday.", GridWidth.LG_HALF) {
        cartesian("line", hours, arrayOf(series("Instances", 4, 6, 6, 9, 7, 5)), curve = "stepline")
      },
    ),
  ),
  ChartSection(
    "Area charts",
    "Filled trends make volumes and contributions easier to compare.",
    listOf(
      ChartExample("single-area", "Area chart", "A single growing volume.") {
        cartesian("area", days, arrayOf(series("Completed", 15, 22, 19, 28, 25, 35, 39)), curve = "smooth")
      },
      ChartExample("two-areas", "Two areas", "Compare two overlapping volumes.") {
        cartesian("area", days, arrayOf(
          series("Completed", 15, 22, 19, 28, 25, 35, 39),
          series("Reopened", 6, 9, 7, 11, 8, 12, 10),
        ), curve = "smooth")
      },
      ChartExample("stacked-areas", "Stacked areas", "See each part of the combined total.") {
        cartesian("area", days, arrayOf(
          series("Completed", 15, 22, 19, 28, 25, 35, 39),
          series("Reopened", 6, 9, 7, 11, 8, 12, 10),
        ), stacked = true)
      },
      ChartExample("device-sessions", "Sessions by device", "Desktop and mobile traffic.") {
        cartesian("area", months, arrayOf(
          series("Desktop", 42, 46, 44, 52, 57, 60),
          series("Mobile", 31, 34, 39, 41, 47, 54),
        ))
      },
      ChartExample("bandwidth", "Bandwidth usage", "Ingress and egress volumes.") {
        cartesian("area", months, arrayOf(
          series("Ingress", 28, 32, 37, 39, 44, 49),
          series("Egress", 19, 25, 28, 30, 36, 40),
        ), curve = "smooth")
      },
      ChartExample("storage", "Storage by bucket", "Stacked usage across two buckets.") {
        cartesian("area", months, arrayOf(
          series("Media", 35, 39, 44, 47, 53, 60),
          series("Backups", 12, 16, 18, 21, 25, 27),
        ), stacked = true, curve = "smooth")
      },
    ),
  ),
  ChartSection(
    "Bar charts",
    "Compare categories vertically, horizontally, or as parts of a total.",
    listOf(
      ChartExample("basic-bars", "Bar chart", "Completed work by day.") {
        cartesian("bar", days, arrayOf(series("Completed", 12, 18, 16, 24, 21, 29, 33)))
      },
      ChartExample("horizontal-bars", "Horizontal bars", "The same data on a horizontal axis.") {
        cartesian("bar", days, arrayOf(series("Completed", 12, 18, 16, 24, 21, 29, 33)), horizontal = true)
      },
      ChartExample("grouped-bars", "Grouped bars", "Two series side by side.") {
        cartesian("bar", days, arrayOf(
          series("Completed", 12, 18, 16, 24, 21, 29, 33),
          series("Reopened", 5, 7, 6, 10, 8, 11, 9),
        ))
      },
      ChartExample("stacked-bars", "Stacked bars", "Show the total for each day.") {
        cartesian("bar", days, arrayOf(
          series("Completed", 12, 18, 16, 24, 21, 29, 33),
          series("Reopened", 5, 7, 6, 10, 8, 11, 9),
        ), stacked = true)
      },
      ChartExample("cache-status", "Semantic colors", "Successful, stale, and failed requests.") {
        val config = cartesian("bar", days, arrayOf(
          series("Hit", 43, 46, 50, 48, 55, 57, 62),
          series("Miss", 12, 14, 11, 16, 13, 15, 12),
          series("Error", 3, 2, 4, 3, 2, 3, 1),
        ), stacked = true)
        config.colors = arrayOf("var(--tblr-green)", "var(--tblr-orange)", "var(--tblr-red)")
        config
      },
      ChartExample("signup-funnel", "Signup funnel", "Conversion through successive steps.") {
        val config = cartesian("bar", arrayOf("Visit", "Explore", "Register", "Trial", "Subscribe"),
          arrayOf(series("Visitors", 100, 76, 49, 31, 18)), horizontal = true)
        config.plotOptions.bar.isFunnel = true
        config
      },
    ),
  ),
  ChartSection(
    "Circular charts",
    "Show shares of a whole or progress toward a target.",
    listOf(
      ChartExample("traffic-sources", "Traffic sources", "How visitors reached the site.") {
        circular("donut", arrayOf("Search", "Direct", "Referral", "Social"), arrayOf(45, 30, 15, 10))
      },
      ChartExample("email-campaign", "Email campaign", "Delivery, open, and click-through rates.") {
        circular("radialBar", arrayOf("Delivered", "Opened", "Clicked"), arrayOf(88, 62, 37))
      },
      ChartExample("regional-sales", "Sales by region", "The regional share of sales.") {
        circular("polarArea", arrayOf("North", "South", "East", "West"), arrayOf(38, 25, 29, 19))
      },
    ),
  ),
  ChartSection(
    "Other charts",
    "More ways to compare relationships, distributions, and schedules.",
    listOf(
      ChartExample("spend-leads", "Spend and leads", "Columns and lines on one plot.", GridWidth.LG_TWO_THIRDS) {
        cartesian("line", months, arrayOf(
          json("name" to "Spend", "type" to "column", "data" to arrayOf(32, 37, 41, 45, 50, 55)),
          json("name" to "Leads", "type" to "line", "data" to arrayOf(23, 29, 35, 34, 43, 49)),
        ))
      },
      ChartExample("plan-comparison", "Plan comparison", "Score plans across several capabilities.") {
        cartesian("radar", arrayOf("Storage", "Support", "Speed", "Users", "Reports", "Access"), arrayOf(
          series("Plus", 55, 70, 60, 75, 45, 65),
          series("Business", 85, 90, 80, 95, 75, 90),
        ))
      },
      ChartExample("load-time", "Page load time", "Samples across two device types.") {
        cartesian("scatter", emptyArray(), arrayOf(
          json("name" to "Desktop", "data" to arrayOf(xyPoint(1, 140), xyPoint(2, 120), xyPoint(3, 160), xyPoint(4, 135))),
          json("name" to "Mobile", "data" to arrayOf(xyPoint(1, 280), xyPoint(2, 310), xyPoint(3, 245), xyPoint(4, 330))),
        ))
      },
      ChartExample("release-roadmap", "Release roadmap", "A timeline for project phases.", GridWidth.LG_TWO_THIRDS) {
        val config = cartesian("rangeBar", emptyArray(), arrayOf(json(
          "name" to "Phases",
          "data" to arrayOf(
            json("x" to "Research", "y" to arrayOf(1767571200000.0, 1770681600000.0)),
            json("x" to "Design", "y" to arrayOf(1769904000000.0, 1773532800000.0)),
            json("x" to "Build", "y" to arrayOf(1772323200000.0, 1779235200000.0)),
          ),
        )), horizontal = true)
        config.xaxis.type = "datetime"
        config
      },
      ChartExample("campaign-performance", "Campaign performance", "Reach, conversion, and relative size.") {
        cartesian("bubble", emptyArray(), arrayOf(
          json("name" to "Search", "data" to arrayOf(
            json("x" to 12, "y" to 25, "z" to 18),
            json("x" to 28, "y" to 42, "z" to 25),
            json("x" to 42, "y" to 36, "z" to 32),
          )),
          json("name" to "Social", "data" to arrayOf(
            json("x" to 18, "y" to 31, "z" to 14),
            json("x" to 36, "y" to 52, "z" to 22),
          )),
        ))
      },
      ChartExample("folder-storage", "Storage by folder", "Relative space used by folders.") {
        val config = cartesian("treemap", emptyArray(), arrayOf(json(
          "name" to "Storage",
          "data" to arrayOf(
            namedPoint("Photos", 44),
            namedPoint("Videos", 32),
            namedPoint("Documents", 18),
            namedPoint("Backups", 27),
          ),
        )))
        // ApexCharts parses treemap colors numerically, so CSS variables are not supported here.
        config.colors = arrayOf("#206bc4", "#2fb344", "#ae3ec9", "#f76707")
        config
      },
      ChartExample("response-times", "API response times", "Monthly latency distribution.") {
        cartesian("boxPlot", emptyArray(), arrayOf(json(
          "name" to "Response (ms)",
          "data" to arrayOf(
            json("x" to "Jan", "y" to arrayOf(80, 110, 140, 175, 220)),
            json("x" to "Feb", "y" to arrayOf(75, 100, 125, 165, 200)),
            json("x" to "Mar", "y" to arrayOf(90, 115, 145, 185, 235)),
            json("x" to "Apr", "y" to arrayOf(70, 95, 120, 150, 195)),
          ),
        )))
      },
    ),
  ),
)

/** Displays the reference chart families using site-local ApexCharts configurations. */
@Page(routeOverride = SiteRoutes.Charts)
@Composable
fun ChartPage() {
  cardDeck {
    sections.forEach { section ->
      col(width = GridWidth.FULL) {
        KDiv {
          H2 { Text(section.title) }
          P { Text(section.description) }
        }
      }
      section.examples.forEach { example ->
        card(width = example.width) {
          header(title = example.title, subtitle = example.description)
          body {
            SiteChart(
              id = "site-chart-${example.id}",
              label = example.title,
              options = example.options,
            )
          }
        }
      }
    }
  }
}
