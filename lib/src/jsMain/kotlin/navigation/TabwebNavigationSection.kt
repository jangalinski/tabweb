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
fun TabwebNavigation.sectionNavigationFactory(): TabwebSectionNavigationFactory =
  TabwebSectionNavigationConfig(
    sections = root.flatMap { it.collectSections() },
  ).factory()

private fun TabwebNavigationElement.collectSections(): List<TabwebSectionNavigationConfig.Section> =
  sections.map { section ->
    TabwebSectionNavigationConfig.Section(
      title = section.title,
      items = section.elements.map { it.toSectionItem() },
    )
  } + sections.flatMap { section -> section.elements.flatMap { it.collectSections() } } +
    children.flatMap { it.collectSections() }

private fun TabwebNavigationElement.toSectionItem(): TabwebSectionNavigationConfig.Item =
  if (children.isEmpty()) {
    TabwebSectionNavigationConfig.Item.Link(
      title = title,
      path = route.get(),
    )
  } else {
    TabwebSectionNavigationConfig.Item.Group(
      title = title,
      path = route.get(),
      items = children.map { it.toSectionItem() },
    )
  }

/**
 * A labelled group in the left-side section navigation.
 *
 * @param title section heading.
 * @param elements section entries in display order.
 */
data class TabwebNavigationSection(
  val title: String,
  val elements: List<TabwebNavigationElement> = emptyList(),
)
