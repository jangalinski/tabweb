package com.github.jangalinski.tabweb._app

import com.github.jangalinski.tabweb.navigation.NavbarBehavior

/**
 * Configuration for the structural and behavioral variants of a Tabler page
 * shell.
 *
 * The options correspond to the independent layout choices represented by
 * the Tabler preview pages: navigation position, content width, sidebar mode
 * and placement, navbar behavior, and navbar theme. They are modeled as
 * configuration rather than separate layouts because the preview variants
 * share the same page, navigation, page-wrapper, main, and footer blocks.
 *
 * This model is intentionally not consumed by [com.github.jangalinski.tabweb._compose.TablerLayout]
 * yet. It establishes the type-safe configuration boundary for a later
 * layout implementation without changing the current layout behavior.
 *
 * @param navigationPosition whether navigation is presented vertically or horizontally.
 * @param containerWidth whether the page content uses Tabler's fluid or boxed width.
 * @param sidebarMode whether a vertical sidebar is expanded, folded, or folded on hover.
 * @param sidebarPlacement which side of the page contains a vertical sidebar.
 * @param navbarBehavior whether the navbar is in its default, sticky, or overlapping mode.
 * @param navbarTheme whether the navbar uses its default or dark theme.
 */
data class TablerLayoutOptions(
  val navigationPosition: NavigationPosition = NavigationPosition.Vertical,
  val containerWidth: ContainerWidth = ContainerWidth.Fluid,
  val sidebarMode: SidebarMode = SidebarMode.Default,
  val sidebarPlacement: SidebarPlacement = SidebarPlacement.Start,
  val navbarBehavior: NavbarBehavior = NavbarBehavior.DEFAULT,
  val navbarTheme: NavbarTheme = NavbarTheme.Default,
) {
  /** Selects the primary navigation arrangement. */
  enum class NavigationPosition {
    /** Render navigation in a vertical sidebar. */
    Vertical,

    /** Render navigation in a horizontal navbar. */
    Horizontal,
  }

  /** Selects the width behavior of the page content container. */
  enum class ContainerWidth {
    /** Allow the page content to use the available width. */
    Fluid,

    /** Constrain the page content to Tabler's boxed layout. */
    Boxed,
  }

  /** Selects the presentation state of a vertical sidebar. */
  enum class SidebarMode {
    /** Render the sidebar at its normal width. */
    Default,

    /** Keep the sidebar folded to its compact icon-oriented form. */
    Folded,

    /** Keep the sidebar folded until the pointer hovers over it. */
    FoldedHover,
  }

  /** Selects the side on which a vertical sidebar is rendered. */
  enum class SidebarPlacement {
    /** Render the sidebar at the start side of the page. */
    Start,

    /** Render the sidebar at the end side of the page. */
    End,
  }

  /** Selects the navbar surface theme. */
  enum class NavbarTheme {
    /** Use the default navbar theme. */
    Default,

    /** Use Tabler's dark navbar theme. */
    Dark,
  }
}
