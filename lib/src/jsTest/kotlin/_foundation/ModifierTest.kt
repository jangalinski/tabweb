package com.github.jangalinski.tabweb._foundation

import assertk.assertThat
import assertk.assertions.contains
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.github.jangalinski.tabweb.KobwebTabwebFixture
import org.jetbrains.compose.web.testutils.ComposeWebExperimentalTestsApi
import org.jetbrains.compose.web.testutils.runTest
import kotlin.test.Test

@OptIn(ComposeWebExperimentalTestsApi::class)
class ModifierTest {

  @Test
  fun readsClassesFromTheRenderedElement() = runTest {
    val modifier = Modifier.classNames("foo", "bar")
    lateinit var classes: List<String>

    composition {
      classes = KobwebTabwebFixture.extractClasses(modifier).value
    }

    waitForRecompositionComplete()

    assertThat(classes).contains("foo")
    assertThat(classes).contains("bar")
  }

}
