package com.github.jangalinski.tabweb.badge

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.Link
import com.github.jangalinski.tabweb._foundation.TabwebComposable
import com.github.jangalinski.tabweb._foundation.TabwebDirection
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb.icon.Icon
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Provides page-level composable entry points for the badge concept.
 */
interface BadgeComposable : TabwebComposable {

  /**
   * Creates and renders a [Badge] without requiring an intermediate component instance at the call site.
   *
   * @param text label shown by the badge.
   * @param color background color for a solid or light badge, and the border color for an outline badge.
   * @param style visual treatment for the badge.
   * @param size size of the badge.
   * @param shape shape of the badge.
   * @param link optional destination that renders this badge as an anchor.
   * @param modifier additional attributes and styles applied to the badge root.
   * @return `Unit` after the badge has been emitted into the current composition.
   */
  @Composable
  fun badge(
    text: String,
    color: BackgroundColor = BackgroundColor.SEMANTIC.PRIMARY,
    style: BadgeStyle = BadgeStyle.DEFAULT,
    size: BadgeSize = BadgeSize.DEFAULT,
    shape: BadgeShape = BadgeShape.DEFAULT,
    link: Link? = null,
    modifier: Modifier = Modifier,
  )

  /**
   * Creates and renders a text [Badge] with an icon at either edge.
   *
   * @param text label shown by the badge.
   * @param icon icon shown beside [text].
   * @param iconPosition edge at which [icon] is rendered.
   * @param color background color for a solid or light badge, and the border color for an outline badge.
   * @param style visual treatment for the badge.
   * @param size size of the badge.
   * @param shape shape of the badge.
   * @param link optional destination that renders this badge as an anchor.
   * @param modifier additional attributes and styles applied to the badge root.
   * @return `Unit` after the badge has been emitted into the current composition.
   */
  @Composable
  fun badge(
    text: String,
    icon: Icon,
    iconPosition: TabwebDirection.Horizontal = TabwebDirection.LEFT,
    color: BackgroundColor = BackgroundColor.SEMANTIC.PRIMARY,
    style: BadgeStyle = BadgeStyle.DEFAULT,
    size: BadgeSize = BadgeSize.DEFAULT,
    shape: BadgeShape = BadgeShape.DEFAULT,
    link: Link? = null,
    modifier: Modifier = Modifier,
  )

  /**
   * Creates and renders an icon-only [Badge].
   *
   * @param icon icon shown by the badge.
   * @param color background color for a solid or light badge, and the border color for an outline badge.
   * @param style visual treatment for the badge.
   * @param size size of the badge.
   * @param shape shape of the badge.
   * @param link optional destination that renders this badge as an anchor.
   * @param modifier additional attributes and styles applied to the badge root.
   * @return `Unit` after the badge has been emitted into the current composition.
   */
  @Composable
  fun badge(
    icon: Icon,
    color: BackgroundColor = BackgroundColor.SEMANTIC.PRIMARY,
    style: BadgeStyle = BadgeStyle.DEFAULT,
    size: BadgeSize = BadgeSize.DEFAULT,
    shape: BadgeShape = BadgeShape.DEFAULT,
    link: Link? = null,
    modifier: Modifier = Modifier,
  )

  /**
   * Creates and renders a [BadgeList] from typed child declarations.
   *
   * @param modifier additional attributes and styles applied to the list root.
   * @param content the DSL block that adds badges to the list.
   * @return `Unit` after the list has been emitted into the current composition.
   */
  @Composable
  fun badges(
    modifier: Modifier = Modifier,
    content: BadgeListScope.() -> Unit,
  )
}
