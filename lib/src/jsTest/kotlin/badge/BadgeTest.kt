package com.github.jangalinski.tabweb.badge

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.doesNotContain
import assertk.assertions.isEqualTo
import com.github.jangalinski.tabweb.Tabweb.badge
import com.github.jangalinski.tabweb.Tabweb.badges
import com.github.jangalinski.tabweb._foundation.Link
import com.github.jangalinski.tabweb._foundation.TabwebDirection
import com.github.jangalinski.tabweb._foundation.Url
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb.icon.TablerIcon
import org.jetbrains.compose.web.testutils.ComposeWebExperimentalTestsApi
import org.jetbrains.compose.web.testutils.runTest
import kotlin.test.Test

@OptIn(ComposeWebExperimentalTestsApi::class)
class BadgeTest {

  private val documentationLink = object : Link {
    override val href = Url("/interfaces/badges")
    override val text = "Badge documentation"
  }

  @Test
  fun rendersInstancesAndDslBadgesWithSupportedVariants() = runTest {
    composition {
      Badge(text = "Primary", color = BackgroundColor.SEMANTIC.PRIMARY)()
      badge(text = "Large", color = BackgroundColor.BASE.BLUE, size = BadgeSize.L)
      badges {
        badge(text = "Outline", color = BackgroundColor.BASE.GREEN, style = BadgeStyle.OUTLINE)
        badge(text = "Star", icon = TablerIcon.TI_STAR, color = BackgroundColor.BASE.YELLOW)
      }
    }

    val html = root.innerHTML

    assertThat(html).contains("badge")
    assertThat(html).contains("bg-primary")
    assertThat(html).contains("badge-lg")
    assertThat(html).contains("badge-list")
    assertThat(html).contains("badge-outline")
    assertThat(html).contains("text-green")
    assertThat(html).contains("ti-star")
    assertThat(root.querySelectorAll(".badge").length).isEqualTo(4)
  }

  @Test
  fun keepsTheDefaultForegroundForLightBadges() = runTest {
    composition {
      Badge(text = "Blue", color = BackgroundColor.LIGHT.BLUE)()
    }

    assertThat(root.innerHTML).contains("bg-blue-lt")
    assertThat(root.innerHTML).doesNotContain("text-blue-fg")
  }

  @Test
  fun rendersLeftRightAndIconOnlyBadgeContent() = runTest {
    composition {
      badges {
        badge(text = "Left", icon = TablerIcon.TI_CHECK)
        badge(text = "Right", icon = TablerIcon.TI_ARROW_RIGHT, iconPosition = TabwebDirection.RIGHT)
        badge(icon = TablerIcon.TI_STAR)
      }
    }

    assertThat(root.innerHTML).contains("badge-icononly")
    assertThat(root.querySelectorAll(".badge-icononly").length).isEqualTo(1)
    assertThat(root.querySelectorAll(".ti-check").length).isEqualTo(1)
    assertThat(root.querySelectorAll(".ti-arrow-right").length).isEqualTo(1)
    assertThat(root.querySelectorAll(".ti-star").length).isEqualTo(1)
  }

  @Test
  fun rendersLinkedBadgesAsAnchors() = runTest {
    composition {
      Badge(text = "Linked", shape = BadgeShape.PILL, link = documentationLink)()
      badge(text = "DSL", link = documentationLink)
      badges {
        badge(text = "List", link = documentationLink)
      }
    }

    val links = root.querySelectorAll("a.badge")

    assertThat(links.length).isEqualTo(3)
    assertThat(root.innerHTML).contains("badge-pill")
    assertThat(root.innerHTML).contains("href=\"/interfaces/badges\"")
  }
}
