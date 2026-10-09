package com.github.jangalinski.tabweb.card

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.Link
import com.github.jangalinski.tabweb._foundation.TabwebComponentDsl
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.css.plus
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Default implementation of [CardComposable].
 */
internal data object CardDsl : TabwebComponentDsl, CardComposable {

  @Composable
  override fun card(card: Card, modifier: Modifier) {
    card.invoke(modifier)
  }

  @Composable
  override fun card(
    size: CardSize,
    status: CardStatus?,
    ribbon: CardRibbon?,
    stamp: CardStamp?,
    progress: CardProgress?,
    rotate: CardRotate?,
    active: Boolean,
    inactive: Boolean,
    borderless: Boolean,
    stacked: Boolean,
    link: Link?,
    linkType: CardLinkType,
    modifier: Modifier,
    content: @Composable CardScope.() -> Unit,
  ) {
    Card(
      size = size,
      status = status,
      ribbon = ribbon,
      stamp = stamp,
      progress = progress,
      rotate = rotate,
      active = active,
      inactive = inactive,
      borderless = borderless,
      stacked = stacked,
      link = link,
      linkType = linkType,
      content = content,
    ).invoke(modifier)
  }

  @Composable
  override fun card(
    title: String,
    body: String,
    subtitle: String?,
    size: CardSize,
    status: CardStatus?,
    ribbon: CardRibbon?,
    stamp: CardStamp?,
    progress: CardProgress?,
    rotate: CardRotate?,
    active: Boolean,
    inactive: Boolean,
    borderless: Boolean,
    stacked: Boolean,
    link: Link?,
    linkType: CardLinkType,
    modifier: Modifier,
  ) {
    Card(
      title = title,
      body = body,
      subtitle = subtitle,
      size = size,
      status = status,
      ribbon = ribbon,
      stamp = stamp,
      progress = progress,
      rotate = rotate,
      active = active,
      inactive = inactive,
      borderless = borderless,
      stacked = stacked,
      link = link,
      linkType = linkType,
    ).invoke(modifier)
  }

  @Composable
  override fun cardGroup(modifier: Modifier, content: @Composable () -> Unit) {
    KDiv(modifier = CardCss.CARD_GROUP + modifier) {
      content()
    }
  }

  @Composable
  override fun cardRow(
    deck: Boolean,
    modifier: Modifier,
    content: @Composable CardRowScope.() -> Unit,
  ) {
    CardRow(deck = deck, content = content).invoke(modifier)
  }
}
