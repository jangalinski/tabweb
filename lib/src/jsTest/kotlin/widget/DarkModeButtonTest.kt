package com.github.jangalinski.tabweb.widget

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import com.github.jangalinski.tabweb._app.ProvideTablerAppState
import com.github.jangalinski.tabweb._app.TablerAppState
import com.github.jangalinski.tabweb._app.TablerSettings
import com.github.jangalinski.tabweb._app.TablerTheme
import com.github.jangalinski.tabweb.element.TooltipRuntimeStub
import org.jetbrains.compose.web.testutils.ComposeWebExperimentalTestsApi
import org.jetbrains.compose.web.testutils.runTest
import org.w3c.dom.HTMLElement
import kotlin.test.Test

@OptIn(ComposeWebExperimentalTestsApi::class)
class DarkModeButtonTest {

  @Test
  fun rendersLightModeToggleWhenInDarkMode() = runTest {
    val runtime = TooltipRuntimeStub()
    runtime.install()
    try {
      val state = TablerAppState(TablerSettings(theme = TablerTheme.Dark))

      composition {
        ProvideTablerAppState(state) {
          DarkModeButton()
        }
      }

      val button = root.firstElementChild as HTMLElement
      assertThat(button.tagName).isEqualTo("BUTTON")
      assertThat(button.className).contains("btn")
      assertThat(button.className).contains("btn-icon")
      assertThat(button.className).contains("btn-dark")
      val html = root.innerHTML
      assertThat(html).contains("ti-sun")
      assertThat(html).contains("title=\"Enable light mode\"")
      assertThat(html).contains("aria-label=\"Enable light mode\"")
      assertThat(button.getAttribute("data-tblr-toggle")).isEqualTo("tooltip")
    } finally {
      runtime.restore()
    }
  }

  @Test
  fun rendersDarkModeToggleWhenInLightMode() = runTest {
    val runtime = TooltipRuntimeStub()
    runtime.install()
    try {
      val state = TablerAppState(TablerSettings(theme = TablerTheme.Light))

      composition {
        ProvideTablerAppState(state) {
          DarkModeButton()
        }
      }

      val button = root.firstElementChild as HTMLElement
      assertThat(button.tagName).isEqualTo("BUTTON")
      assertThat(button.className).contains("btn")
      assertThat(button.className).contains("btn-icon")
      assertThat(button.className).contains("btn-light")
      val html = root.innerHTML
      assertThat(html).contains("ti-moon")
      assertThat(html).contains("title=\"Enable dark mode\"")
      assertThat(html).contains("aria-label=\"Enable dark mode\"")
      assertThat(button.getAttribute("data-tblr-toggle")).isEqualTo("tooltip")
    } finally {
      runtime.restore()
    }
  }

  @Test
  fun togglesThemeWhenClicked() = runTest {
    val runtime = TooltipRuntimeStub()
    runtime.install()
    try {
      val state = TablerAppState(TablerSettings(theme = TablerTheme.Light))

      composition {
        ProvideTablerAppState(state) {
          DarkModeButton()
        }
      }

      val link = root.querySelector("button.btn.btn-icon") as HTMLElement
      link.click()

      assertThat(state.settings.theme).isEqualTo(TablerTheme.Dark)
      waitForRecompositionComplete()
      assertThat(root.innerHTML).contains("ti-sun")
      assertThat(link.className).contains("btn-dark")
      assertThat(runtime.state.created[runtime.state.created.length - 1].options.title as String)
        .isEqualTo("Enable light mode")
      link.click()
      waitForRecompositionComplete()
      assertThat(state.settings.theme).isEqualTo(TablerTheme.Light)
      assertThat(link.className).contains("btn-light")
      assertThat(runtime.state.created[runtime.state.created.length - 1].options.title as String)
        .isEqualTo("Enable dark mode")
    } finally {
      runtime.restore()
    }
  }
}
