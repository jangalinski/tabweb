package com.github.jangalinski.tabweb.badge

import com.github.jangalinski.tabweb._foundation.TabwebContent
import com.github.jangalinski.tabweb._foundation.TabwebDirection
import com.github.jangalinski.tabweb.icon.Icon

/**
 * Represents the typed label and icon content supported by a [Badge].
 */
sealed interface BadgeContent : TabwebContent{
  /**
   * Text-only badge content.
   *
   * @property value label rendered by the badge.
   */
  data class Text(val value: String) : BadgeContent

  /**
   * Text accompanied by an icon at a defined edge of the badge.
   *
   * @property text label rendered by the badge.
   * @property icon icon rendered beside [text].
   * @property position edge at which [icon] is rendered.
   */
  data class TextWithIcon(
    val text: String,
    val icon: Icon,
    val position: TabwebDirection.Horizontal,
  ) : BadgeContent

  /**
   * Icon-only badge content.
   *
   * @property icon icon rendered by the badge.
   */
  data class IconOnly(val icon: Icon) : BadgeContent
}
