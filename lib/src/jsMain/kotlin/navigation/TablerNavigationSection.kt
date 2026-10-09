package com.github.jangalinski.tabweb.navigation

/**
 * Creates a route-aware section navigation from the sections embedded in this
 * navigation tree.
 *
 * This allows generated navigation manifests to define both the primary
 * navbar and documentation side navigation in one source file.
 *
 * @return a factory that resolves active and expanded states per route.
 */
fun TablerNavigation.sectionNavigationFactory(): TablerSectionNavigationFactory =
  TablerSectionNavigationConfig(
    sections = root.flatMap { it.collectSections() },
  ).factory()

private fun TablerNavigationElement.collectSections(): List<TablerSectionNavigationConfig.Section> =
  sections.map { section ->
    TablerSectionNavigationConfig.Section(
      title = section.title,
      items = section.elements.map { it.toSectionItem() },
    )
  } + sections.flatMap { section -> section.elements.flatMap { it.collectSections() } } +
    children.flatMap { it.collectSections() }

private fun TablerNavigationElement.toSectionItem(): TablerSectionNavigationConfig.Item =
  if (children.isEmpty()) {
    TablerSectionNavigationConfig.Item.Link(
      title = title,
      path = route.get(),
    )
  } else {
    TablerSectionNavigationConfig.Item.Group(
      title = title,
      path = route.get(),
      items = children.map { it.toSectionItem() },
    )
  }
