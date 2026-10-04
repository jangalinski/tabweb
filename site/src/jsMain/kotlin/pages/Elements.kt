package com.github.jangalinski.tabweb.site.pages

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb.Tabweb.cardDeck
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
fun initElementsPage(ctx: InitRouteContext) {
  ctx.data.add(sitePageMeta("Elements", "Low-level Tabler elements"))
  ctx.data.add(siteLayoutData(SiteRoutes.Elements))
}

@Page(routeOverride = SiteRoutes.Elements)
@Composable
fun Elements() {
  cardDeck {
    card(width = HALF) {
      header(title = "Icons")
      body {
        P { Text("Icons can be used directly inside cards and navigation items.") }
      }
    }
    card(width = HALF) {
      header(title = "Tables")
      body {
        P { Text("The table demonstrations are available under Elements > Tables.") }
      }
    }
  }
}
