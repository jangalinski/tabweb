package com.github.jangalinski.tabweb._app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.github.jangalinski.tabweb._foundation.Url
import com.github.jangalinski.tabweb.navigation.TabwebBrand
import com.github.jangalinski.tabweb.navigation.TabwebNavbarFactory
import com.github.jangalinski.tabweb.navigation.TabwebNavigation
import com.varabyte.kobweb.core.AppGlobals

/**
 * Static, site-specific defaults for the Tabweb page shell.
 *
 * Presentation settings that may change while the application runs belong in
 * [TabwebSettings], not in this configuration.
 *
 * @param shell Shared page-shell defaults supplied by [TabwebSiteConfig].
 */
data class TabwebSiteConfig(
  val shell: TabwebShellConfig = TabwebShellConfig(),
)

/**
 * Shared page-shell defaults supplied by [TabwebSiteConfig].
 *
 * @param brand Primary brand header rendered at the top of the page.
 * @param navbar Primary navigation rendered below the brand header for the active route.
 * @param navigation Static route tree used for page metadata and generated navigation.
 * @param navbarActions Actions rendered at the right side of the navbar's first row.
 * @param footer structured footer rendered at the bottom of the page.
 */
data class TabwebShellConfig(
  val brand: TabwebBrand.Brand = TabwebBrand.Brand.Logo(
    image = Url("/tabweb/tabweb-logo.svg"),
    caption = AppGlobals["title"] ?: "tabweb"
  ),
  val navbar: TabwebNavbarFactory = TabwebNavbarFactory.None,
  val navigation: TabwebNavigation? = null,
  val navbarActions: @Composable () -> Unit = {},
  val footer: TabwebFooter = TabwebFooter(),
)

/**
 * The [TabwebSiteConfig] visible to the current Compose subtree.
 *
 * The neutral default keeps individual Tabweb components and layouts usable
 * without a [TabwebApp] wrapper.
 */
val LocalTabwebSiteConfig = staticCompositionLocalOf { TabwebSiteConfig() }
