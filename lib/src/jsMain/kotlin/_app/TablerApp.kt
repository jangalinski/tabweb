package com.github.jangalinski.tabweb._app

import androidx.compose.runtime.*
import kotlinx.browser.document
import kotlinx.browser.window

/**
 * Creates one [TablerAppState] for the lifetime of its composition location.
 * A theme selected in this browser tab's session takes precedence over [initialSettings].
 */
@Composable
fun rememberTablerAppState(initialSettings: TablerSettings = TablerSettings()): TablerAppState =
  remember {
    val theme = when (window.sessionStorage.getItem(THEME_SESSION_KEY)) {
      "light" -> TablerTheme.Light
      "dark" -> TablerTheme.Dark
      else -> initialSettings.theme
    }
    TablerAppState(initialSettings.copy(theme = theme))
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
    settings.applyToDocument()
    when (settings.theme) {
      TablerTheme.System -> window.sessionStorage.removeItem(THEME_SESSION_KEY)
      TablerTheme.Light -> window.sessionStorage.setItem(THEME_SESSION_KEY, "light")
      TablerTheme.Dark -> window.sessionStorage.setItem(THEME_SESSION_KEY, "dark")
    }
  }

  CompositionLocalProvider(LocalTablerAppState provides state) {
    content()
  }
}

internal const val THEME_SESSION_KEY = "tabweb.theme"

/** The [TablerAppState] visible to the current Compose subtree. */
val LocalTablerAppState = staticCompositionLocalOf<TablerAppState> {
  error("TablerAppState was not provided")
}

internal fun TablerSettings.applyToDocument() {
  document.documentElement?.let { html ->
    when (theme) {
      TablerTheme.System -> html.removeAttribute("data-bs-theme")
      TablerTheme.Light -> html.setAttribute("data-bs-theme", "light")
      TablerTheme.Dark -> html.setAttribute("data-bs-theme", "dark")
    }
  }
}
