package com.github.jangalinski.tabweb.badge

import com.github.jangalinski.tabweb._foundation.TabwebDsl
import com.github.jangalinski.tabweb._foundation.Link
import com.github.jangalinski.tabweb._foundation.TabwebComponentScope
import com.github.jangalinski.tabweb._foundation.TabwebDirection
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb.icon.Icon

/**
 * Provides the children for a badge list DSL.
 */
@TabwebDsl
class BadgeListScope internal constructor() : TabwebComponentScope {
  internal val badges = mutableListOf<Badge>()

  /**
   * Adds an already configured [Badge] to this list.
   *
   * @param badge component instance to add in display order.
   * @return `Unit` after [badge] has been added to this list.
   */
  fun badge(badge: Badge) {
    badges += badge
  }

  /**
   * Creates and adds a [Badge] to this list.
   *
   * @param text label shown by the badge.
   * @param color background color for a solid or light badge, and the border color for an outline badge.
   * @param style visual treatment for the badge.
   * @param size size of the badge.
   * @param shape shape of the badge.
   * @param link optional destination that renders this badge as an anchor.
   * @return `Unit` after the configured badge has been added to this list.
   */
  fun badge(
    text: String,
    color: BackgroundColor = BackgroundColor.SEMANTIC.PRIMARY,
    style: BadgeStyle = BadgeStyle.DEFAULT,
    size: BadgeSize = BadgeSize.DEFAULT,
    shape: BadgeShape = BadgeShape.DEFAULT,
    link: Link? = null,
  ) {
    badge(Badge(text = text, color = color, style = style, size = size, shape = shape, link = link))
  }

  /**
   * Creates and adds a text [Badge] with an icon at either edge.
   *
   * @param text label shown by the badge.
   * @param icon icon shown beside [text].
   * @param iconPosition edge at which [icon] is rendered.
   * @param color background color for a solid or light badge, and the border color for an outline badge.
   * @param style visual treatment for the badge.
   * @param size size of the badge.
   * @param shape shape of the badge.
   * @param link optional destination that renders this badge as an anchor.
   * @return `Unit` after the configured badge has been added to this list.
   */
  fun badge(
    text: String,
    icon: Icon,
    iconPosition: TabwebDirection.Horizontal = TabwebDirection.LEFT,
    color: BackgroundColor = BackgroundColor.SEMANTIC.PRIMARY,
    style: BadgeStyle = BadgeStyle.DEFAULT,
    size: BadgeSize = BadgeSize.DEFAULT,
    shape: BadgeShape = BadgeShape.DEFAULT,
    link: Link? = null,
  ) {
    badge(Badge(text = text, icon = icon, iconPosition = iconPosition, color = color, style = style, size = size, shape = shape, link = link))
  }

  /**
   * Creates and adds an icon-only [Badge].
   *
   * @param icon icon shown by the badge.
   * @param color background color for a solid or light badge, and the border color for an outline badge.
   * @param style visual treatment for the badge.
   * @param size size of the badge.
   * @param shape shape of the badge.
   * @param link optional destination that renders this badge as an anchor.
   * @return `Unit` after the configured badge has been added to this list.
   */
  fun badge(
    icon: Icon,
    color: BackgroundColor = BackgroundColor.SEMANTIC.PRIMARY,
    style: BadgeStyle = BadgeStyle.DEFAULT,
    size: BadgeSize = BadgeSize.DEFAULT,
    shape: BadgeShape = BadgeShape.DEFAULT,
    link: Link? = null,
  ) {
    badge(Badge(icon = icon, color = color, style = style, size = size, shape = shape, link = link))
  }
}
