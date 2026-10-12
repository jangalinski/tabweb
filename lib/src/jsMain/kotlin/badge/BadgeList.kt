package com.github.jangalinski.tabweb.badge

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.Tabweb
import com.github.jangalinski.tabweb._foundation.TabwebComponent
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.css.plus
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * A Tabweb wrapper that spaces [Badge]s inline.
 */
interface BadgeList : TabwebComponent {
  companion object {
    /**
     * Creates a list containing the supplied badges.
     *
     * @param badges badges in display order.
     * @return a [BadgeList] containing [badges].
     */
    operator fun invoke(vararg badges: Badge): BadgeList = invoke(badges.toList())

    /**
     * Creates a list containing the supplied badges.
     *
     * @param badges badges in display order.
     * @return a [BadgeList] containing [badges].
     */
    operator fun invoke(badges: List<Badge>): BadgeList = object : BadgeList {
      override val badges = badges
    }
  }

  /**
   * Badges rendered in display order.
   */
  val badges: List<Badge>

  @Composable
  override fun invoke(modifier: Modifier) {
    KDiv(modifier = BadgeCss.badgeList + modifier) {
      badges.forEach { it() }
    }
  }
}
