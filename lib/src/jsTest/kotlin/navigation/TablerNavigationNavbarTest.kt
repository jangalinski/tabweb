package com.github.jangalinski.tabweb.navigation

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.github.jangalinski.tabweb._foundation.Url
import com.github.jangalinski.tabweb.icon.TablerIcon
import com.github.jangalinski.tabweb.navbar.TablerNavbarItem
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
}
