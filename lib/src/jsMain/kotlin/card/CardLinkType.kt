package com.github.jangalinski.tabweb.card

import com.github.jangalinski.tabweb._foundation.TabwebStyle
import com.github.jangalinski.tabweb._foundation.css.plus
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Hover transition effect applied to a linked card.
 */
enum class CardLinkType(val modifier: Modifier) : TabwebStyle {
  DEFAULT(CardCss.CARD_LINK),
  ROTATE(CardCss.CARD_LINK + CardCss.CARD_LINK_ROTATE),
  POP(CardCss.CARD_LINK + CardCss.CARD_LINK_POP);
}
