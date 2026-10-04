package com.github.jangalinski.tabweb.card

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebComponent
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.css.plus
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb.icon.Icon
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Position of a ribbon on a card.
 */
enum class CardRibbonPosition(val modifier: Modifier) {
  DEFAULT(Modifier),
  TOP(CardCss.RIBBON_TOP),
  BOTTOM(CardCss.RIBBON_BOTTOM),
  START(CardCss.RIBBON_START),
  END(CardCss.RIBBON_END);
}

/**
 * Ribbon badge positioned along the border of a card.
 */
interface CardRibbon : TabwebComponent {
  val text: String?
  val icon: Icon?
  val position: CardRibbonPosition
  val bookmark: Boolean
  val color: BackgroundColor

  @Composable
  override fun invoke(modifier: Modifier) {
    val bookmarkModifier = if (bookmark) CardCss.RIBBON_BOOKMARK else Modifier
    KDiv(modifier = CardCss.RIBBON + position.modifier + bookmarkModifier + color + modifier) {
      icon?.invoke(Modifier)
      text?.let { KText(it) }
    }
  }

  companion object {
    operator fun invoke(
      text: String? = null,
      icon: Icon? = null,
      position: CardRibbonPosition = CardRibbonPosition.DEFAULT,
      bookmark: Boolean = false,
      color: BackgroundColor = BackgroundColor.SEMANTIC.PRIMARY,
    ): CardRibbon = object : CardRibbon {
      override val text: String? = text
      override val icon: Icon? = icon
      override val position: CardRibbonPosition = position
      override val bookmark: Boolean = bookmark
      override val color: BackgroundColor = color
    }
  }
}
