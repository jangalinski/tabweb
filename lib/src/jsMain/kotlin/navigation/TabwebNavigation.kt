package com.github.jangalinski.tabweb.navigation

import com.github.jangalinski.tabweb._foundation.Url
import com.github.jangalinski.tabweb.icon.TabwebIcon
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Static navigation hierarchy shared by generated routes and navigation renderers.
 *
 * @param root top-navbar entries in display order.
 */
data class TabwebNavigation(
  val root: List<TabwebNavigationElement> = emptyList(),
)

/** Reads the navigation manifest format used by site code generators. */
data object TabwebNavigationJson {

  /**
   * Parses a JSON navigation manifest into typed navigation data.
   *
   * A section may contain `{ "\$include": "dokka.json" }`. The include is
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
  ): TabwebNavigation = Json
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
  fun toNavigation(resolve: (String) -> String): TabwebNavigation = TabwebNavigation(
    root = root.map { it.toNavigation(resolve) },
  )

  fun toSections(resolve: (String) -> String): List<TabwebNavigationSection> =
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
  fun toNavigation(resolve: (String) -> String): TabwebNavigationElement = TabwebNavigationElement(
    title = title,
    description = description,
    route = Url(route),
    icon = icon?.let { iconName ->
      runCatching { TabwebIcon.valueOf(iconName) }
        .getOrElse { error("Unknown TabwebIcon '$iconName' for navigation entry '$title'.") }
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
  fun toNavigation(resolve: (String) -> String): List<TabwebNavigationSection> = when {
    include != null -> Json
      .decodeFromString<NavigationManifest>(resolve(include))
      .toSections(resolve)

    title != null -> listOf(
      TabwebNavigationSection(
        title = title,
        elements = elements.map { it.toNavigation(resolve) },
      ),
    )

    else -> error("Navigation section must define 'name' or '\$include'.")
  }
}
