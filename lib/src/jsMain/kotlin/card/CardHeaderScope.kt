package com.github.jangalinski.tabweb.card

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebComponentScope
import com.github.jangalinski.tabweb._foundation.TabwebDsl
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.compose.KH3
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.css.plus
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Receiver scope for building the header section of a card.
 */
@TabwebDsl
interface CardHeaderScope : TabwebComponentScope {
  /**
   * Renders a title heading inside the card header.
   */
  @Composable
  fun title(text: String, modifier: Modifier = Modifier)

  /**
   * Renders a composable title heading inside the card header.
   */
  @Composable
  fun title(modifier: Modifier = Modifier, content: @Composable () -> Unit)

  /**
   * Renders a subtitle inside the card header.
   */
  @Composable
  fun subtitle(text: String, modifier: Modifier = Modifier)

  /**
   * Renders a composable subtitle inside the card header.
   */
  @Composable
  fun subtitle(modifier: Modifier = Modifier, content: @Composable () -> Unit)

  /**
   * Renders an actions container for buttons, links, or dropdowns inside the card header.
   */
  @Composable
  fun actions(modifier: Modifier = Modifier, content: @Composable () -> Unit)
}

internal data object DefaultCardHeaderScope : CardHeaderScope {
  @Composable
  override fun title(text: String, modifier: Modifier) {
    KH3(modifier = CardCss.CARD_TITLE + modifier) {
      KText(text)
    }
  }

  @Composable
  override fun title(modifier: Modifier, content: @Composable () -> Unit) {
    KH3(modifier = CardCss.CARD_TITLE + modifier) {
      content()
    }
  }

  @Composable
  override fun subtitle(text: String, modifier: Modifier) {
    KDiv(modifier = CardCss.CARD_SUBTITLE + modifier) {
      KText(text)
    }
  }

  @Composable
  override fun subtitle(modifier: Modifier, content: @Composable () -> Unit) {
    KDiv(modifier = CardCss.CARD_SUBTITLE + modifier) {
      content()
    }
  }

  @Composable
  override fun actions(modifier: Modifier, content: @Composable () -> Unit) {
    KDiv(modifier = CardCss.CARD_ACTIONS + modifier) {
      content()
    }
  }
}
