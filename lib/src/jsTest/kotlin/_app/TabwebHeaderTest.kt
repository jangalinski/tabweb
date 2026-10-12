package com.github.jangalinski.tabweb._app

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
class TabwebPageHeaderTest {

  @Test
  fun rendersTitleText() = runTest {
    composition {
      TabwebPageHeader(title = "Dashboard")
    }

    val html = root.innerHTML
    assertThat(html).contains("Dashboard")
  }

  @Test
  fun rendersPageHeaderClass() = runTest {
    composition {
      TabwebPageHeader(title = "Overview")
    }

    val html = root.innerHTML
    assertThat(html).contains("page-header")
  }

  @Test
  fun rendersOptionalSubtitle() = runTest {
    composition {
      TabwebPageHeader(title = "Reports", subtitle = "Last 30 days")
    }

    val html = root.innerHTML
    assertThat(html).contains("Last 30 days")
  }

  @Test
  fun omitsSubtitleWhenNotProvided() = runTest {
    composition {
      TabwebPageHeader(title = "Reports")
    }

    val html = root.innerHTML
    assertThat(html).doesNotContain("Last 30 days")
  }

  @Test
  @Ignore
  fun rendersBreadcrumbsWhenProvided() = runTest {
    composition {
      TabwebPageHeader(
        title = "Users",
        breadcrumbs = listOf(
          BreadcrumbItem(label = "Home", href = "/"),
          BreadcrumbItem(label = "Users"),
        ),
      )
    }

    val html = root.innerHTML
    assertThat(html).contains("breadcrumb")
    assertThat(html).contains("Home")
  }

  @Test
  fun omitsBreadcrumbsWhenListIsEmpty() = runTest {
    composition {
      TabwebPageHeader(title = "Users")
    }

    val html = root.innerHTML
    assertThat(html).doesNotContain("breadcrumb")
  }
}
