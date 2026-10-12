package com.github.jangalinski.tabweb.navigation

interface TabwebNavigation : Iterable<TabwebNavigationElement> {
  override fun iterator(): Iterator<TabwebNavigationElement>
}

/**
 * Static navigation hierarchy shared by generated routes and navigation renderers.
 *
 * @param root top-navbar entries in display order.
 */
data class TabwebNavigationData(
  val root: List<TabwebNavigationElement> = emptyList(),
) : TabwebNavigation {
  override fun iterator(): Iterator<TabwebNavigationElement> = root.iterator()
}
