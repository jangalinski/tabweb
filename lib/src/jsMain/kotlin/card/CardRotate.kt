package com.github.jangalinski.tabweb.card

import com.github.jangalinski.tabweb._foundation.TabwebStyle
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Rotation styling applied to a card.
 */
enum class CardRotate(val modifier: Modifier) : TabwebStyle {
  START(CardCss.CARD_ROTATE_START),
  END(CardCss.CARD_ROTATE_END),
  LEFT(CardCss.CARD_ROTATE_LEFT),
  RIGHT(CardCss.CARD_ROTATE_RIGHT);
}
