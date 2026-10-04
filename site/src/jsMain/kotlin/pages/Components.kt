package com.github.jangalinski.tabweb.site.pages

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb.Tabweb.cardDeck
import com.github.jangalinski.tabweb.icon.TablerIcon
import com.github.jangalinski.tabweb.site.SiteRoutes
import com.github.jangalinski.tabweb.site.siteLayoutData
import com.github.jangalinski.tabweb.site.sitePageMeta
import com.github.jangalinski.tabweb._foundation.css.GridWidth.HALF
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@InitRoute
fun initComponentsPage(ctx: InitRouteContext) {
  ctx.data.add(sitePageMeta("Components", "Reusable UI building blocks"))
  ctx.data.add(siteLayoutData(SiteRoutes.Components))
}

@Page(routeOverride = SiteRoutes.Components)
@Composable
fun Components() {
  cardDeck {
    card(width = HALF) {
      header(title = "Cards")
      body {
        P { Text("Card rows and cards provide the basic card layout.") }
      }
    }
    card(width = HALF) {
      header(title = "Statistics ..... 1")
      body {
        P { Text("Stat cards are useful for compact values and summaries.") }
        TablerIcon.entries.forEach { icon -> icon() }
      }
    }
  }
}
