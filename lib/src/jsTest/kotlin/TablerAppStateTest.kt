package com.github.jangalinski.tabweb

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.github.jangalinski.tabweb._app.LocalTablerAppState
import com.github.jangalinski.tabweb._app.ProvideTablerAppState
import com.github.jangalinski.tabweb._app.TablerAppState
import com.github.jangalinski.tabweb._app.TablerSettings
import com.github.jangalinski.tabweb._app.TablerTheme
import com.github.jangalinski.tabweb._app.THEME_SESSION_KEY
import com.github.jangalinski.tabweb._app.rememberTablerAppState
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.testutils.ComposeWebExperimentalTestsApi
import org.jetbrains.compose.web.testutils.runTest
import kotlin.test.Test

@OptIn(ComposeWebExperimentalTestsApi::class)
class TablerAppStateTest {

  @Test
  fun providesStateToDescendants() = runTest {
    val state = TablerAppState(TablerSettings(theme = TablerTheme.Dark))
    var observed: TablerAppState? = null

    composition {
      ProvideTablerAppState(state) {
        observed = LocalTablerAppState.current
      }
    }

    assertThat(observed).isEqualTo(state)
  }

  @Test
  fun synchronizesExplicitThemeToDocument() = runTest {
    val state = TablerAppState(TablerSettings(theme = TablerTheme.Light))

    composition {
      ProvideTablerAppState(state) {}
    }

    assertThat(document.documentElement?.getAttribute("data-bs-theme")).isEqualTo("light")
  }

  @Test
  fun removesThemeAttributeForSystemMode() = runTest {
    val state = TablerAppState(TablerSettings(theme = TablerTheme.System))
    document.documentElement?.setAttribute("data-bs-theme", "dark")

    composition {
      ProvideTablerAppState(state) {}
    }

    assertThat(document.documentElement?.getAttribute("data-bs-theme")).isNull()
  }

  @Test
  fun synchronizesDocumentWhenThemeChanges() = runTest {
    val state = TablerAppState(TablerSettings(theme = TablerTheme.Light))

    composition {
      ProvideTablerAppState(state) {}
    }

    state.setTheme(TablerTheme.Dark)

    assertThat(state.settings).isEqualTo(TablerSettings(theme = TablerTheme.Dark))
    waitForRecompositionComplete()
    assertThat(document.documentElement?.getAttribute("data-bs-theme")).isEqualTo("dark")
  }

  @Test
  fun restoresSelectedThemeFromSession() = runTest {
    window.sessionStorage.setItem(THEME_SESSION_KEY, "dark")
    try {
      var state: TablerAppState? = null
      composition {
        val remembered = rememberTablerAppState(TablerSettings(theme = TablerTheme.Light))
        state = remembered
        ProvideTablerAppState(remembered) {}
      }

      assertThat(state?.settings?.theme).isEqualTo(TablerTheme.Dark)
      assertThat(document.documentElement?.getAttribute("data-bs-theme")).isEqualTo("dark")
    } finally {
      window.sessionStorage.removeItem(THEME_SESSION_KEY)
    }
  }

  @Test
  fun savesThemeChangesAndClearsPreferenceForSystemMode() = runTest {
    window.sessionStorage.removeItem(THEME_SESSION_KEY)
    try {
      val state = TablerAppState(TablerSettings())
      composition {
        ProvideTablerAppState(state) {}
      }

      state.setTheme(TablerTheme.Dark)
      waitForRecompositionComplete()
      assertThat(window.sessionStorage.getItem(THEME_SESSION_KEY)).isEqualTo("dark")

      state.setTheme(TablerTheme.Light)
      waitForRecompositionComplete()
      assertThat(window.sessionStorage.getItem(THEME_SESSION_KEY)).isEqualTo("light")

      state.setTheme(TablerTheme.System)
      waitForRecompositionComplete()
      assertThat(window.sessionStorage.getItem(THEME_SESSION_KEY)).isNull()
    } finally {
      window.sessionStorage.removeItem(THEME_SESSION_KEY)
    }
  }

  @Test
  fun usesInitialThemeWithoutStoredPreference() = runTest {
    window.sessionStorage.removeItem(THEME_SESSION_KEY)
    try {
      var state: TablerAppState? = null
      composition {
        val remembered = rememberTablerAppState(TablerSettings(theme = TablerTheme.Light))
        state = remembered
        ProvideTablerAppState(remembered) {}
      }

      assertThat(state?.settings?.theme).isEqualTo(TablerTheme.Light)
      assertThat(window.sessionStorage.getItem(THEME_SESSION_KEY)).isEqualTo("light")
    } finally {
      window.sessionStorage.removeItem(THEME_SESSION_KEY)
    }
  }
}
