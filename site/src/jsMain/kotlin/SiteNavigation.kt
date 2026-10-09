package com.github.jangalinski.tabweb.site

import com.github.jangalinski.tabweb.navigation.TablerSectionNavigationConfig

/**
 * Documentation navigation used by the Markdown layout example.
 *
 * The primary navbar is generated from `navigation.json`; this remains a
 * separate route tree for the Markdown layout's section navigation.
 */
val siteDocumentationNavigation = TablerSectionNavigationConfig(
  sections = listOf(
    TablerSectionNavigationConfig.Section(
      title = "Tabweb",
      items = listOf(
        TablerSectionNavigationConfig.Item.Link(
          title = "Markdown pages",
          path = "/markdown-pages",
        ),
        TablerSectionNavigationConfig.Item.Group(
          title = "Components",
          path = SiteRoutes.Components,
          items = listOf(
            TablerSectionNavigationConfig.Item.Link("Avatars", SiteRoutes.Avatars),
            TablerSectionNavigationConfig.Item.Link("Badges", SiteRoutes.Badges),
            TablerSectionNavigationConfig.Item.Link("Buttons", SiteRoutes.Buttons),
            TablerSectionNavigationConfig.Item.Link("Cards", SiteRoutes.Cards),
            TablerSectionNavigationConfig.Item.Link("Tables", SiteRoutes.Tables),
          ),
        ),
      ),
    ),
  ),
).factory()
