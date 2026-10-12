package com.github.jangalinski.tabweb.breadcrumb

/**
 * A single crumb in a Tabweb breadcrumb trail.
 *
 * Crumbs may be clickable or active.
 */
data class BreadcrumbItem(
  val label: String,
  val href: String? = null,
  val active: Boolean = false,
)
