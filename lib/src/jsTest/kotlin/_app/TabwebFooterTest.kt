package com.github.jangalinski.tabweb._app

import assertk.assertThat
import assertk.assertions.contains
import org.jetbrains.compose.web.testutils.ComposeWebExperimentalTestsApi
import org.jetbrains.compose.web.testutils.runTest
import org.jetbrains.compose.web.dom.Text
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore
@OptIn(ComposeWebExperimentalTestsApi::class)
class TabwebFooterTest {

  @Test
  fun rendersFooterClass() = runTest {
    composition {
      TabwebFooter()()
    }

    val html = root.innerHTML
    assertThat(html).contains("footer")
  }

  @Test
  fun rendersSlotContent() = runTest {
    composition {
      TabwebFooter(
        left = listOf(
          TabwebFooterItem { Text("© 2024 My App") },
        ),
      )()
    }

    val html = root.innerHTML
    assertThat(html).contains("© 2024 My App")
  }

  @Test
  fun rendersFooterTransparentClass() = runTest {
    composition {
      TabwebFooter()()
    }

    val html = root.innerHTML
    assertThat(html).contains("footer-transparent")
  }
}
