package com.github.jangalinski.tabweb.button

import com.github.jangalinski.tabweb._foundation.TabwebDirection
import com.github.jangalinski.tabweb.icon.Icon

/**
 * Represents the typed label and icon content supported by a [Button].
 */
sealed interface ButtonContent {
  /**
   * Text-only button content.
   *
   * @property value label rendered by the button.
   */
  data class Text(val value: String) : ButtonContent

  /**
   * Text accompanied by an icon at a defined edge of the button.
   *
   * @property text label rendered by the button.
   * @property icon icon rendered beside [text].
   * @property position edge at which [icon] is rendered.
   */
  data class TextWithIcon(
    val text: String,
    val icon: Icon,
    val position: TabwebDirection.Horizontal,
  ) : ButtonContent

  /**
   * Icon-only button content with its required accessible name.
   *
   * @property icon icon rendered by the button.
   * @property ariaLabel accessible name written to the button's `aria-label` attribute.
   */
  data class IconOnly(
    val icon: Icon,
    val ariaLabel: String,
  ) : ButtonContent
}
