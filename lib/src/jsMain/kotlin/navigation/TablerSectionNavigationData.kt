package com.github.jangalinski.tabweb.navigation

import com.github.jangalinski.tabweb._foundation.Url

/**
 * Static configuration for a route-scoped documentation navigation.
 *
 * The route tree is kept separate from the rendered data so active and
 * expanded state can be derived for every route without duplicating it in the
 * site configuration.
 *
 * @param sections top-level labelled groups in the section navigation.
 */
data class TablerSectionNavigationConfig(
  val sections: List<Section> = emptyList(),
) {
  /** A labelled group of documentation routes. */
  data class Section(
    val title: String,
    val items: List<Item>,
  )

  /** A route or nested route group in a documentation section. */
  sealed interface Item {
    val title: String

    /** A directly navigable documentation route. */
    data class Link(
      override val title: String,
      val path: String,
    ) : Item

    /** A nested group whose children may be expanded when active. */
    data class Group(
      override val title: String,
      val items: List<Item>,
      val path: String? = null,
    ) : Item
  }

  /**
   * Creates a factory that marks the exact route active and expands its
   * ancestor groups.
   *
   * @return a route-aware section-navigation factory.
   */
  fun factory(): TablerSectionNavigationFactory = TablerSectionNavigationFactory { activeRoute ->
    TablerSectionNavigationData(
      sections = sections.map { section ->
        TablerSectionNavigationData.Section(
          title = section.title,
          items = section.items.map { it.resolve(activeRoute) },
        )
      },
    )
  }

  private fun Item.resolve(activeRoute: String): TablerSectionNavigationData.Item = when (this) {
    is Item.Link -> TablerSectionNavigationData.Item.Link(
      title = title,
      url = Url(path),
      active = path.matchesRoute(activeRoute),
    )

    is Item.Group -> {
      val items = items.map { it.resolve(activeRoute) }
      val active = path?.matchesRoute(activeRoute) == true ||
        items.any { it.active || it.hasActiveDescendant() }
      TablerSectionNavigationData.Item.Group(
        title = title,
        url = path?.let { Url(it) },
        items = items,
        active = active,
        expanded = active,
      )
    }
  }
}

/**
 * Route-specific section-navigation data consumed by the renderer.
 *
 * @param sections labelled groups displayed in order.
 */
data class TablerSectionNavigationData(
  val sections: List<Section> = emptyList(),
) {
  /** A labelled group of rendered navigation items. */
  data class Section(
    val title: String,
    val items: List<Item>,
  )

  /** A rendered link or nested group. */
  sealed interface Item {
    val title: String
    val active: Boolean

    /** A rendered documentation link. */
    data class Link(
      override val title: String,
      val url: Url,
      override val active: Boolean = false,
    ) : Item

    /** A rendered nested group. */
    data class Group(
      override val title: String,
      val url: Url?,
      val items: List<Item>,
      override val active: Boolean,
      val expanded: Boolean,
    ) : Item
  }
}

/** Creates route-specific data for a section navigation. */
fun interface TablerSectionNavigationFactory {
  /**
   * Creates navigation data for [activeRoute].
   *
   * @param activeRoute base-path-independent current route.
   * @return section-navigation data with active and expanded state resolved.
   */
  fun create(activeRoute: String): TablerSectionNavigationData
}

private fun TablerSectionNavigationData.Item.hasActiveDescendant(): Boolean = when (this) {
  is TablerSectionNavigationData.Item.Link -> active
  is TablerSectionNavigationData.Item.Group -> active || items.any { it.hasActiveDescendant() }
}

private fun String.matchesRoute(activeRoute: String): Boolean = normalizeRoute(this) == normalizeRoute(activeRoute)

private fun normalizeRoute(route: String): String = route
  .substringBefore('#')
  .trimEnd('/')
  .ifEmpty { "/" }
