package com.github.jangalinski.tabweb.card

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebComponent
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.css.plus
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Position of a status indicator on a card.
 */
enum class CardStatusPosition(val modifier: Modifier) {
  TOP(CardCss.CARD_STATUS_TOP),
  BOTTOM(CardCss.CARD_STATUS_BOTTOM),
  START(CardCss.CARD_STATUS_START),
  END(CardCss.CARD_STATUS_END);
}

/**
 * Status indicator bar displayed along an edge of a card.
 */
interface CardStatus : TabwebComponent {
  val position: CardStatusPosition
  val color: BackgroundColor

  @Composable
  override fun invoke(modifier: Modifier) {
    KDiv(modifier = position.modifier + color + modifier)
  }

  companion object {
    operator fun invoke(
      position: CardStatusPosition = CardStatusPosition.TOP,
      color: BackgroundColor = BackgroundColor.SEMANTIC.PRIMARY,
    ): CardStatus = object : CardStatus {
      override val position: CardStatusPosition = position
      override val color: BackgroundColor = color
    }

    fun top(color: BackgroundColor): CardStatus = invoke(CardStatusPosition.TOP, color)
    fun bottom(color: BackgroundColor): CardStatus = invoke(CardStatusPosition.BOTTOM, color)
    fun start(color: BackgroundColor): CardStatus = invoke(CardStatusPosition.START, color)
    fun end(color: BackgroundColor): CardStatus = invoke(CardStatusPosition.END, color)
  }
}
