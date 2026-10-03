package com.github.jangalinski.tabweb.site

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._app.LocalTablerAppState
import com.github.jangalinski.tabweb._app.TablerTheme
import com.github.jangalinski.tabweb._foundation.Url
import com.github.jangalinski.tabweb.icon.TablerIcon
import com.github.jangalinski.tabweb.navbar.TablerNavbarData
import com.github.jangalinski.tabweb.navbar.TablerNavbarItem
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div

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
          url = Url(SiteRoutes.Colors),
          title = "Colors",
          caption = "Show colors, gradients, and hex values",
          active = activeRoute == SiteRoutes.Colors,
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
          url = Url(SiteRoutes.Elements),
          title = "Elements",
          caption = "Low-level Tabler elements",
          active = activeRoute == SiteRoutes.Elements,
        ),
        TablerNavbarItem.Link(
          url = Url(SiteRoutes.Tables),
          title = "Tables",
          caption = "Tabler table examples",
          active = activeRoute == SiteRoutes.Tables,
          icon = TablerIcon.TI_TABLE,
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

/** Renders the light/dark mode control in the site navbar's first row. */
@Composable
fun SiteThemeToggle() {
  val tabler = LocalTablerAppState.current
  val darkMode = tabler.settings.theme == TablerTheme.Dark
  val targetMode = if (darkMode) TablerTheme.Light else TablerTheme.Dark
  val targetName = if (darkMode) "light" else "dark"

  Div(attrs = { attr("class", "d-none d-md-flex me-3") }) {
    Div(attrs = { attr("class", "nav-item") }) {
      A(
        href = "#",
        attrs = {
          attr("class", "nav-link px-0")
          attr("title", "Enable $targetName mode")
          attr("aria-label", "Enable $targetName mode")
          onClick {
            it.preventDefault()
            tabler.setTheme(targetMode)
          }
        },
      ) {
        if (darkMode) TablerIcon.TI_SUN() else TablerIcon.TI_MOON()
      }
    }
  }
}
