package com.github.jangalinski.tabweb.card

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.Link
import com.github.jangalinski.tabweb._foundation.TabwebComponentScope
import com.github.jangalinski.tabweb._foundation.TabwebDsl
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.css.GridWidth
import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.github.jangalinski.tabweb._foundation.css.plus
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
   * Adds a column with a typed responsive [width].
   *
   * @param width responsive grid width for the column.
   * @param modifier additional column styling.
   * @param content content rendered inside the column.
   * @return Unit.
   */
  @Composable
  fun col(
    width: GridWidth,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
  ) {
    col(modifier = width.modifier() + modifier, content = content)
  }

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
   * Adds a pre-built [card] inside a column with a typed responsive [width].
   *
   * @param card pre-built card rendered in the column.
   * @param width responsive grid width for the column.
   * @param modifier additional column styling.
   * @param cardModifier styling applied to the card itself.
   * @return Unit.
   */
  @Composable
  fun card(
    card: Card,
    width: GridWidth,
    modifier: Modifier = Modifier,
    cardModifier: Modifier = Modifier,
  ) {
    card(card = card, modifier = width.modifier() + modifier, cardModifier = cardModifier)
  }

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
   * Adds a card inside a column with a typed responsive [width].
   *
   * @param width responsive grid width for the column.
   * @param modifier additional column styling.
   * @param cardModifier styling applied to the card itself.
   * @param size card size.
   * @param status optional status indicator.
   * @param ribbon optional ribbon.
   * @param stamp optional stamp icon.
   * @param progress optional progress bar.
   * @param rotate optional rotation treatment.
   * @param active whether the card is active.
   * @param inactive whether the card is inactive.
   * @param borderless whether the card has no border.
   * @param stacked whether the card has a stacked appearance.
   * @param link optional destination for a linked card.
   * @param linkType linked card transition.
   * @param content card content.
   * @return Unit.
   */
  @Composable
  fun card(
    width: GridWidth,
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
  ) {
    card(
      modifier = width.modifier() + modifier,
      cardModifier = cardModifier,
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
    )
  }

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

  /**
   * Adds a simple card inside a column with a typed responsive [width].
   *
   * @param title card title.
   * @param body card body text.
   * @param width responsive grid width for the column.
   * @param subtitle optional card subtitle.
   * @param modifier additional column styling.
   * @param cardModifier styling applied to the card itself.
   * @param size card size.
   * @param status optional status indicator.
   * @param ribbon optional ribbon.
   * @param stamp optional stamp icon.
   * @param progress optional progress bar.
   * @param rotate optional rotation treatment.
   * @param active whether the card is active.
   * @param inactive whether the card is inactive.
   * @param borderless whether the card has no border.
   * @param stacked whether the card has a stacked appearance.
   * @param link optional destination for a linked card.
   * @param linkType linked card transition.
   * @return Unit.
   */
  @Composable
  fun card(
    title: String,
    body: String,
    width: GridWidth,
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
  ) {
    card(
      title = title,
      body = body,
      subtitle = subtitle,
      modifier = width.modifier() + modifier,
      cardModifier = cardModifier,
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
    )
  }
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
