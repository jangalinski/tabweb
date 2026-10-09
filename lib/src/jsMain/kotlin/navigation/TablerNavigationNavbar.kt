package com.github.jangalinski.tabweb.navigation

import com.github.jangalinski.tabweb._foundation.Url
import com.github.jangalinski.tabweb.navbar.TablerNavbarBadge
import com.github.jangalinski.tabweb.navbar.TablerNavbarData
import com.github.jangalinski.tabweb.navbar.TablerNavbarFactory
import com.github.jangalinski.tabweb.navbar.TablerNavbarItem

/**
 * Creates a route-aware primary-navbar factory from this static navigation tree.
 *
 * The generated navigation remains immutable; active state is derived each time
 * the page shell requests navbar data for its current route.
 *
 * @param badgeResolver resolves site-owned badge references.
 * @return a factory that derives active and ancestor-active states.
 */
fun TablerNavigation.navbarFactory(
  badgeResolver: (String) -> TablerNavbarBadge? = { null },
): TablerNavbarFactory = TablerNavbarFactory { activeRoute ->
  TablerNavbarData(
    items = root.map { it.toNavbarItem(activeRoute, badgeResolver) },
  )
}

private fun TablerNavigationElement.toNavbarItem(
  activeRoute: String,
  badgeResolver: (String) -> TablerNavbarBadge?,
): TablerNavbarItem {
  val badge = badgeRef?.let(badgeResolver)
  val items = children.map { it.toNavbarItem(activeRoute, badgeResolver) }
  val active = route.matchesRoute(activeRoute)

  return if (items.isEmpty()) {
    TablerNavbarItem.Link(
      url = route,
      title = title,
      caption = description,
      icon = icon,
      badge = badge,
      active = active,
    )
  } else {
    TablerNavbarItem.Section(
      title = title,
      caption = description,
      icon = icon,
      badge = badge,
      items = items,
      columns = columns,
      active = active,
    )
  }
}

private fun Url.matchesRoute(activeRoute: String): Boolean =
  normalizeRoute(get()) == normalizeRoute(activeRoute)

private fun normalizeRoute(route: String): String = route
  .substringBefore('#')
  .trimEnd('/')
  .ifEmpty { "/" }
