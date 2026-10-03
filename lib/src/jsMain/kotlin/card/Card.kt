package com.github.jangalinski.tabweb.card

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.Link
import com.github.jangalinski.tabweb._foundation.TabwebComponent
import com.github.jangalinski.tabweb._foundation.compose.KAnchor
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.css.plus
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Tabler card component that acts as a container for headers, bodies, footers, status bars, and stamps.
 */
interface Card : TabwebComponent {
  val size: CardSize get() = CardSize.DEFAULT
  val status: CardStatus? get() = null
  val ribbon: CardRibbon? get() = null
  val stamp: CardStamp? get() = null
  val progress: CardProgress? get() = null
  val rotate: CardRotate? get() = null
  val active: Boolean get() = false
  val inactive: Boolean get() = false
  val borderless: Boolean get() = false
  val stacked: Boolean get() = false
  val link: Link? get() = null
  val linkType: CardLinkType get() = CardLinkType.DEFAULT
  val content: @Composable CardScope.() -> Unit

  @Composable
  override fun invoke(modifier: Modifier) {
    val activeModifier = if (active) CardCss.CARD_ACTIVE else Modifier
    val inactiveModifier = if (inactive) CardCss.CARD_INACTIVE else Modifier
    val borderlessModifier = if (borderless) CardCss.CARD_BORDERLESS else Modifier
    val stackedModifier = if (stacked) CardCss.CARD_STACKED else Modifier
    val rotateModifier = rotate?.modifier ?: Modifier
    val sizeModifier = size.modifier

    val baseModifier = CardCss.CARD +
      sizeModifier +
      activeModifier +
      inactiveModifier +
      borderlessModifier +
      stackedModifier +
      rotateModifier

    if (link != null) {
      val linkModifier = linkType.modifier
      KAnchor(href = link!!.href, modifier = baseModifier + linkModifier + modifier) {
        status?.invoke(Modifier)
        ribbon?.invoke(Modifier)
        stamp?.invoke(Modifier)
        progress?.invoke(Modifier)
        DefaultCardScope.content()
      }
    } else {
      KDiv(modifier = baseModifier + modifier) {
        status?.invoke(Modifier)
        ribbon?.invoke(Modifier)
        stamp?.invoke(Modifier)
        progress?.invoke(Modifier)
        DefaultCardScope.content()
      }
    }
  }

  companion object {
    /**
     * Creates a [Card] with a DSL builder block.
     */
    operator fun invoke(
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
    ): Card = object : Card {
      override val size: CardSize = size
      override val status: CardStatus? = status
      override val ribbon: CardRibbon? = ribbon
      override val stamp: CardStamp? = stamp
      override val progress: CardProgress? = progress
      override val rotate: CardRotate? = rotate
      override val active: Boolean = active
      override val inactive: Boolean = inactive
      override val borderless: Boolean = borderless
      override val stacked: Boolean = stacked
      override val link: Link? = link
      override val linkType: CardLinkType = linkType
      override val content: @Composable CardScope.() -> Unit = content
    }

    /**
     * Creates a simple [Card] with a title, optional subtitle, and text body.
     */
    operator fun invoke(
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
    ): Card = invoke(
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
    ) {
      this.body {
        this.title(title)
        if (subtitle != null) {
          this.subtitle(subtitle)
        }
        KText(body)
      }
    }
  }
}
