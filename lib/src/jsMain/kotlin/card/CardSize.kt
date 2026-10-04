package com.github.jangalinski.tabweb.card

import com.github.jangalinski.tabweb._foundation.TabwebSize
import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Sizing scale applied to a card.
 */
enum class CardSize(val modifier: Modifier) : TabwebSize {
  DEFAULT(Modifier),
  SM(CardCss.CARD_SM),
  MD(CardCss.CARD_MD),
  LG(CardCss.CARD_LG);
}
