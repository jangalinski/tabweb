package com.github.jangalinski.tabweb.card

import com.github.jangalinski.tabweb._foundation.TabwebStyle
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Placement of an image within a card.
 */
enum class CardImagePosition(val modifier: Modifier) : TabwebStyle {
  TOP(CardCss.CARD_IMG_TOP),
  BOTTOM(CardCss.CARD_IMG_BOTTOM),
  START(CardCss.CARD_IMG_START),
  END(CardCss.CARD_IMG_END);
}
