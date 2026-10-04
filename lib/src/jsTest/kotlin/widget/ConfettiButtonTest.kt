package com.github.jangalinski.tabweb.widget

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import com.github.jangalinski.tabweb.button.ButtonColor
import com.github.jangalinski.tabweb.button.ButtonIconPosition
import com.github.jangalinski.tabweb.button.ButtonShape
import com.github.jangalinski.tabweb.button.ButtonSize
import com.github.jangalinski.tabweb.button.ButtonStyle
import com.github.jangalinski.tabweb.icon.TablerIcon
import org.jetbrains.compose.web.testutils.ComposeWebExperimentalTestsApi
import org.jetbrains.compose.web.testutils.runTest
import org.w3c.dom.HTMLElement
import kotlin.test.Test

@OptIn(ComposeWebExperimentalTestsApi::class)
class ConfettiButtonTest {

  @Test
  fun rendersDefaultConfettiButton() = runTest {
    composition {
      ConfettiButton()()
    }

    val button = root.querySelector("button") as HTMLElement
    assertThat(button.getAttribute("data-bs-toggle")).isEqualTo("confetti")
    assertThat(button.getAttribute("aria-label")).isEqualTo("Confetti button")
    assertThat(button.className).contains("btn")
    assertThat(button.className).contains("btn-icon")
    assertThat(button.className).contains("btn-light")
    assertThat(root.innerHTML).contains("ti-confetti")
  }

  @Test
  fun rendersDarkConfettiButton() = runTest {
    composition {
      ConfettiButton(color = ButtonColor.DARK)()
    }

    val button = root.querySelector("button") as HTMLElement
    assertThat(button.className).contains("btn-dark")
    assertThat(button.getAttribute("data-bs-toggle")).isEqualTo("confetti")
  }

  @Test
  fun rendersTextConfettiButton() = runTest {
    composition {
      ConfettiButton(text = "Celebrate")()
    }

    val button = root.querySelector("button") as HTMLElement
    assertThat(button.getAttribute("data-bs-toggle")).isEqualTo("confetti")
    assertThat(button.textContent).isEqualTo("Celebrate")
    assertThat(button.className).contains("btn")
    assertThat(button.className).contains("btn-light")
  }

  @Test
  fun rendersConfettiButtonWithAllAttributes() = runTest {
    composition {
      ConfettiButton(
        text = "Shower",
        icon = TablerIcon.TI_CONFETTI,
        iconPosition = ButtonIconPosition.LEFT,
        color = ButtonColor.PRIMARY,
        style = ButtonStyle.OUTLINE,
        size = ButtonSize.LARGE,
        shape = ButtonShape.PILL,
        count = 150,
        duration = 1500,
        colors = listOf("#f76707", "#f59f00", "#d63939"),
        speed = 0.5,
        target = "#confetti-target",
      )()
    }

    val button = root.querySelector("button") as HTMLElement
    assertThat(button.getAttribute("data-bs-toggle")).isEqualTo("confetti")
    assertThat(button.getAttribute("data-bs-count")).isEqualTo("150")
    assertThat(button.getAttribute("data-bs-duration")).isEqualTo("1500")
    assertThat(button.getAttribute("data-bs-colors")).isEqualTo("#f76707, #f59f00, #d63939")
    assertThat(button.getAttribute("data-bs-speed")).isEqualTo("0.5")
    assertThat(button.getAttribute("data-bs-target")).isEqualTo("#confetti-target")
    assertThat(button.className).contains("btn")
    assertThat(button.className).contains("btn-primary")
    assertThat(button.className).contains("btn-outline")
    assertThat(button.className).contains("btn-lg")
    assertThat(button.className).contains("btn-pill")
    assertThat(root.innerHTML).contains("ti-confetti")
  }
}
