package com.github.jangalinski.tabweb.site.pages

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb.Tabweb.cardRow
import com.github.jangalinski.tabweb.Tabweb.table
import com.github.jangalinski.tabweb._foundation.Image
import com.github.jangalinski.tabweb._foundation.Link
import com.github.jangalinski.tabweb._foundation.Url
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.compose.KH2
import com.github.jangalinski.tabweb._foundation.compose.KP
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.css.GridWidth
import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb.card.CardLinkType
import com.github.jangalinski.tabweb.card.CardRibbon
import com.github.jangalinski.tabweb.card.CardRibbonPosition
import com.github.jangalinski.tabweb.card.CardRotate
import com.github.jangalinski.tabweb.card.CardSize
import com.github.jangalinski.tabweb.card.CardStamp
import com.github.jangalinski.tabweb.card.CardStampSize
import com.github.jangalinski.tabweb.card.CardStatus
import com.github.jangalinski.tabweb.icon.TablerIcon
import com.github.jangalinski.tabweb.site.SiteRoutes
import com.github.jangalinski.tabweb.site.chart.SiteChart
import com.github.jangalinski.tabweb.table.TableResponsive
import com.varabyte.kobweb.core.Page
import kotlin.js.json

private const val lorem = "Lorem ipsum dolor sit amet, consectetur adipisicing elit. " +
  "Accusamus architecto beatae consequatur doloribus eligendi, enim expedita facilis " +
  "illo impedit labore magni minus molestias neque nostrum, pariatur quas sed sint vitae."

private fun photo(seed: String) = "https://picsum.photos/seed/$seed/800/450"

private val cardsLink = object : Link {
  override val href = Url(SiteRoutes.Cards)
  override val text = "Card examples"
}

private fun chartOptions(type: String): dynamic = json(
  "chart" to json(
    "type" to type,
    "height" to 240,
    "fontFamily" to "inherit",
    "toolbar" to json("show" to false),
    "animations" to json("enabled" to false),
  ),
  "series" to arrayOf(
    json("name" to "Visits", "data" to arrayOf(18, 25, 21, 34, 30, 42)),
    json("name" to "Orders", "data" to arrayOf(9, 13, 17, 15, 24, 28)),
  ),
  "xaxis" to json("categories" to arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun")),
  "colors" to arrayOf("var(--tblr-primary)", "var(--tblr-teal)"),
  "stroke" to json("width" to 2),
  "dataLabels" to json("enabled" to false),
)

@Composable
private fun section(title: String, description: String) {
  KDiv {
    KH2 { KText(title) }
    KP { KText(description) }
  }
}

/** Displays card combinations to evaluate where the card DSL needs stricter boundaries. */
@Page(routeOverride = SiteRoutes.ExtremeCards)
@Composable
fun ExtremeCardsPage() {
  section(
    "Extreme cards",
    "Fourteen deliberately crowded cards. Compare the title, image, data, and decoration collisions before narrowing the API.",
  )

  section("Titles meet images", "Header titles and body titles are both legal, even when images compete for space.")
  cardRow {
    card(
      width = GridWidth.MD_HALF_LG_QUARTER,
      stamp = CardStamp(TablerIcon.TI_STAR, BackgroundColor.BASE.YELLOW),
    ) {
      imageTop(photo("extreme-top-stamp"), "Landscape above a stamped card")
      header(title = "Header title", subtitle = "Header subtitle")
      body {
        title("Another title in the body")
        subtitle("And a second subtitle")
        KP { KText(lorem) }
      }
    }
    card(
      width = GridWidth.MD_HALF_LG_QUARTER,
      ribbon = CardRibbon(text = "FEATURED", color = BackgroundColor.SEMANTIC.DANGER),
    ) {
      header(title = "Header before image", subtitle = "Does the visual order still make sense?")
      imageTop(photo("extreme-header-image"), "Photo between header and body")
      body {
        title("Body title after image")
        KP { KText(lorem) }
      }
    }
    card(width = GridWidth.MD_HALF_LG_QUARTER, status = CardStatus.bottom(BackgroundColor.SEMANTIC.SUCCESS)) {
      header(title = "Bottom image", subtitle = "Footer and image both want the last word")
      body {
        title("Body title")
        KP { KText(lorem) }
      }
      imageBottom(photo("extreme-bottom"), "Photo beneath the body")
      footer(transparent = true) { KText("Footer after the bottom image") }
    }
    card(width = GridWidth.MD_HALF_LG_QUARTER, size = CardSize.SM) {
      header(title = "Image inside body", subtitle = "An inline image rather than a card-edge image")
      body {
        title("Before the photo")
        KP { KText(lorem) }
        Image.Resource(
          url = photo("extreme-body"),
          modifier = cssClass("img-fluid"),
          altText = "Photo inside card body",
        )()
        KP { KText("After the photo") }
      }
    }
  }

  section("Data versus decoration", "Charts and tables compete with headings, stamps, and footers.")
  cardRow {
    card(
      width = GridWidth.LG_HALF,
      stamp = CardStamp(TablerIcon.TI_CHART_LINE, BackgroundColor.BASE.AZURE, CardStampSize.LG),
      status = CardStatus.top(BackgroundColor.SEMANTIC.PRIMARY),
    ) {
      header(title = "Chart + stamp", subtitle = "The watermark occupies chart space")
      body {
        title("Visits, again")
        SiteChart("extreme-chart-line", "Visits and orders by month", { chartOptions("line") })
      }
      footer { KText("The chart has both a header title and a body title.") }
    }
    card(
      width = GridWidth.LG_HALF,
      ribbon = CardRibbon(text = "LIVE", position = CardRibbonPosition.TOP),
    ) {
      imageTop(photo("extreme-chart-image"), "Photo above a chart")
      header(title = "Image + chart", subtitle = "Two competing hero visuals")
      body {
        SiteChart("extreme-chart-area", "Visits and orders area chart", { chartOptions("area") })
      }
    }
    card(width = GridWidth.LG_HALF, stamp = CardStamp(TablerIcon.TI_TABLE, BackgroundColor.BASE.PURPLE)) {
      header(title = "Table + stamp", subtitle = "A dense card with no body wrapper")
      table(cardTable = true, hover = true) {
        header { cell("Plan"); cell("Users"); cell("Revenue") }
        body {
          row { cell("Basic"); cell("48"); cell("$480") }
          row { cell("Plus"); cell("32"); cell("$960") }
          row { cell("Business"); cell("12"); cell("$1,200") }
        }
      }
      footer { KText("The stamp sits behind the table.") }
    }
    card(
      width = GridWidth.LG_HALF,
      status = CardStatus.start(BackgroundColor.SEMANTIC.DANGER),
      ribbon = CardRibbon(text = "AUDIT", position = CardRibbonPosition.END),
    ) {
      header(title = "Table in body", subtitle = "Extra headings around a compact table")
      body {
        title("A second report title")
        subtitle("Two rows of data, then explanatory copy")
        table(sm = true, striped = true, responsive = TableResponsive.ALWAYS) {
          header { cell("Stage"); cell("Count") }
          body {
            row { cell("Open"); cell("14") }
            row { cell("Closed"); cell("27") }
          }
        }
        KP { KText(lorem) }
      }
    }
  }

  section("Too many switches", "Status, ribbons, stamps, progress, rotation, and links can stack up.")
  cardRow {
    card(
      width = GridWidth.MD_HALF_LG_QUARTER,
      stacked = true,
      rotate = CardRotate.START,
      ribbon = CardRibbon(text = "NEW", color = BackgroundColor.BASE.ORANGE),
      status = CardStatus.top(BackgroundColor.SEMANTIC.WARNING),
    ) {
      progress(72, BackgroundColor.SEMANTIC.SUCCESS)
      header(title = "Stacked + rotated", subtitle = "Four treatments on one card")
      body { KP { KText(lorem) } }
    }
    card(
      width = GridWidth.MD_HALF_LG_QUARTER,
      inactive = true,
      stamp = CardStamp(TablerIcon.TI_BELL, BackgroundColor.BASE.YELLOW, CardStampSize.LG),
    ) {
      imageTop(photo("extreme-inactive"), "Photo in an inactive stamped card")
      header(title = "Inactive image + stamp", subtitle = "Muted content still has a watermark")
      body { KP { KText(lorem) } }
    }
    card(
      width = GridWidth.MD_HALF_LG_QUARTER,
      link = cardsLink,
      linkType = CardLinkType.POP,
      ribbon = CardRibbon(text = "LINK", position = CardRibbonPosition.TOP),
      status = CardStatus.end(BackgroundColor.SEMANTIC.INFO),
    ) {
      header(title = "Whole-card link", subtitle = "Pop, ribbon, status, and image")
      imageTop(photo("extreme-linked"), "Photo in a linked card")
      body { KP { KText("Open the regular card examples. $lorem") } }
    }
    card(
      width = GridWidth.MD_HALF_LG_QUARTER,
      active = true,
      borderless = true,
      stamp = CardStamp(TablerIcon.TI_STAR, BackgroundColor.BASE.GREEN),
      ribbon = CardRibbon(text = "ACTIVE", position = CardRibbonPosition.BOTTOM),
    ) {
      header(title = "Active + borderless", subtitle = "What does a ribbon attach to?")
      body {
        title("Another title")
        KP { KText(lorem) }
      }
      footer(borderless = true) { KText("Borderless footer") }
    }
  }

  section("Kitchen sink", "Long content and nested cards push the same API beyond a single clear purpose.")
  cardRow {
    card(
      width = GridWidth.LG_HALF,
      size = CardSize.LG,
      stamp = CardStamp(TablerIcon.TI_STAR, BackgroundColor.BASE.ORANGE, CardStampSize.LG),
      ribbon = CardRibbon(text = "ALL IN", color = BackgroundColor.SEMANTIC.DANGER),
      status = CardStatus.start(BackgroundColor.SEMANTIC.SUCCESS),
      stacked = true,
    ) {
      progress(55, BackgroundColor.SEMANTIC.PRIMARY)
      imageTop(photo("extreme-kitchen"), "Photo on the kitchen-sink card")
      header(title = "Everything at once", subtitle = "Stamp, ribbon, image, chart, table, progress")
      body {
        title("Yet another title")
        subtitle("And another subtitle")
        KP { KText(lorem) }
        SiteChart("extreme-chart-kitchen", "Kitchen-sink visits chart", { chartOptions("bar") })
        table(sm = true) {
          header { cell("Metric"); cell("Value") }
          body {
            row { cell("Visits"); cell("42") }
            row { cell("Orders"); cell("28") }
          }
        }
      }
      footer { KText("Footer after everything else") }
    }
    card(width = GridWidth.LG_HALF, stamp = CardStamp(TablerIcon.TI_BOX, BackgroundColor.BASE.TEAL)) {
      header(title = "Nested + scrollable", subtitle = "A card containing more cards and long copy")
      body(scrollable = true) {
        title("Body title")
        KP { KText(lorem) }
        KP { KText(lorem) }
        cardRow {
          card(width = GridWidth.LG_HALF, status = CardStatus.top(BackgroundColor.SEMANTIC.INFO)) {
            header(title = "Nested image")
            imageTop(photo("extreme-nested"), "Photo in a nested card")
            body { KText(lorem) }
          }
          card(width = GridWidth.LG_HALF, stamp = CardStamp(TablerIcon.TI_STAR, BackgroundColor.BASE.GREEN)) {
            header(title = "Nested stamp", subtitle = "Inside an already stamped parent")
            body { KText(lorem) }
          }
        }
        KP { KText(lorem) }
      }
      footer(transparent = true) { KText("Nested cards above a footer") }
    }
  }
}
