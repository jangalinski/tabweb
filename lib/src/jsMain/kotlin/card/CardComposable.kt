package com.github.jangalinski.tabweb.card

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.Link
import com.github.jangalinski.tabweb._foundation.TabwebComposable
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Provides page-level composable entry points for the card concept.
 */
interface CardComposable : TabwebComposable {
  /**
   * Renders a [Card] instance.
   */
  @Composable
  fun card(card: Card, modifier: Modifier = Modifier)

  /**
   * Creates and renders a [Card] from a DSL builder block.
   */
  @Composable
  fun card(
    size: CardSize = CardSize.DEFAULT,
    status: CardStatus? = null,
    ribbon: CardRibbon? = null,
    stamp: CardStamp? = null,
    progress: CardProgress? = null,
    rotate: CardRotate? = null,
    active: Boolean = false,
    inactive: Boolean = false,
    borderless: Boolean = false,
    stacked: Boolean = false,
    link: Link? = null,
    linkType: CardLinkType = CardLinkType.DEFAULT,
    modifier: Modifier = Modifier,
    content: @Composable CardScope.() -> Unit,
  )

  /**
   * Creates and renders a simple [Card] with title, optional subtitle, and text body.
   */
  @Composable
  fun card(
    title: String,
    body: String,
    subtitle: String? = null,
    size: CardSize = CardSize.DEFAULT,
    status: CardStatus? = null,
    ribbon: CardRibbon? = null,
    stamp: CardStamp? = null,
    progress: CardProgress? = null,
    rotate: CardRotate? = null,
    active: Boolean = false,
    inactive: Boolean = false,
    borderless: Boolean = false,
    stacked: Boolean = false,
    link: Link? = null,
    linkType: CardLinkType = CardLinkType.DEFAULT,
    modifier: Modifier = Modifier,
  )

  /**
   * Creates and renders a group of cards attached together seamlessly.
   */
  @Composable
  fun cardGroup(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
  )

  /**
   * Creates and renders a row of cards with responsive spacing.
   */
  @Composable
  fun cardRow(
    deck: Boolean = false,
    modifier: Modifier = Modifier,
    content: @Composable CardRowScope.() -> Unit,
  )

  /**
   * Creates and renders a row of cards with same height..
   */
  @Composable
  fun cardDeck(
    modifier: Modifier = Modifier,
    content: @Composable CardRowScope.() -> Unit,
  ) = cardRow(deck = true, modifier = modifier, content = content)
}
