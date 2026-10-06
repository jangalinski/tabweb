package com.github.jangalinski.tabweb.badge

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.Link
import com.github.jangalinski.tabweb._foundation.TabwebComponent
import com.github.jangalinski.tabweb._foundation.TabwebDirection
import com.github.jangalinski.tabweb._foundation.compose.KAnchor
import com.github.jangalinski.tabweb._foundation.compose.KSpan
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.css.plus
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb.icon.Icon
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * A small Tabler label used to show a status, count, or tag.
 */
interface Badge : TabwebComponent {
  companion object {
    /**
     * Creates a configured [Badge].
     *
     * @param text label shown by the badge.
     * @param color background color for a solid or light badge, and the border color for an outline badge.
     * @param style visual treatment for the badge.
     * @param size size of the badge.
     * @param shape shape of the badge.
     * @param link optional destination that renders this badge as an anchor.
     * @return a configured [Badge] component instance.
     */
    operator fun invoke(
      text: String,
      color: BackgroundColor = BackgroundColor.SEMANTIC.PRIMARY,
      style: BadgeStyle = BadgeStyle.DEFAULT,
      size: BadgeSize = BadgeSize.DEFAULT,
      shape: BadgeShape = BadgeShape.DEFAULT,
      link: Link? = null,
    ): Badge = object : Badge {
      override val content = BadgeContent.Text(text)
      override val color = color
      override val style = style
      override val size = size
      override val shape = shape
      override val link = link
    }

    /**
     * Creates a label [Badge] with an icon at either edge.
     *
     * @param text label shown by the badge.
     * @param icon icon shown beside [text].
     * @param iconPosition edge at which [icon] is rendered.
     * @param color background color for a solid or light badge, and the border color for an outline badge.
     * @param style visual treatment for the badge.
     * @param size size of the badge.
     * @param shape shape of the badge.
     * @param link optional destination that renders this badge as an anchor.
     * @return a configured [Badge] component instance.
     */
    operator fun invoke(
      text: String,
      icon: Icon,
      iconPosition: TabwebDirection.Horizontal = TabwebDirection.LEFT,
      color: BackgroundColor = BackgroundColor.SEMANTIC.PRIMARY,
      style: BadgeStyle = BadgeStyle.DEFAULT,
      size: BadgeSize = BadgeSize.DEFAULT,
      shape: BadgeShape = BadgeShape.DEFAULT,
      link: Link? = null,
    ): Badge = object : Badge {
      override val content = BadgeContent.TextWithIcon(text, icon, iconPosition)
      override val color = color
      override val style = style
      override val size = size
      override val shape = shape
      override val link = link
    }

    /**
     * Creates an icon-only [Badge].
     *
     * @param icon icon shown by the badge.
     * @param color background color for a solid or light badge, and the border color for an outline badge.
     * @param style visual treatment for the badge.
     * @param size size of the badge.
     * @param shape shape of the badge.
     * @param link optional destination that renders this badge as an anchor.
     * @return a configured [Badge] component instance.
     */
    operator fun invoke(
      icon: Icon,
      color: BackgroundColor = BackgroundColor.SEMANTIC.PRIMARY,
      style: BadgeStyle = BadgeStyle.DEFAULT,
      size: BadgeSize = BadgeSize.DEFAULT,
      shape: BadgeShape = BadgeShape.DEFAULT,
      link: Link? = null,
    ): Badge = object : Badge {
      override val content = BadgeContent.IconOnly(icon)
      override val color = color
      override val style = style
      override val size = size
      override val shape = shape
      override val link = link
    }
  }

  /**
   * Typed text and icon content rendered by the badge.
   */
  val content: BadgeContent

  /**
   * Color used by the badge treatment.
   */
  val color: BackgroundColor

  /**
   * Visual treatment applied to the badge.
   */
  val style: BadgeStyle

  /**
   * Size applied to the badge.
   */
  val size: BadgeSize

  /**
   * Shape applied to the badge.
   */
  val shape: BadgeShape get() = BadgeShape.DEFAULT

  /**
   * Optional destination that renders this badge as an anchor.
   */
  val link: Link? get() = null

  @Composable
  override fun invoke(modifier: Modifier) {
    val modifiers = BadgeCss.badge + style.toBadgeModifier(color) + size.toBadgeModifier() + BadgeCss.shape(shape) + modifier

    @Composable
    fun content() {
      when (val content = content) {
        is BadgeContent.Text -> KText(content.value)
        is BadgeContent.TextWithIcon -> {
          if (content.position == TabwebDirection.LEFT) content.icon()
          KText(content.text)
          if (content.position == TabwebDirection.RIGHT) content.icon()
        }
        is BadgeContent.IconOnly -> content.icon()
      }
    }

    val rootModifier = if (content is BadgeContent.IconOnly) modifiers + BadgeCss.iconOnly else modifiers
    link?.let { KAnchor(href = it.href, modifier = rootModifier, content = ::content) }
      ?: KSpan(modifier = rootModifier, content = ::content)
  }
}
