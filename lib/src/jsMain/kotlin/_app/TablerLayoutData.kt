package com.github.jangalinski.tabweb._app

import com.github.jangalinski.tabweb.breadcrumb.BreadcrumbItem
import com.github.jangalinski.tabweb.navigation.TablerSectionNavigationFactory

/**
 * Route-scoped content supplied to the shared Tabler Kobweb layout.
 *
 * [activeRoute] selects configured navigation. The optional navigation and
 * footer slots override the defaults supplied by `TablerSiteConfig` for this
 * route, while the shell structure remains owned by the library.
 */
data class TablerLayoutData(
  /** Base-path-independent route used to select configured navigation. */
  val activeRoute: String,
  /** Footer overriding the configured Tabler shell footer. */
  val footer: TablerFooter? = null,
  /** Optional route-scoped navigation rendered beside the page body. */
  val sectionNavigation: TablerSectionNavigationFactory? = null,
)

/**
 * Route metadata consumed by the shared Tabler page-header block.
 *
 * Kobweb supplies this through route initialization so the shared layout can
 * render the preview's `BEGIN PAGE HEADER` region consistently for every page.
 */
data class TablerPageMeta(
  val title: String,
  val subtitle: String? = null,
  /** Breadcrumbs rendered above the page title in the shared header. */
  val breadcrumbs: List<BreadcrumbItem> = emptyList(),
)
