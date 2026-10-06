package com.github.jangalinski.tabweb

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._app.TablerLayout
import com.github.jangalinski.tabweb._app.TablerSiteConfig
import com.github.jangalinski.tabweb._app.ProvideTablerAppState
import com.github.jangalinski.tabweb._app.ProvideTablerSiteConfig
import com.github.jangalinski.tabweb._app.TablerSettings
import com.github.jangalinski.tabweb._app.rememberTablerAppState
import com.github.jangalinski.tabweb._foundation.Markdown
import com.github.jangalinski.tabweb._foundation.Url
import com.github.jangalinski.tabweb._foundation.Url.Internal
import com.github.jangalinski.tabweb.avatar.AvatarComposable
import com.github.jangalinski.tabweb.avatar.AvatarDsl
import com.github.jangalinski.tabweb.badge.BadgeComposable
import com.github.jangalinski.tabweb.badge.BadgeDsl
import com.github.jangalinski.tabweb.button.ButtonComposable
import com.github.jangalinski.tabweb.button.ButtonDsl
import com.github.jangalinski.tabweb.card.CardComposable
import com.github.jangalinski.tabweb.card.CardDsl
import com.github.jangalinski.tabweb.chart.ChartComposable
import com.github.jangalinski.tabweb.chart.ChartDsl
import com.github.jangalinski.tabweb.element.ElementComposable
import com.github.jangalinski.tabweb.element.ElementDsl
import com.github.jangalinski.tabweb.table.TableComposable
import com.github.jangalinski.tabweb.table.TableDsl
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.core.KobwebApp
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.navigation.BasePath

data object Tabweb :
  AvatarComposable by AvatarDsl,
  BadgeComposable by BadgeDsl,
  ButtonComposable by ButtonDsl,
  CardComposable by CardDsl,
  ChartComposable by ChartDsl,
  ElementComposable by ElementDsl,
  TableComposable by TableDsl {

  const val TABLER_LAYER = "tabweb"
  const val TABLER_LAYOUT = "com.github.jangalinski.tabweb.Tabweb.Layout"
  val HOME = Url("/")

  @Composable
  fun markdown(markdown: String, modifier: Modifier = Modifier) {
    Markdown(markdown).invoke(modifier)
  }

  @Layout
  @Composable
  fun Layout(ctx: PageContext, content: @Composable () -> Unit) {
    TablerLayout(ctx, content)
  }

  /**
   * Installs Kobweb, site defaults, and reactive Tabler settings for an application.
   *
   * Applications that already own their [KobwebApp] wrapper can instead use
   * [ProvideTablerAppState] and [ProvideTablerSiteConfig].
   */
  @Composable
  fun KobwebTablerApp(
    site: TablerSiteConfig = TablerSiteConfig(),
    settings: TablerSettings = TablerSettings(),
    content: @Composable () -> Unit,
  ) {
    val state = rememberTablerAppState(settings)

    KobwebApp {
      ProvideTablerAppState(state) {
        ProvideTablerSiteConfig(site, content)
      }
    }
  }

  fun publicResourcePath(fileName: String) : String = if (fileName.startsWith("/"))
    BasePath.prependTo(fileName)
  else
    publicResourcePath("/$fileName")
}
