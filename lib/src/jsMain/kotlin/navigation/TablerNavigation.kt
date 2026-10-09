package com.github.jangalinski.tabweb.navigation

import com.github.jangalinski.tabweb._foundation.Url
import com.github.jangalinski.tabweb.icon.TablerIcon
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Static navigation hierarchy shared by generated routes and navigation renderers.
 *
 * @param root top-navbar entries in display order.
 */
data class TablerNavigation(
  val root: List<TablerNavigationElement> = emptyList(),
)

/**
 * One navigation entry, including its dropdown children and section navigation.
 *
 * Every entry has a route so route generation never needs to invent a URL for a
 * grouping entry. A site may still render an entry as a dropdown or section
 * heading when it also has [children] or [sections].
 *
 * @param title display title.
 * @param description optional supporting text.
 * @param route route represented by this entry.
 * @param icon optional generated Tabler icon.
 * @param badgeRef optional site-owned badge registry key.
 * @param children nested top-navigation entries.
 * @param sections left-side navigation sections belonging to this entry.
 */
data class TablerNavigationElement(
  val title: String,
  val description: String? = null,
  val route: Url,
  val icon: TablerIcon? = null,
  val badgeRef: String? = null,
  val children: List<TablerNavigationElement> = emptyList(),
  val sections: List<TablerNavigationSection> = emptyList(),
)

/**
 * A labelled group in the left-side section navigation.
 *
 * @param title section heading.
 * @param elements section entries in display order.
 */
data class TablerNavigationSection(
  val title: String,
  val elements: List<TablerNavigationElement> = emptyList(),
)

/** Reads the navigation manifest format used by site code generators. */
data object TablerNavigationJson {

  /**
   * Parses a JSON navigation manifest into typed navigation data.
   *
   * A section may contain `{ "\$include": "\$dokka.json" }`. The include is
   * resolved through [resolve], which keeps file-system access outside the
   * library.
   *
   * @param json JSON document with a `root` list.
   * @param resolve loads a referenced JSON document by its reference.
   * @return typed navigation hierarchy.
   * @throws IllegalArgumentException if an icon name or route is invalid.
   */
  fun parse(
    json: String,
    resolve: (String) -> String = { reference ->
      error("Navigation reference '$reference' requires a resolver.")
    },
  ): TablerNavigation = Json
    .decodeFromString<NavigationManifest>(json)
    .toNavigation(resolve)
}

@Serializable
private data class NavigationManifest(
  @SerialName("\$schema")
  val schema: String? = null,
  val version: Int = 1,
  val root: List<NavigationElementManifest> = emptyList(),
  val sections: List<NavigationSectionManifest> = emptyList(),
) {
  fun toNavigation(resolve: (String) -> String): TablerNavigation = TablerNavigation(
    root = root.map { it.toNavigation(resolve) },
  )

  fun toSections(resolve: (String) -> String): List<TablerNavigationSection> =
    sections.flatMap { it.toNavigation(resolve) }
}

@Serializable
private data class NavigationElementManifest(
  @SerialName("name")
  val title: String,
  val description: String? = null,
  val route: String,
  val icon: String? = null,
  val badgeRef: String? = null,
  val children: List<NavigationElementManifest> = emptyList(),
  val sections: List<NavigationSectionManifest> = emptyList(),
) {
  fun toNavigation(resolve: (String) -> String): TablerNavigationElement = TablerNavigationElement(
    title = title,
    description = description,
    route = Url(route),
    icon = icon?.let { iconName ->
      runCatching { TablerIcon.valueOf(iconName) }
        .getOrElse { error("Unknown TablerIcon '$iconName' for navigation entry '$title'.") }
    },
    badgeRef = badgeRef,
    children = children.map { it.toNavigation(resolve) },
    sections = sections.flatMap { it.toNavigation(resolve) },
  )
}

@Serializable
private data class NavigationSectionManifest(
  @SerialName("name")
  val title: String? = null,
  val elements: List<NavigationElementManifest> = emptyList(),
  @SerialName("\$include")
  val include: String? = null,
) {
  fun toNavigation(resolve: (String) -> String): List<TablerNavigationSection> = when {
    include != null -> Json
      .decodeFromString<NavigationManifest>(resolve(include))
      .toSections(resolve)

    title != null -> listOf(
      TablerNavigationSection(
        title = title,
        elements = elements.map { it.toNavigation(resolve) },
      ),
    )

    else -> error("Navigation section must define 'name' or '\$include'.")
  }
}
