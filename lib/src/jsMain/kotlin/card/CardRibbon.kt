package com.github.jangalinski.tabweb.card

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.alignment.*
import com.github.jangalinski.tabweb._foundation.TabwebComponent
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.css.plus
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb.icon.Icon
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Ribbon badge positioned along the border of a card.
 */
interface CardRibbon : TabwebComponent {

  companion object {
    operator fun invoke(
      text: String? = null,
      icon: Icon? = null,
      position: CardinalDirection? = null,
      bookmark: Boolean = false,
      color: BackgroundColor = BackgroundColor.SEMANTIC.PRIMARY,
    ): CardRibbon = object : CardRibbon {
      override val text: String? = text
      override val icon: Icon? = icon
      override val position: CardinalDirection? = position
      override val bookmark: Boolean = bookmark
      override val color: BackgroundColor = color
    }

    private val CardinalDirection?.css get() = when (this) {
      LEFT, START -> CardCss.RIBBON_START
      RIGHT, END -> CardCss.RIBBON_END
      TOP -> CardCss.RIBBON_TOP
      BOTTOM -> CardCss.RIBBON_BOTTOM
      null -> Modifier
    }
  }

  val text: String?
  val icon: Icon?
  val position: CardinalDirection?
  val bookmark: Boolean
  val color: BackgroundColor



  @Composable
  override fun invoke(modifier: Modifier) {
    val bookmarkModifier = if (bookmark) CardCss.RIBBON_BOOKMARK else Modifier
    KDiv(modifier = CardCss.RIBBON + position.css + bookmarkModifier + color + modifier) {
      icon?.invoke(Modifier)
      text?.let { KText(it) }
    }
  }

}
