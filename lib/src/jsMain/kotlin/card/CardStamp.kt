package com.github.jangalinski.tabweb.card

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebComponent
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.css.plus
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb.icon.Icon
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.attr

/**
 * Sizing scale applied to a card stamp.
 */
enum class CardStampSize(val modifier: Modifier) {
  DEFAULT(Modifier),
  LG(CardCss.CARD_STAMP_LG);
}

/**
 * Stamp badge or watermark icon shown in the corner of a card.
 */
interface CardStamp : TabwebComponent {
  val icon: Icon
  val color: BackgroundColor
  val size: CardStampSize

  @Composable
  override fun invoke(modifier: Modifier) {
    KDiv(modifier = CardCss.CARD_STAMP + size.modifier + modifier) {
      KDiv(modifier = CardCss.CARD_STAMP_ICON + color) {
        icon.invoke(Modifier.attr("style", "font-size: calc(var(--tblr-stamp-size) * 0.75)"))
      }
    }
  }

  companion object {
    operator fun invoke(
      icon: Icon,
      color: BackgroundColor = BackgroundColor.SEMANTIC.PRIMARY,
      size: CardStampSize = CardStampSize.DEFAULT,
    ): CardStamp = object : CardStamp {
      override val icon: Icon = icon
      override val color: BackgroundColor = color
      override val size: CardStampSize = size
    }
  }
}
