package com.github.jangalinski.tabweb

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.github.jangalinski.tabweb._app.LocalTabwebAppState
import com.github.jangalinski.tabweb._app.ProvideTabwebAppState
import com.github.jangalinski.tabweb._app.TabwebAppState
import com.github.jangalinski.tabweb._app.TabwebSettings
import com.github.jangalinski.tabweb._app.TabwebTheme
import com.github.jangalinski.tabweb._app.rememberTabwebAppState
import com.github.jangalinski.tabweb.navigation.NavbarBehavior
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.testutils.ComposeWebExperimentalTestsApi
import org.jetbrains.compose.web.testutils.runTest
import kotlin.test.Test

@OptIn(ComposeWebExperimentalTestsApi::class)
class TabwebAppStateTest {

  @Test
  fun providesStateToDescendants() = runTest {
    val state = TabwebAppState(TabwebSettings(theme = TabwebTheme.DARK))
    var observed: TabwebAppState? = null

    composition {
      ProvideTabwebAppState(state) {
        observed = LocalTabwebAppState.current
      }
    }

    assertThat(observed).isEqualTo(state)
  }

  @Test
  fun synchronizesExplicitThemeToDocument() = runTest {
    val state = TabwebAppState(TabwebSettings(theme = TabwebTheme.LIGHT))

    composition {
      ProvideTabwebAppState(state) {}
    }

    assertThat(document.documentElement?.getAttribute("data-bs-theme")).isEqualTo("light")
  }

  @Test
  fun removesThemeAttributeForSystemMode() = runTest {
    val state = TabwebAppState(TabwebSettings(theme = TabwebTheme.SYSTEM))
    document.documentElement?.setAttribute("data-bs-theme", "dark")

    composition {
      ProvideTabwebAppState(state) {}
    }

    assertThat(document.documentElement?.getAttribute("data-bs-theme")).isNull()
  }

  @Test
  fun synchronizesDocumentWhenThemeChanges() = runTest {
    val state = TabwebAppState(TabwebSettings(theme = TabwebTheme.LIGHT))

    composition {
      ProvideTabwebAppState(state) {}
    }

    state.setTheme(TabwebTheme.DARK)

    assertThat(state.settings).isEqualTo(TabwebSettings(theme = TabwebTheme.DARK))
    waitForRecompositionComplete()
    assertThat(document.documentElement?.getAttribute("data-bs-theme")).isEqualTo("dark")
  }

  @Test
  fun restoresSelectedThemeFromSession() = runTest {
    window.sessionStorage.setItem(TabwebTheme.SESSION_KEY.value, "dark")
    try {
      var state: TabwebAppState? = null
      composition {
        val remembered = rememberTabwebAppState(TabwebSettings(theme = TabwebTheme.LIGHT))
        state = remembered
        ProvideTabwebAppState(remembered) {}
      }

      assertThat(state?.settings?.theme).isEqualTo(TabwebTheme.DARK)
      assertThat(document.documentElement?.getAttribute("data-bs-theme")).isEqualTo("dark")
    } finally {
      window.sessionStorage.removeItem(TabwebTheme.SESSION_KEY.value)
    }
  }

  @Test
  fun savesThemeChangesAndClearsPreferenceForSystemMode() = runTest {
    window.sessionStorage.removeItem(TabwebTheme.SESSION_KEY.value)
    try {
      val state = TabwebAppState(TabwebSettings())
      composition {
        ProvideTabwebAppState(state) {}
      }

      state.setTheme(TabwebTheme.DARK)
      waitForRecompositionComplete()
      assertThat(window.sessionStorage.getItem(TabwebTheme.SESSION_KEY.value)).isEqualTo("dark")

      state.setTheme(TabwebTheme.LIGHT)
      waitForRecompositionComplete()
      assertThat(window.sessionStorage.getItem(TabwebTheme.SESSION_KEY.value)).isEqualTo("light")

      state.setTheme(TabwebTheme.SYSTEM)
      waitForRecompositionComplete()
      assertThat(window.sessionStorage.getItem(TabwebTheme.SESSION_KEY.value)).isNull()
    } finally {
      window.sessionStorage.removeItem(TabwebTheme.SESSION_KEY.value)
    }
  }

  @Test
  fun usesInitialThemeWithoutStoredPreference() = runTest {
    window.sessionStorage.removeItem(TabwebTheme.SESSION_KEY.value)
    try {
      var state: TabwebAppState? = null
      composition {
        val remembered = rememberTabwebAppState(TabwebSettings(theme = TabwebTheme.LIGHT))
        state = remembered
        ProvideTabwebAppState(remembered) {}
      }

      assertThat(state?.settings?.theme).isEqualTo(TabwebTheme.LIGHT)
      assertThat(window.sessionStorage.getItem(TabwebTheme.SESSION_KEY.value)).isEqualTo("light")
    } finally {
      window.sessionStorage.removeItem(TabwebTheme.SESSION_KEY.value)
    }
  }

  @Test
  fun restoresSelectedNavbarBehaviorFromSession() = runTest {
    window.sessionStorage.setItem("tabweb.navbarBehavior", "sticky")
    try {
      var state: TabwebAppState? = null
      composition {
        val remembered = rememberTabwebAppState(
          TabwebSettings(navbarBehavior = NavbarBehavior.DEFAULT),
        )
        state = remembered
        ProvideTabwebAppState(remembered) {}
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
      var state: TabwebAppState? = null
      composition {
        val remembered = rememberTabwebAppState(
          TabwebSettings(navbarBehavior = NavbarBehavior.STICKY),
        )
        state = remembered
        ProvideTabwebAppState(remembered) {}
      }

      assertThat(state?.settings?.navbarBehavior).isEqualTo(NavbarBehavior.STICKY)
      assertThat(document.documentElement?.getAttribute("data-bs-navbar")).isEqualTo("sticky")
    } finally {
      document.documentElement?.removeAttribute("data-bs-navbar")
    }
  }
}
