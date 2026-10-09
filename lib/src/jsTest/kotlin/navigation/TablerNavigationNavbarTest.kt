package com.github.jangalinski.tabweb.navigation

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.github.jangalinski.tabweb._foundation.Url
import com.github.jangalinski.tabweb.icon.TablerIcon
import kotlin.test.Test

class TablerNavigationNavbarTest {

  @Test
  fun derivesActiveLeafAndAncestorSectionsFromTheCurrentRoute() {
    val factory = TablerNavigation(
      root = listOf(
        TablerNavigationElement(
          title = "Interface",
          route = Url("/interface"),
          icon = TablerIcon.TI_BOX,
          children = listOf(
            TablerNavigationElement(title = "Buttons", route = Url("/interfaces/buttons")),
          ),
        ),
      ),
    ).navbarFactory()

    val section = factory.create("/interfaces/buttons").items.single() as TablerNavbarItem.Section
    val link = section.items.single() as TablerNavbarItem.Link

    assertThat(link.active).isTrue()
    assertThat(section.active).isFalse()
  }

  @Test
  fun marksASectionActiveWhenItsOwnRouteMatches() {
    val section = TablerNavigation(
      root = listOf(
        TablerNavigationElement(
          title = "Interface",
          route = Url("/interface"),
          children = listOf(
            TablerNavigationElement(title = "Buttons", route = Url("/interfaces/buttons")),
          ),
        ),
      ),
    ).navbarFactory().create("/interface").items.single() as TablerNavbarItem.Section

    assertThat(section.active).isTrue()
  }

  @Test
  fun looksUpMetadataThroughNestedSectionEntries() {
    val navigation = TablerNavigation(
      root = listOf(
        TablerNavigationElement(
          title = "Components",
          route = Url("/components"),
          sections = listOf(
            TablerNavigationSection(
              title = "Tabweb",
              elements = listOf(
                TablerNavigationElement(
                  title = "Markdown pages",
                  description = "Generated documentation",
                  route = Url("/markdown-pages"),
                ),
              ),
            ),
          ),
        ),
      ),
    )

    val meta = navigation.pageMeta("/markdown-pages/")

    assertThat(meta?.title).isEqualTo("Markdown pages")
    assertThat(meta?.subtitle).isEqualTo("Generated documentation")
    assertThat(meta?.breadcrumbs?.map { it.label })
      .isEqualTo(listOf("Components", "Markdown pages"))
    assertThat(meta?.breadcrumbs?.last()?.active).isEqualTo(true)
  }

  @Test
  fun buildsSectionNavigationFromEmbeddedSections() {
    val factory = TablerNavigation(
      root = listOf(
        TablerNavigationElement(
          title = "Components",
          route = Url("/components"),
          sections = listOf(
            TablerNavigationSection(
              title = "Tabweb",
              elements = listOf(
                TablerNavigationElement(title = "Markdown", route = Url("/markdown")),
                TablerNavigationElement(
                  title = "Components",
                  route = Url("/components"),
                  children = listOf(
                    TablerNavigationElement(title = "Buttons", route = Url("/buttons")),
                  ),
                ),
              ),
            ),
          ),
        ),
      ),
    ).sectionNavigationFactory()

    val data = factory.create("/buttons")
    val group = data.sections.single().items[1] as TablerSectionNavigationData.Item.Group

    assertThat(group.active).isTrue()
    assertThat(group.expanded).isTrue()
    assertThat(group.items.single().active).isTrue()
  }
}
