package com.github.jangalinski.tabweb.site

import com.github.jangalinski.tabweb._foundation.Url
import com.github.jangalinski.tabweb.icon.TablerIcon
import com.github.jangalinski.tabweb.navbar.TablerNavbarData
import com.github.jangalinski.tabweb.navbar.TablerNavbarItem

/** Builds the sample primary navigation shown by the documentation site. */
fun siteNavbar(activeRoute: String) = TablerNavbarData(
  items = listOf(
    TablerNavbarItem.Link(
      url = Url(SiteRoutes.Home),
      title = "Home",
      icon = TablerIcon.TI_HOME,
      active = activeRoute == SiteRoutes.Home,
    ),
    TablerNavbarItem.Section(
      title = "Interface",
      icon = TablerIcon.TI_BOX,
      items = listOf(
        TablerNavbarItem.Link(
          url = Url(SiteRoutes.Avatars),
          title = "Avatars",
          caption = "Display a photo, icon, or initials",
          active = activeRoute == SiteRoutes.Avatars,
        ),
        TablerNavbarItem.Link(
          url = Url(SiteRoutes.Badges),
          title = "Badges",
          caption = "Show labels, statuses, and counts",
          active = activeRoute == SiteRoutes.Badges,
        ),
        TablerNavbarItem.Link(
          url = Url(SiteRoutes.Buttons),
          title = "Buttons",
          caption = "Invoke actions in every Tabler style",
          active = activeRoute == SiteRoutes.Buttons,
        ),
        TablerNavbarItem.Link(
          url = Url(SiteRoutes.Cards),
          title = "Cards",
          caption = "Show different card layouts and content types",
          active = activeRoute == SiteRoutes.Cards,
        ),
        TablerNavbarItem.Link(
          url = Url(SiteRoutes.ExtremeCards),
          title = "Extreme cards",
          caption = "Stress-test combinations of card options",
          active = activeRoute == SiteRoutes.ExtremeCards,
        ),
        TablerNavbarItem.Section(
          title = "Charts",
          caption = "Explore ApexCharts inside Tabler cards",
          items = listOf(
            TablerNavbarItem.Link(
              url = Url(SiteRoutes.Charts),
              title = "Chart gallery",
              caption = "Compare chart families and variants",
              active = activeRoute == SiteRoutes.Charts,
            ),
            TablerNavbarItem.Link(
              url = Url(SiteRoutes.BarCharts),
              title = "Bar charts",
              caption = "Explore the first typed chart spec",
              active = activeRoute == SiteRoutes.BarCharts,
            ),
          ),
        ),
        TablerNavbarItem.Link(
          url = Url(SiteRoutes.Colors),
          title = "Colors",
          caption = "Show colors, gradients, and hex values",
          active = activeRoute == SiteRoutes.Colors,
        ),
        TablerNavbarItem.Link(
          url = Url(SiteRoutes.Tables),
          title = "Tables",
          caption = "Tabler table examples",
          active = activeRoute == SiteRoutes.Tables,
        ),
      ),
    ),
    TablerNavbarItem.Section(
      title = "Infrastructure",
      icon = TablerIcon.TI_SERVER,
      items = listOf(
        TablerNavbarItem.Link(
          url = Url(SiteRoutes.Components),
          title = "Components",
          caption = "Reusable UI building blocks",
          active = activeRoute == SiteRoutes.Components,
        ),
        TablerNavbarItem.Link(
          url = Url(SiteRoutes.Empty),
          title = "Empty",
          caption = "An empty page",
          active = activeRoute == SiteRoutes.Empty,
        ),
        TablerNavbarItem.Link(
          url = Url(SiteRoutes.Elements),
          title = "Elements",
          caption = "Low-level Tabler elements",
          active = activeRoute == SiteRoutes.Elements,
        ),
      ),
    ),
    TablerNavbarItem.Section(
      title = "Plugins",
      icon = TablerIcon.TI_PUZZLE,
      items = listOf(
        TablerNavbarItem.Link(url = Url(SiteRoutes.Home), title = "All plugins"),
        TablerNavbarItem.Link(url = Url(SiteRoutes.Home), title = "Marketplace"),
        TablerNavbarItem.Section(
          title = "Installed",
          items = listOf(
            TablerNavbarItem.Link(url = Url(SiteRoutes.Home), title = "Analytics"),
            TablerNavbarItem.Link(url = Url(SiteRoutes.Home), title = "Backups"),
            TablerNavbarItem.Link(url = Url(SiteRoutes.Home), title = "Monitoring"),
          ),
        ),
        TablerNavbarItem.Link(url = Url(SiteRoutes.Home), title = "Updates"),
        TablerNavbarItem.Link(url = Url(SiteRoutes.Home), title = "Plugin settings"),
        TablerNavbarItem.Link(url = Url(SiteRoutes.Home), title = "Developer tools"),
      ),
      columns = 2,
    ),
    TablerNavbarItem.Link(
      url = Url(SiteRoutes.Home),
      title = "Help",
      caption = "Documentation and support",
      icon = TablerIcon.TI_HELP_CIRCLE,
    ),
  ),
)
