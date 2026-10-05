package com.github.jangalinski.tabweb.element

import androidx.compose.runtime.mutableStateOf
import com.github.jangalinski.tabweb._foundation.Placement
import com.github.jangalinski.tabweb._foundation.TabwebText.Companion.markdown
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb.widget.ConfettiButton
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import kotlinx.browser.document
import org.jetbrains.compose.web.testutils.ComposeWebExperimentalTestsApi
import org.jetbrains.compose.web.testutils.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.MouseEvent
import org.w3c.dom.events.MouseEventInit

@OptIn(ComposeWebExperimentalTestsApi::class)
class TooltipTest {
  private val runtime = TooltipRuntimeStub()

  @Test
  fun dismissesOnPointerClickWithoutReplacingActionOrBlurringKeyboardActivation() = runTest {
    runtime.install()
    try {
      var clicks = 0
      composition {
        ConfettiButton()(Tooltip("Celebrate").modifier.onClick { clicks++ })
      }
      val button = root.querySelector("button") as HTMLElement
      button.focus()
      button.dispatchEvent(MouseEvent("click", MouseEventInit(bubbles = true, detail = 1)))
      assertTrue(document.activeElement != button)
      assertEquals(1, clicks)
      assertEquals(1, runtime.state.hidden as Int)

      button.focus()
      button.dispatchEvent(MouseEvent("click", MouseEventInit(bubbles = true, detail = 0)))
      assertEquals(button, document.activeElement)
      assertEquals(2, clicks)
      assertEquals(1, runtime.state.hidden as Int)
    } finally {
      runtime.restore()
    }
  }

  @Test
  fun initializesConfettiTooltipWithoutOverwritingItsToggle() = runTest {
    runtime.install()
    try {
      composition {
        ConfettiButton()(Tooltip("Celebrate", Placement.TOP).modifier)
      }

      val button = root.querySelector("button")!!
      assertEquals("confetti", button.getAttribute("data-bs-toggle"))
      assertEquals("tooltip", button.getAttribute("data-tblr-toggle"))
      assertEquals("top", button.getAttribute("data-bs-placement"))
      assertEquals(1, runtime.state.created.length as Int)
      assertEquals("Celebrate", runtime.state.created[0].options.title as String)
      assertEquals("top", runtime.state.created[0].options.placement as String)
      assertEquals(false, runtime.state.created[0].options.html as Boolean)
    } finally {
      runtime.restore()
    }
  }

  @Test
  fun refreshesChangedOptionsAndDisposesOnRemoval() = runTest {
    runtime.install()
    try {
      val text = mutableStateOf("Before")
      val placement = mutableStateOf(Placement.BOTTOM)
      val visible = mutableStateOf(true)
      composition {
        if (visible.value) KDiv(modifier = Tooltip(text.value, placement.value).modifier)
      }
      text.value = "After"
      placement.value = Placement.LEFT
      waitForRecompositionComplete()

      assertEquals("After", runtime.state.created[runtime.state.created.length - 1].options.title as String)
      assertEquals("left", runtime.state.created[runtime.state.created.length - 1].options.placement as String)
      assertEquals((runtime.state.created.length as Int) - 1, runtime.state.disposed as Int)

      visible.value = false
      waitForRecompositionComplete()
      assertEquals(runtime.state.created.length as Int, runtime.state.disposed as Int)
    } finally {
      runtime.restore()
    }
  }

  @Test
  fun rendersMarkdownAsHtml() = runTest {
    runtime.install()
    try {
      composition {
        KDiv(modifier = Tooltip(markdown("**Celebrate**")).modifier)
      }
      assertEquals(true, runtime.state.created[0].options.html as Boolean)
      assertTrue((runtime.state.created[0].options.title as String).contains("<strong>Celebrate</strong>"))
    } finally {
      runtime.restore()
    }
  }
}
