package com.github.jangalinski.tabweb._app

import androidx.compose.runtime.*
import com.github.jangalinski.tabweb._foundation.sessionItem
import com.github.jangalinski.tabweb.navigation.NavbarBehavior
import kotlinx.browser.document
import kotlinx.browser.window

/**
 * Creates one [TablerAppState] for the lifetime of its composition location.
 * A theme selected in this browser tab's session takes precedence over [initialSettings].
 */
@Composable
fun rememberTablerAppState(initialSettings: TablerSettings = TablerSettings()): TablerAppState = remember {
  val sessionItem = window.sessionItem
  val theme = TablerTheme.getOrDefault(sessionItem, initialSettings.theme)
  val navbarBehavior = NavbarBehavior.getOrDefault(sessionItem, initialSettings.navbarBehavior)

  TablerAppState(initialSettings.copy(theme = theme, navbarBehavior = navbarBehavior))
}

/**
 * Provides static [site] defaults to Tabler composables in [content].
 *
 * @param site The static site configuration to provide.
 * @param content The content to provide the site configuration to.
 */
@Composable
fun ProvideTablerSiteConfig(
  site: TablerSiteConfig,
  content: @Composable () -> Unit,
) {
  CompositionLocalProvider(LocalTablerSiteConfig provides site) {
    content()
  }
}

/**
 * Provides [state] to Tabler composables and synchronizes its settings with
 * the document's Tabler attributes and the browser tab's session storage.
 */
@Composable
fun ProvideTablerAppState(
  state: TablerAppState,
  content: @Composable () -> Unit,
) {
  val settings = state.settings

  SideEffect {
    settings.sideEffect(document, window)
  }

  CompositionLocalProvider(LocalTablerAppState provides state) {
    content()
  }
}

/**
 * The [TablerAppState] visible to the current Compose subtree.
 */
val LocalTablerAppState = staticCompositionLocalOf<TablerAppState> {
  error("TablerAppState was not provided")
}
