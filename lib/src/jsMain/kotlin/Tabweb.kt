package com.github.jangalinski.tabweb

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._app.TabwebLayout
import com.github.jangalinski.tabweb._app.TabwebSiteConfig
import com.github.jangalinski.tabweb._app.ProvideTabwebAppState
import com.github.jangalinski.tabweb._app.ProvideTabwebSiteConfig
import com.github.jangalinski.tabweb._app.TabwebSettings
import com.github.jangalinski.tabweb._app.rememberTabwebAppState
import com.github.jangalinski.tabweb._foundation.Markdown
import com.github.jangalinski.tabweb._foundation.Url
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

/**
 * The main entry point for Tabweb applications.
 *
 * This object provides access to all Tabweb composables, as well as the [TabwebApp] wrapper
 * that installs Kobweb and Tabweb defaults for an application.
 */
data object Tabweb :
  AvatarComposable by AvatarDsl,
  BadgeComposable by BadgeDsl,
  ButtonComposable by ButtonDsl,
  CardComposable by CardDsl,
  ChartComposable by ChartDsl,
  ElementComposable by ElementDsl,
  TableComposable by TableDsl {

  const val TABWEB_LAYER = "tabweb"
  const val TABWEB_LAYOUT = "com.github.jangalinski.tabweb.Tabweb.Layout"
  val HOME = Url("/")

  @Composable
  fun markdown(markdown: String, modifier: Modifier = Modifier) {
    Markdown(markdown).invoke(modifier)
  }

  @Layout
  @Composable
  fun Layout(ctx: PageContext, content: @Composable () -> Unit) {
    TabwebLayout(ctx, content)
  }

  /**
   * Installs Kobweb, site defaults, and reactive Tabweb settings for an application.
   *
   * Applications that already own their [KobwebApp] wrapper can instead use
   * [ProvideTabwebAppState] and [ProvideTabwebSiteConfig].
   */
  @Composable
  fun TabwebApp(
    site: TabwebSiteConfig = TabwebSiteConfig(),
    settings: TabwebSettings = TabwebSettings(),
    content: @Composable () -> Unit,
  ) {
    val state = rememberTabwebAppState(settings)

    KobwebApp {
      ProvideTabwebAppState(state) {
        ProvideTabwebSiteConfig(site, content)
      }
    }
  }
}
