package com.github.jangalinski.tabweb.site

import com.github.jangalinski.tabweb._app.TablerLayoutData
import com.github.jangalinski.tabweb._app.TablerPageMeta

/**
 * Supplies route-specific layout state to the shared Tabler layout.
 */
fun siteLayoutData(activeRoute: String): TablerLayoutData =
  TablerLayoutData(
    activeRoute = activeRoute,
  )

/**
 * Builds the page header metadata consumed by the shared Tabler layout.
 */
fun sitePageMeta(title: String, subtitle: String? = null): TablerPageMeta =
  TablerPageMeta(title = title, subtitle = subtitle)
