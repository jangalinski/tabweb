package com.github.jangalinski.tabweb.card

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.Link
import com.github.jangalinski.tabweb._foundation.TabwebComponentScope
import com.github.jangalinski.tabweb._foundation.TabwebDsl
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Receiver scope for building a row of cards.
 */
@TabwebDsl
interface CardRowScope : TabwebComponentScope {

  /**
   * Adds a column container inside the row.
   */
  @Composable
  fun col(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
  )

  /**
   * Adds a pre-built [Card] inside the row within a column layout container.
   */
  @Composable
  fun card(
    card: Card,
    modifier: Modifier = Modifier,
    cardModifier: Modifier = Modifier,
  )

  /**
   * Adds a card inside the row within a column layout container.
   */
  @Composable
  fun card(
    modifier: Modifier = Modifier,
    cardModifier: Modifier = Modifier,
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
    content: @Composable CardScope.() -> Unit = {},
  )

  /**
   * Adds a simple card with title and body inside the row within a column layout container.
   */
  @Composable
  fun card(
    title: String,
    body: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier,
    cardModifier: Modifier = Modifier,
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
  )
}

internal data object DefaultCardRowScope : CardRowScope {
  @Composable
  override fun col(modifier: Modifier, content: @Composable () -> Unit) {
    val colModifier = if (modifier == Modifier) cssClass("col") else modifier
    KDiv(modifier = colModifier) {
      content()
    }
  }

  @Composable
  override fun card(
    card: Card,
    modifier: Modifier,
    cardModifier: Modifier,
  ) {
    val colModifier = if (modifier == Modifier) cssClass("col") else modifier
    KDiv(modifier = colModifier) {
      card.invoke(cardModifier)
    }
  }

  @Composable
  override fun card(
    modifier: Modifier,
    cardModifier: Modifier,
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
    content: @Composable CardScope.() -> Unit,
  ) {
    val colModifier = if (modifier == Modifier) cssClass("col") else modifier
    KDiv(modifier = colModifier) {
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
      ).invoke(cardModifier)
    }
  }

  @Composable
  override fun card(
    title: String,
    body: String,
    subtitle: String?,
    modifier: Modifier,
    cardModifier: Modifier,
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
  ) {
    val colModifier = if (modifier == Modifier) cssClass("col") else modifier
    KDiv(modifier = colModifier) {
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
      ).invoke(cardModifier)
    }
  }
}
