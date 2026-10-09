package com.github.jangalinski.tabweb.site.pages

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebText
import com.github.jangalinski.tabweb._foundation.compose.KH1
import com.github.jangalinski.tabweb.site.SiteRoutes
import com.varabyte.kobweb.core.Page


  @Page(routeOverride = SiteRoutes.Empty)
  @Composable
  fun EmptyPage() {

    TabwebText.Companion.markdown("""

      # this is an empty page

      wrapped in a data object.


      """.trimIndent()).invoke()

  }
