package com.github.jangalinski.tabweb.card

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebComponent
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.github.jangalinski.tabweb._foundation.css.plus
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Row layout container for arranging cards with deck spacing.
 */
interface CardRow : TabwebComponent {
  val deck: Boolean
  val content: @Composable CardRowScope.() -> Unit

  @Composable
  override fun invoke(modifier: Modifier) {
    val deckModifier = if (deck) cssClass("row-deck") else Modifier
    KDiv(modifier = cssClass("row") + cssClass("row-cards") + deckModifier + modifier) {
      DefaultCardRowScope.content()
    }
  }

  companion object {
    operator fun invoke(
      deck: Boolean = false,
      content: @Composable CardRowScope.() -> Unit = {},
    ): CardRow = object : CardRow {
      override val deck: Boolean = deck
      override val content: @Composable CardRowScope.() -> Unit = content
    }
  }
}
