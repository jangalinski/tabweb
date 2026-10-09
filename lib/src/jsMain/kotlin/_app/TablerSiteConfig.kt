package com.github.jangalinski.tabweb._app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.github.jangalinski.tabweb._foundation.Url
import com.github.jangalinski.tabweb.navigation.TablerBrand
import com.github.jangalinski.tabweb.navigation.TablerNavbarFactory
import com.github.jangalinski.tabweb.navigation.TablerNavigation
import com.varabyte.kobweb.core.AppGlobals

/**
 * Static, site-specific defaults for the Tabler page shell.
 *
 * Presentation settings that may change while the application runs belong in
 * [TablerSettings], not in this configuration.
 *
 * @param shell Shared page-shell defaults supplied by [TablerSiteConfig].
 */
data class TablerSiteConfig(
  val shell: TablerShellConfig = TablerShellConfig(),
)

/**
 * Shared page-shell defaults supplied by [TablerSiteConfig].
 *
 * @param brand Primary brand header rendered at the top of the page.
 * @param navbar Primary navigation rendered below the brand header for the active route.
 * @param navbarActions Actions rendered at the right side of the navbar's first row.
 * @param footer structured footer rendered at the bottom of the page.
 */
data class TablerShellConfig(
  val brand: TablerBrand.Brand = TablerBrand.Brand.Logo(
    image = Url("/tabweb/tabweb-logo.svg"),
    caption = AppGlobals["title"] ?: "tabweb"
  ),
  /** Primary navigation rendered below the brand header for the active route. */
  val navbar: TablerNavbarFactory = TablerNavbarFactory.None,
  /** Static route tree used for page metadata and generated navigation. */
  val navigation: TablerNavigation? = null,
  /** Actions rendered at the right side of the navbar's first row. */
  val navbarActions: @Composable () -> Unit = {},
  val footer: TablerFooter = TablerFooter(),
)

/**
 * The [TablerSiteConfig] visible to the current Compose subtree.
 *
 * The neutral default keeps individual Tabler components and layouts usable
 * without a [KobwebTablerApp] wrapper.
 */
val LocalTablerSiteConfig = staticCompositionLocalOf { TablerSiteConfig() }
