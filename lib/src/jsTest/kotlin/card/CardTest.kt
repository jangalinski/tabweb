package com.github.jangalinski.tabweb.card

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import com.github.jangalinski.tabweb.Tabweb.card
import com.github.jangalinski.tabweb.Tabweb.cardGroup
import com.github.jangalinski.tabweb.Tabweb.cardRow
import com.github.jangalinski.tabweb._foundation.Link
import com.github.jangalinski.tabweb._foundation.Url
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb.icon.TablerIcon
import org.jetbrains.compose.web.testutils.ComposeWebExperimentalTestsApi
import org.jetbrains.compose.web.testutils.runTest
import kotlin.test.Test

@OptIn(ComposeWebExperimentalTestsApi::class)
class CardTest {

  private val testLink = object : Link {
    override val href = Url("/test")
    override val text = "Test Link"
  }

  @Test
  fun rendersInstancesAndDslCardsWithHeaderBodyAndFooter() = runTest {
    composition {
      Card(title = "Simple Card", body = "This is a simple card.")()
      card {
        header(title = "Card Header", subtitle = "Card Subtitle", light = true)
        body {
          KText("Body content")
        }
        footer(transparent = true) {
          KText("Footer content")
        }
      }
    }

    val html = root.innerHTML

    assertThat(html).contains("card")
    assertThat(html).contains("card-title")
    assertThat(html).contains("Simple Card")
    assertThat(html).contains("card-header")
    assertThat(html).contains("card-header-light")
    assertThat(html).contains("Card Header")
    assertThat(html).contains("card-subtitle")
    assertThat(html).contains("Card Subtitle")
    assertThat(html).contains("card-body")
    assertThat(html).contains("Body content")
    assertThat(html).contains("card-footer")
    assertThat(html).contains("card-footer-transparent")
    assertThat(html).contains("Footer content")
    assertThat(root.querySelectorAll(".card").length).isEqualTo(2)
    assertThat(root.querySelectorAll(".card-header > div > h2.card-title + p.card-subtitle").length).isEqualTo(1)
  }

  @Test
  fun rendersStatusRibbonStampAndProgress() = runTest {
    composition {
      card(
        status = CardStatus.top(BackgroundColor.SEMANTIC.DANGER),
        ribbon = CardRibbon(text = "NEW", color = BackgroundColor.SEMANTIC.SUCCESS, position = CardRibbonPosition.TOP),
        stamp = CardStamp(icon = TablerIcon.TI_STAR, color = BackgroundColor.BASE.YELLOW, size = CardStampSize.LG),
        progress = CardProgress(value = 45, color = BackgroundColor.SEMANTIC.PRIMARY),
      ) {
        body {
          title("Feature Card")
          KText("Card with extras")
        }
      }
    }

    val html = root.innerHTML

    assertThat(html).contains("card-status-top")
    assertThat(html).contains("bg-danger")
    assertThat(html).contains("ribbon")
    assertThat(html).contains("ribbon-top")
    assertThat(html).contains("bg-success")
    assertThat(html).contains("NEW")
    assertThat(html).contains("card-stamp")
    assertThat(html).contains("card-stamp-lg")
    assertThat(html).contains("ti-star")
    assertThat(root.querySelectorAll(".card-stamp.card-stamp-lg > .card-stamp-icon > .icon.ti-star").length).isEqualTo(1)
    assertThat(html).contains("font-size: calc(var(--tblr-stamp-size) * 0.75)")
    assertThat(html).contains("card-progress")
    assertThat(html).contains("progress-bar")
    assertThat(html).contains("width: 45%")
    assertThat(html).contains("aria-valuenow=\"45\"")
  }

  @Test
  fun rendersLinkedCardsAsAnchorsWithTransitions() = runTest {
    composition {
      card(link = testLink, linkType = CardLinkType.POP) {
        body {
          title("Linked Card")
        }
      }
      card(link = testLink, linkType = CardLinkType.ROTATE) {
        body {
          title("Rotate Linked Card")
        }
      }
    }

    val links = root.querySelectorAll("a.card")

    assertThat(links.length).isEqualTo(2)
    assertThat(root.innerHTML).contains("card-link-pop")
    assertThat(root.innerHTML).contains("card-link-rotate")
    assertThat(root.innerHTML).contains("href=\"/test\"")
  }

  @Test
  fun rendersCardModifiersAndGroups() = runTest {
    composition {
      card(active = true, stacked = true, size = CardSize.SM, rotate = CardRotate.START) {
        body {
          KText("Active stacked sm")
        }
      }
      cardGroup {
        card {
          body { KText("Grouped 1") }
        }
        card {
          body { KText("Grouped 2") }
        }
      }
      cardRow(deck = true) {
        card {
          body { KText("Row card 1") }
        }
        card {
          body { KText("Row card 2") }
        }
      }
    }

    val html = root.innerHTML

    assertThat(html).contains("card-active")
    assertThat(html).contains("card-stacked")
    assertThat(html).contains("card-sm")
    assertThat(html).contains("card-rotate-start")
    assertThat(html).contains("card-group")
    assertThat(html).contains("row-deck")
    assertThat(root.querySelectorAll(".card").length).isEqualTo(5)
  }

  @Test
  fun rendersCardImagesAtVariousPositions() = runTest {
    composition {
      card {
        imageTop(src = "/photos/top.jpg", alt = "Top image")
        body { KText("Card with top image") }
      }
      card {
        body { KText("Card with bottom image") }
        imageBottom(src = "/photos/bottom.jpg", alt = "Bottom image")
      }
      card {
        imageStart(src = "/photos/start.jpg", alt = "Start image")
        body { KText("Card with start image") }
      }
      card {
        imageEnd(src = "/photos/end.jpg", alt = "End image")
        body { KText("Card with end image") }
      }
    }

    val html = root.innerHTML

    assertThat(html).contains("card-img-top")
    assertThat(html).contains("src=\"/photos/top.jpg\"")
    assertThat(html).contains("alt=\"Top image\"")
    assertThat(html).contains("card-img-bottom")
    assertThat(html).contains("src=\"/photos/bottom.jpg\"")
    assertThat(html).contains("alt=\"Bottom image\"")
    assertThat(html).contains("card-img-start")
    assertThat(html).contains("src=\"/photos/start.jpg\"")
    assertThat(html).contains("alt=\"Start image\"")
    assertThat(html).contains("card-img-end")
    assertThat(html).contains("src=\"/photos/end.jpg\"")
    assertThat(html).contains("alt=\"End image\"")
  }
}
