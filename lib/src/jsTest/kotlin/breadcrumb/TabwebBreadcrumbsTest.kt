package com.github.jangalinski.tabweb.breadcrumb

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.doesNotContain
import com.github.jangalinski.tabweb.breadcrumb.BreadcrumbItem
import org.jetbrains.compose.web.testutils.ComposeWebExperimentalTestsApi
import org.jetbrains.compose.web.testutils.runTest
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore
@OptIn(ComposeWebExperimentalTestsApi::class)
class TabwebBreadcrumbsTest {

  @Test
  fun rendersNothingForEmptyList() = runTest {
    composition {
      TabwebBreadcrumbs(items = emptyList())
    }

    val html = root.innerHTML
    assertThat(html).doesNotContain("breadcrumb")
  }

  @Test
  @Ignore
  fun rendersAllLabels() = runTest {
    composition {
      TabwebBreadcrumbs(
        items = listOf(
          BreadcrumbItem(label = "Home", href = "/"),
          BreadcrumbItem(label = "Users", href = "/users"),
          BreadcrumbItem(label = "Alice"),
        ),
      )
    }

    val html = root.innerHTML
    assertThat(html).contains("Home")
    assertThat(html).contains("Users")
    assertThat(html).contains("Alice")
  }

  @Test
  fun rendersBreadcrumbStructure() = runTest {
    composition {
      TabwebBreadcrumbs(items = listOf(BreadcrumbItem(label = "Home")))
    }

    val html = root.innerHTML
    assertThat(html).contains("breadcrumb")
    assertThat(html).contains("breadcrumb-item")
  }

  @Test
  @Ignore
  fun marksLastItemAsActive() = runTest {
    composition {
      TabwebBreadcrumbs(
        items = listOf(
          BreadcrumbItem(label = "Home", href = "/"),
          BreadcrumbItem(label = "Current Page"),
        ),
      )
    }

    val html = root.innerHTML
    // The last item should carry the active class
    assertThat(html).contains("active")
    // The last item should carry aria-current
    assertThat(html).contains("aria-current")
  }

  @Test
  @Ignore
  fun rendersLinkForNonActiveItems() = runTest {
    composition {
      TabwebBreadcrumbs(
        items = listOf(
          BreadcrumbItem(label = "Home", href = "/home"),
          BreadcrumbItem(label = "End"),
        ),
      )
    }

    val html = root.innerHTML
    assertThat(html).contains("href=\"/home\"")
  }

  @Test
  fun doesNotRenderLinkForActiveItem() = runTest {
    composition {
      TabwebBreadcrumbs(
        items = listOf(
          BreadcrumbItem(label = "Only", active = true),
        ),
      )
    }

    val html = root.innerHTML
    // Active items are rendered as <span>, not <a>
    assertThat(html).doesNotContain("<a ")
    assertThat(html).contains("active")
  }
}
