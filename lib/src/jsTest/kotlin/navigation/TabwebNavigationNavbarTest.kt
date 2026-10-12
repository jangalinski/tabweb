package com.github.jangalinski.tabweb.navigation

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.github.jangalinski.tabweb._foundation.Url
import com.github.jangalinski.tabweb.icon.TabwebIcon
import kotlin.test.Test

class TabwebNavigationNavbarTest {

  @Test
  fun derivesActiveLeafAndAncestorSectionsFromTheCurrentRoute() {
    val factory = TabwebNavigation(
      root = listOf(
        TabwebNavigationElement(
          title = "Interface",
          route = Url("/interface"),
          icon = TabwebIcon.TI_BOX,
          children = listOf(
            TabwebNavigationElement(title = "Buttons", route = Url("/interfaces/buttons")),
          ),
        ),
      ),
    ).navbarFactory()

    val section = factory.create("/interfaces/buttons").items.single() as TabwebNavbarItem.Section
    val link = section.items.single() as TabwebNavbarItem.Link

    assertThat(link.active).isTrue()
    assertThat(section.active).isFalse()
  }

  @Test
  fun marksASectionActiveWhenItsOwnRouteMatches() {
    val section = TabwebNavigation(
      root = listOf(
        TabwebNavigationElement(
          title = "Interface",
          route = Url("/interface"),
          children = listOf(
            TabwebNavigationElement(title = "Buttons", route = Url("/interfaces/buttons")),
          ),
        ),
      ),
    ).navbarFactory().create("/interface").items.single() as TabwebNavbarItem.Section

    assertThat(section.active).isTrue()
  }

  @Test
  fun looksUpMetadataThroughNestedSectionEntries() {
    val navigation = TabwebNavigation(
      root = listOf(
        TabwebNavigationElement(
          title = "Components",
          route = Url("/components"),
          sections = listOf(
            TabwebNavigationSection(
              title = "Tabweb",
              elements = listOf(
                TabwebNavigationElement(
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
    val factory = TabwebNavigation(
      root = listOf(
        TabwebNavigationElement(
          title = "Components",
          route = Url("/components"),
          sections = listOf(
            TabwebNavigationSection(
              title = "Tabweb",
              elements = listOf(
                TabwebNavigationElement(title = "Markdown", route = Url("/markdown")),
                TabwebNavigationElement(
                  title = "Components",
                  route = Url("/components"),
                  children = listOf(
                    TabwebNavigationElement(title = "Buttons", route = Url("/buttons")),
                  ),
                ),
              ),
            ),
          ),
        ),
      ),
    ).sectionNavigationFactory()

    val data = factory.create("/buttons")
    val group = data.sections.single().items[1] as TabwebSectionNavigationData.Item.Group

    assertThat(group.active).isTrue()
    assertThat(group.expanded).isTrue()
    assertThat(group.items.single().active).isTrue()
  }
}
