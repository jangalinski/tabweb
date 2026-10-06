package com.github.jangalinski.tabweb.element

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebElement
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.github.jangalinski.tabweb._foundation.plus
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * A divider component that can optionally display text.
 *
 * https://docs.tabler.io/ui/components/divider
 */
interface Divider : TabwebElement {

  companion object {
    internal val HR = cssClass("hr")
    internal val HR_TEXT = cssClass("hr-text")

    /**
     * Creates a new instance of [Divider] with the given [text].
     */
    operator fun invoke(text: String? = null) = object : Divider {
      override val text: String? = text
    }
  }

  /**
   * The text to display in the divider. If null, no text will be displayed.
   */
  val text: String? get() = null

  @Composable
  override fun invoke(modifier: Modifier) {
    if (text != null) {
      KDiv(HR_TEXT + modifier) {
        KText(text!!)
      }
    } else {
      KDiv(HR + modifier)
    }
  }
}
