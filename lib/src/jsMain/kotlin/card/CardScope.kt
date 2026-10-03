package com.github.jangalinski.tabweb.card

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebComponentScope
import com.github.jangalinski.tabweb._foundation.TabwebDsl
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.compose.KH3
import com.github.jangalinski.tabweb._foundation.compose.KImg
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.css.plus
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb.icon.Icon
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Receiver scope for building card content.
 */
@TabwebDsl
interface CardScope : TabwebComponentScope {
  /**
   * Renders a header section on the card.
   */
  @Composable
  fun header(
    title: String? = null,
    subtitle: String? = null,
    light: Boolean = false,
    modifier: Modifier = Modifier,
    actions: (@Composable () -> Unit)? = null,
    content: (@Composable CardHeaderScope.() -> Unit)? = null,
  )

  /**
   * Renders a body section on the card.
   */
  @Composable
  fun body(
    scrollable: Boolean = false,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
  )

  /**
   * Renders a footer section on the card.
   */
  @Composable
  fun footer(
    transparent: Boolean = false,
    borderless: Boolean = false,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
  )

  /**
   * Renders a title heading inside the card.
   */
  @Composable
  fun title(text: String, modifier: Modifier = Modifier)

  /**
   * Renders a subtitle inside the card.
   */
  @Composable
  fun subtitle(text: String, modifier: Modifier = Modifier)

  /**
   * Renders a status indicator on the card.
   */
  @Composable
  fun status(
    position: CardStatusPosition = CardStatusPosition.TOP,
    color: BackgroundColor = BackgroundColor.SEMANTIC.PRIMARY,
  )

  /**
   * Renders a ribbon badge on the card.
   */
  @Composable
  fun ribbon(
    text: String? = null,
    icon: Icon? = null,
    position: CardRibbonPosition = CardRibbonPosition.DEFAULT,
    bookmark: Boolean = false,
    color: BackgroundColor = BackgroundColor.SEMANTIC.PRIMARY,
  )

  /**
   * Renders a stamp watermark on the card.
   */
  @Composable
  fun stamp(
    icon: Icon,
    color: BackgroundColor = BackgroundColor.SEMANTIC.PRIMARY,
    size: CardStampSize = CardStampSize.DEFAULT,
  )

  /**
   * Renders a progress indicator at the top of the card.
   */
  @Composable
  fun progress(
    value: Int,
    color: BackgroundColor = BackgroundColor.SEMANTIC.PRIMARY,
    label: String? = null,
  )

  /**
   * Renders an image within the card at a specified position.
   */
  @Composable
  fun image(
    src: String,
    alt: String = "",
    position: CardImagePosition = CardImagePosition.TOP,
    modifier: Modifier = Modifier,
  )

  /**
   * Renders an image at the top of the card.
   */
  @Composable
  fun imageTop(src: String, alt: String = "", modifier: Modifier = Modifier)

  /**
   * Renders an image at the bottom of the card.
   */
  @Composable
  fun imageBottom(src: String, alt: String = "", modifier: Modifier = Modifier)

  /**
   * Renders an image at the start (left) of the card.
   */
  @Composable
  fun imageStart(src: String, alt: String = "", modifier: Modifier = Modifier)

  /**
   * Renders an image at the end (right) of the card.
   */
  @Composable
  fun imageEnd(src: String, alt: String = "", modifier: Modifier = Modifier)
}

internal data object DefaultCardScope : CardScope {
  @Composable
  override fun header(
    title: String?,
    subtitle: String?,
    light: Boolean,
    modifier: Modifier,
    actions: (@Composable () -> Unit)?,
    content: (@Composable CardHeaderScope.() -> Unit)?,
  ) {
    val lightModifier = if (light) CardCss.CARD_HEADER_LIGHT else Modifier
    KDiv(modifier = CardCss.CARD_HEADER + lightModifier + modifier) {
      if (title != null) {
        KH3(modifier = CardCss.CARD_TITLE) {
          KText(title)
        }
      }
      if (subtitle != null) {
        KDiv(modifier = CardCss.CARD_SUBTITLE) {
          KText(subtitle)
        }
      }
      content?.invoke(DefaultCardHeaderScope)
      if (actions != null) {
        KDiv(modifier = CardCss.CARD_ACTIONS) {
          actions()
        }
      }
    }
  }

  @Composable
  override fun body(scrollable: Boolean, modifier: Modifier, content: @Composable () -> Unit) {
    val scrollableModifier = if (scrollable) CardCss.CARD_BODY_SCROLLABLE else Modifier
    KDiv(modifier = CardCss.CARD_BODY + scrollableModifier + modifier) {
      content()
    }
  }

  @Composable
  override fun footer(
    transparent: Boolean,
    borderless: Boolean,
    modifier: Modifier,
    content: @Composable () -> Unit,
  ) {
    val transparentModifier = if (transparent) CardCss.CARD_FOOTER_TRANSPARENT else Modifier
    val borderlessModifier = if (borderless) CardCss.CARD_FOOTER_BORDERLESS else Modifier
    KDiv(modifier = CardCss.CARD_FOOTER + transparentModifier + borderlessModifier + modifier) {
      content()
    }
  }

  @Composable
  override fun title(text: String, modifier: Modifier) {
    KH3(modifier = CardCss.CARD_TITLE + modifier) {
      KText(text)
    }
  }

  @Composable
  override fun subtitle(text: String, modifier: Modifier) {
    KDiv(modifier = CardCss.CARD_SUBTITLE + modifier) {
      KText(text)
    }
  }

  @Composable
  override fun status(position: CardStatusPosition, color: BackgroundColor) {
    CardStatus(position, color).invoke()
  }

  @Composable
  override fun ribbon(
    text: String?,
    icon: Icon?,
    position: CardRibbonPosition,
    bookmark: Boolean,
    color: BackgroundColor,
  ) {
    CardRibbon(text, icon, position, bookmark, color).invoke()
  }

  @Composable
  override fun stamp(icon: Icon, color: BackgroundColor, size: CardStampSize) {
    CardStamp(icon, color, size).invoke()
  }

  @Composable
  override fun progress(value: Int, color: BackgroundColor, label: String?) {
    CardProgress(value, color, label).invoke()
  }

  @Composable
  override fun image(
    src: String,
    alt: String,
    position: CardImagePosition,
    modifier: Modifier,
  ) {
    KImg(src = src, alt = alt, modifier = position.modifier + modifier)
  }

  @Composable
  override fun imageTop(src: String, alt: String, modifier: Modifier) {
    image(src, alt, CardImagePosition.TOP, modifier)
  }

  @Composable
  override fun imageBottom(src: String, alt: String, modifier: Modifier) {
    image(src, alt, CardImagePosition.BOTTOM, modifier)
  }

  @Composable
  override fun imageStart(src: String, alt: String, modifier: Modifier) {
    image(src, alt, CardImagePosition.START, modifier)
  }

  @Composable
  override fun imageEnd(src: String, alt: String, modifier: Modifier) {
    image(src, alt, CardImagePosition.END, modifier)
  }
}
