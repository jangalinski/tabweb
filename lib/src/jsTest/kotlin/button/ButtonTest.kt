package com.github.jangalinski.tabweb.button

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import com.github.jangalinski.tabweb.Tabweb.button
import com.github.jangalinski.tabweb.Tabweb.buttons
import com.github.jangalinski.tabweb.icon.TabwebIcon
import org.jetbrains.compose.web.testutils.ComposeWebExperimentalTestsApi
import org.jetbrains.compose.web.testutils.runTest
import kotlin.test.Test

@OptIn(ComposeWebExperimentalTestsApi::class)
class ButtonTest {

  @Test
  fun rendersInstancesAndDslButtonsWithSupportedVariants() = runTest {
    composition {
      Button(text = "Primary", color = ButtonColor.PRIMARY)()
      button(text = "Outline", color = ButtonColor.GREEN, style = ButtonStyle.OUTLINE, size = ButtonSize.LARGE)
      buttons {
        button(text = "Pill", color = ButtonColor.PURPLE, shape = ButtonShape.PILL)
        button(text = "Right", icon = TabwebIcon.TI_ARROW_RIGHT, iconPosition = ButtonIconPosition.RIGHT)
      }
    }

    val html = root.innerHTML

    assertThat(html).contains("btn")
    assertThat(html).contains("btn-primary")
    assertThat(html).contains("btn-outline")
    assertThat(html).contains("btn-green")
    assertThat(html).contains("btn-lg")
    assertThat(html).contains("btn-list")
    assertThat(html).contains("btn-pill")
    assertThat(html).contains("ti-arrow-right")
    assertThat(html).contains("ti-arrow-right ms-2")
    assertThat(root.querySelectorAll("button.btn").length).isEqualTo(4)
  }

  @Test
  fun rendersAccessibleIconOnlyAndLoadingButtons() = runTest {
    composition {
      Button(icon = TabwebIcon.TI_STAR, ariaLabel = "Favorite", color = ButtonColor.YELLOW)()
      Button(text = "Saving", loading = true)()
    }

    val html = root.innerHTML

    assertThat(html).contains("btn-icon")
    assertThat(html).contains("aria-label=\"Favorite\"")
    assertThat(html).contains("btn-loading")
    assertThat(html).contains("aria-busy=\"true\"")
    assertThat(root.querySelectorAll("button[disabled]").length).isEqualTo(1)
  }
}
