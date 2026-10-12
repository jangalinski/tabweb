package com.github.jangalinski.tabweb.navigation

import com.github.jangalinski.tabweb._foundation.Url
import com.github.jangalinski.tabweb.icon.Icon

/**
 * Immutable configuration for the primary Tabweb navbar.
 *
 * @param items The list of items to render in the navbar.
 */
data class TabwebNavbarData(
  val items: List<TabwebNavbarItem> = emptyList(),
)

/**
 * Creates navbar data for the route currently displayed by the page shell.
 */
fun interface TabwebNavbarFactory {

  /**
   * Creates the navigation data with the appropriate item marked active.
   */
  fun create(activeRoute: String): TabwebNavbarData

  companion object {
    /**
     * A factory that renders an empty navbar.
     */
    val None = TabwebNavbarFactory { TabwebNavbarData() }
  }
}

/**
 * A primary-navigation entry rendered inside the navbar's semantic list.
 */
sealed interface TabwebNavbarItem {
  val title: String
  val caption: String?
  val icon: Icon?
  val badge: TabwebNavbarBadge?

  /**
   * A destination that navigates directly to [url].
   *
   * @param url The destination's URL.
   */
  data class Link(
    val url: Url,
    override val title: String,
    override val caption: String? = null,
    override val icon: Icon? = null,
    override val badge: TabwebNavbarBadge? = null,
    val active: Boolean = false,
  ) : TabwebNavbarItem

  /**
   * A non-navigating group that opens a dropdown of links or nested sections.
   */
  data class Section(
    override val title: String,
    override val caption: String? = null,
    override val icon: Icon? = null,
    override val badge: TabwebNavbarBadge? = null,
    val items: List<TabwebNavbarItem>,

    /**
     * Number of preview-style columns used for this dropdown's direct children.
     */
    val columns: Int = 1,
    /**
     * Whether this section's own route is active.
     */
    val active: Boolean = false,
  ) : TabwebNavbarItem {
    init {
      require(columns > 0) { "Navbar section columns must be positive." }
    }
  }
}

/**
 * A compact label displayed alongside a navbar item.
 *
 * @param label The badge's text.
 * @param color The badge's color.
 */
data class TabwebNavbarBadge(
  val label: String,
  val color: String = "primary",
)
