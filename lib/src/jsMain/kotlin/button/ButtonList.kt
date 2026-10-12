package com.github.jangalinski.tabweb.button

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.Tabweb
import com.github.jangalinski.tabweb._foundation.TabwebComponent
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.css.plus
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * A Tabweb wrapper that spaces [Button]s inline.
 */
interface ButtonList : TabwebComponent {
  companion object {
    /**
     * Creates a list containing the supplied buttons.
     *
     * @param buttons buttons in display order.
     * @return a [ButtonList] containing [buttons].
     */
    operator fun invoke(vararg buttons: Button): ButtonList = invoke(buttons.toList())

    /**
     * Creates a list containing the supplied buttons.
     *
     * @param buttons buttons in display order.
     * @return a [ButtonList] containing [buttons].
     */
    operator fun invoke(buttons: List<Button>): ButtonList = object : ButtonList {
      override val buttons = buttons
    }
  }

  /**
   * Buttons rendered in display order.
   */
  val buttons: List<Button>

  @Composable
  override fun invoke(modifier: Modifier) {
    KDiv(modifier = ButtonCss.buttonList + modifier) {
      buttons.forEach { it() }
    }
  }
}
