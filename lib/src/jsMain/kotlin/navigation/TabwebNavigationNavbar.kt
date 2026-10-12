package com.github.jangalinski.tabweb.navigation

import com.github.jangalinski.tabweb._app.TabwebPageMeta
import com.github.jangalinski.tabweb._foundation.Url
import com.github.jangalinski.tabweb.breadcrumb.BreadcrumbItem
import com.varabyte.kobweb.navigation.BasePath
import com.varabyte.kobweb.navigation.remove

/**
 * Creates a route-aware primary-navbar factory from this static navigation tree.
 *
 * The generated navigation remains immutable; active state is derived each time
 * the page shell requests navbar data for its current route.
 *
 * @param badgeResolver resolves site-owned badge references.
 * @return a factory that derives active and ancestor-active states.
 */
fun TabwebNavigation.navbarFactory(
  badgeResolver: (String) -> TabwebNavbarBadge? = { null },
): TabwebNavbarFactory = TabwebNavbarFactory { activeRoute ->
  TabwebNavbarData(
    items = root.map { it.toNavbarItem(activeRoute, badgeResolver) },
  )
}

/**
 * Finds the page metadata registered for [route] in this navigation tree.
 *
 * The lookup traverses root entries, nested entries, and section entries. A
 * route is matched exactly after removing fragments and trailing slashes, so
 * an ancestor route cannot accidentally provide metadata for a child route.
 * Breadcrumbs are derived from the navigation path.
 *
 * @param route base-path-independent route to look up.
 * @return matching page metadata, or `null` when the route is not registered.
 */
fun TabwebNavigation.pageMeta(route: String): TabwebPageMeta? =
  findRoute(route)?.let { match ->
    TabwebPageMeta(
      title = match.entry.title,
      subtitle = match.entry.description,
      breadcrumbs = match.path.map { entry ->
        BreadcrumbItem(
          label = entry.title,
          href = entry.route.get(),
          active = entry === match.entry,
        )
      },
    )
  }

private data class NavigationRouteMatch(
  val entry: TabwebNavigationElement,
  val path: List<TabwebNavigationElement>,
)

private fun TabwebNavigation.findRoute(route: String): NavigationRouteMatch? =
  root.asSequence().firstNotNullOfOrNull { it.findRoute(route, emptyList()) }

private fun TabwebNavigationElement.findRoute(
  route: String,
  ancestors: List<TabwebNavigationElement>,
): NavigationRouteMatch? {
  val path = ancestors + this
  if (this.route.matchesRoute(route)) {
    return NavigationRouteMatch(this, path)
  }

  children.asSequence().mapNotNull { it.findRoute(route, path) }.firstOrNull()?.let { return it }
  return sections.asSequence()
    .flatMap { it.elements.asSequence() }
    .firstNotNullOfOrNull { it.findRoute(route, path) }
}

private fun TabwebNavigationElement.toNavbarItem(
  activeRoute: String,
  badgeResolver: (String) -> TabwebNavbarBadge?,
): TabwebNavbarItem {
  val badge = badgeRef?.let(badgeResolver)
  val items = children.map { it.toNavbarItem(activeRoute, badgeResolver) }
  val active = route.matchesRoute(activeRoute)

  return if (items.isEmpty()) {
    TabwebNavbarItem.Link(
      url = route,
      title = title,
      caption = description,
      icon = icon,
      badge = badge,
      active = active,
    )
  } else {
    TabwebNavbarItem.Section(
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
  .let { BasePath.remove(it) }
  .substringBefore('#')
  .trimEnd('/')
  .ifEmpty { "/" }
