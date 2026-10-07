package com.github.jangalinski.tabweb

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.github.jangalinski.tabweb._app.LocalTablerAppState
import com.github.jangalinski.tabweb._app.ProvideTablerAppState
import com.github.jangalinski.tabweb._app.TablerAppState
import com.github.jangalinski.tabweb._app.TablerSettings
import com.github.jangalinski.tabweb._app.TablerTheme
import com.github.jangalinski.tabweb._app.rememberTablerAppState
import com.github.jangalinski.tabweb.navigation.NavbarBehavior
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.testutils.ComposeWebExperimentalTestsApi
import org.jetbrains.compose.web.testutils.runTest
import kotlin.test.Test

@OptIn(ComposeWebExperimentalTestsApi::class)
class TablerAppStateTest {

  @Test
  fun providesStateToDescendants() = runTest {
    val state = TablerAppState(TablerSettings(theme = TablerTheme.DARK))
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
    val state = TablerAppState(TablerSettings(theme = TablerTheme.LIGHT))

    composition {
      ProvideTablerAppState(state) {}
    }

    assertThat(document.documentElement?.getAttribute("data-bs-theme")).isEqualTo("light")
  }

  @Test
  fun removesThemeAttributeForSystemMode() = runTest {
    val state = TablerAppState(TablerSettings(theme = TablerTheme.SYSTEM))
    document.documentElement?.setAttribute("data-bs-theme", "dark")

    composition {
      ProvideTablerAppState(state) {}
    }

    assertThat(document.documentElement?.getAttribute("data-bs-theme")).isNull()
  }

  @Test
  fun synchronizesDocumentWhenThemeChanges() = runTest {
    val state = TablerAppState(TablerSettings(theme = TablerTheme.LIGHT))

    composition {
      ProvideTablerAppState(state) {}
    }

    state.setTheme(TablerTheme.DARK)

    assertThat(state.settings).isEqualTo(TablerSettings(theme = TablerTheme.DARK))
    waitForRecompositionComplete()
    assertThat(document.documentElement?.getAttribute("data-bs-theme")).isEqualTo("dark")
  }

  @Test
  fun restoresSelectedThemeFromSession() = runTest {
    window.sessionStorage.setItem(TablerTheme.SESSION_KEY.value, "dark")
    try {
      var state: TablerAppState? = null
      composition {
        val remembered = rememberTablerAppState(TablerSettings(theme = TablerTheme.LIGHT))
        state = remembered
        ProvideTablerAppState(remembered) {}
      }

      assertThat(state?.settings?.theme).isEqualTo(TablerTheme.DARK)
      assertThat(document.documentElement?.getAttribute("data-bs-theme")).isEqualTo("dark")
    } finally {
      window.sessionStorage.removeItem(TablerTheme.SESSION_KEY.value)
    }
  }

  @Test
  fun savesThemeChangesAndClearsPreferenceForSystemMode() = runTest {
    window.sessionStorage.removeItem(TablerTheme.SESSION_KEY.value)
    try {
      val state = TablerAppState(TablerSettings())
      composition {
        ProvideTablerAppState(state) {}
      }

      state.setTheme(TablerTheme.DARK)
      waitForRecompositionComplete()
      assertThat(window.sessionStorage.getItem(TablerTheme.SESSION_KEY.value)).isEqualTo("dark")

      state.setTheme(TablerTheme.LIGHT)
      waitForRecompositionComplete()
      assertThat(window.sessionStorage.getItem(TablerTheme.SESSION_KEY.value)).isEqualTo("light")

      state.setTheme(TablerTheme.SYSTEM)
      waitForRecompositionComplete()
      assertThat(window.sessionStorage.getItem(TablerTheme.SESSION_KEY.value)).isNull()
    } finally {
      window.sessionStorage.removeItem(TablerTheme.SESSION_KEY.value)
    }
  }

  @Test
  fun usesInitialThemeWithoutStoredPreference() = runTest {
    window.sessionStorage.removeItem(TablerTheme.SESSION_KEY.value)
    try {
      var state: TablerAppState? = null
      composition {
        val remembered = rememberTablerAppState(TablerSettings(theme = TablerTheme.LIGHT))
        state = remembered
        ProvideTablerAppState(remembered) {}
      }

      assertThat(state?.settings?.theme).isEqualTo(TablerTheme.LIGHT)
      assertThat(window.sessionStorage.getItem(TablerTheme.SESSION_KEY.value)).isEqualTo("light")
    } finally {
      window.sessionStorage.removeItem(TablerTheme.SESSION_KEY.value)
    }
  }

  @Test
  fun restoresSelectedNavbarBehaviorFromSession() = runTest {
    window.sessionStorage.setItem("tabweb.navbarBehavior", "sticky")
    try {
      var state: TablerAppState? = null
      composition {
        val remembered = rememberTablerAppState(
          TablerSettings(navbarBehavior = NavbarBehavior.DEFAULT),
        )
        state = remembered
        ProvideTablerAppState(remembered) {}
      }

      assertThat(state?.settings?.navbarBehavior).isEqualTo(NavbarBehavior.STICKY)
      assertThat(document.documentElement?.getAttribute("data-bs-navbar")).isEqualTo("sticky")
    } finally {
      window.sessionStorage.removeItem("tabweb.navbarBehavior")
      document.documentElement?.removeAttribute("data-bs-navbar")
    }
  }

  @Test
  fun usesInitialNavbarBehaviorWithoutStoredPreference() = runTest {
    window.sessionStorage.removeItem("tabweb.navbarBehavior")
    try {
      var state: TablerAppState? = null
      composition {
        val remembered = rememberTablerAppState(
          TablerSettings(navbarBehavior = NavbarBehavior.STICKY),
        )
        state = remembered
        ProvideTablerAppState(remembered) {}
      }

      assertThat(state?.settings?.navbarBehavior).isEqualTo(NavbarBehavior.STICKY)
      assertThat(document.documentElement?.getAttribute("data-bs-navbar")).isEqualTo("sticky")
    } finally {
      document.documentElement?.removeAttribute("data-bs-navbar")
    }
  }
}
