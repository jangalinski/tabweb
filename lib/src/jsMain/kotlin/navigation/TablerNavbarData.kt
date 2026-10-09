package com.github.jangalinski.tabweb.navbar

import com.github.jangalinski.tabweb.icon.Icon
import com.github.jangalinski.tabweb._foundation.Url

/** Immutable configuration for the primary Tabler navbar. */
data class TablerNavbarData(
  val items: List<TablerNavbarItem> = emptyList(),
)

/** Creates navbar data for the route currently displayed by the page shell. */
fun interface TablerNavbarFactory {

  /** Creates the navigation data with the appropriate item marked active. */
  fun create(activeRoute: String): TablerNavbarData

  companion object {
    /** A factory that renders an empty navbar. */
    val None = TablerNavbarFactory { TablerNavbarData() }
  }
}

/** A primary-navigation entry rendered inside the navbar's semantic list. */
sealed interface TablerNavbarItem {
  val title: String
  val caption: String?
  val icon: Icon?
  val badge: TablerNavbarBadge?

  /** A destination that navigates directly to [url]. */
  data class Link(
    val url: Url,
    override val title: String,
    override val caption: String? = null,
    override val icon: Icon? = null,
    override val badge: TablerNavbarBadge? = null,
    val active: Boolean = false,
  ) : TablerNavbarItem

  /** A non-navigating group that opens a dropdown of links or nested sections. */
  data class Section(
    override val title: String,
    override val caption: String? = null,
    override val icon: Icon? = null,
    override val badge: TablerNavbarBadge? = null,
    val items: List<TablerNavbarItem>,
    /** Number of preview-style columns used for this dropdown's direct children. */
    val columns: Int = 1,
    /** Whether this section's own route is active. */
    val active: Boolean = false,
  ) : TablerNavbarItem {
    init {
      require(columns > 0) { "Navbar section columns must be positive." }
    }
  }
}

/** A compact label displayed alongside a navbar item. */
data class TablerNavbarBadge(
  val label: String,
  val color: String = "primary",
)
