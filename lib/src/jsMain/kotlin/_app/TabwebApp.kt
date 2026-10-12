package com.github.jangalinski.tabweb._app

import androidx.compose.runtime.*
import com.github.jangalinski.tabweb._foundation.sessionItem
import com.github.jangalinski.tabweb.navigation.NavbarBehavior
import kotlinx.browser.document
import kotlinx.browser.window

/**
 * Creates one [TabwebAppState] for the lifetime of its composition location.
 * A theme selected in this browser tab's session takes precedence over [initialSettings].
 */
@Composable
fun rememberTabwebAppState(initialSettings: TabwebSettings = TabwebSettings()): TabwebAppState = remember {
  val sessionItem = window.sessionItem
  val theme = TabwebTheme.getOrDefault(sessionItem, initialSettings.theme)
  val navbarBehavior = NavbarBehavior.getOrDefault(sessionItem, initialSettings.navbarBehavior)

  TabwebAppState(initialSettings.copy(theme = theme, navbarBehavior = navbarBehavior))
}

/**
 * Provides static [site] defaults to Tabweb composables in [content].
 *
 * @param site The static site configuration to provide.
 * @param content The content to provide the site configuration to.
 */
@Composable
fun ProvideTabwebSiteConfig(
  site: TabwebSiteConfig,
  content: @Composable () -> Unit,
) {
  CompositionLocalProvider(LocalTabwebSiteConfig provides site) {
    content()
  }
}

/**
 * Provides [state] to Tabweb composables and synchronizes its settings with
 * the document's Tabweb attributes and the browser tab's session storage.
 */
@Composable
fun ProvideTabwebAppState(
  state: TabwebAppState,
  content: @Composable () -> Unit,
) {
  val settings = state.settings

  SideEffect {
    settings.sideEffect(document, window)
  }

  CompositionLocalProvider(LocalTabwebAppState provides state) {
    content()
  }
}

/**
 * The [TabwebAppState] visible to the current Compose subtree.
 */
val LocalTabwebAppState = staticCompositionLocalOf<TabwebAppState> {
  error("TabwebAppState was not provided")
}
