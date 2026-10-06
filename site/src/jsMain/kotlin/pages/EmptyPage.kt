package com.github.jangalinski.tabweb.site.pages

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebText
import com.github.jangalinski.tabweb._foundation.compose.KH1
import com.github.jangalinski.tabweb.site.SiteRoutes
import com.github.jangalinski.tabweb.site.siteLayoutData
import com.github.jangalinski.tabweb.site.sitePageMeta
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext


  @InitRoute
  fun initEmptyPage(ctx: InitRouteContext) {
    ctx.data.add(sitePageMeta("Empty", "an empty page"))
    ctx.data.add(siteLayoutData(SiteRoutes.Empty))
  }

  @Page(routeOverride = SiteRoutes.Empty)
  @Composable
  fun EmptyPage() {

    TabwebText.Companion.markdown("""

      # this is an empty page

      wrapped in a data object.


      """.trimIndent()).invoke()

  }
