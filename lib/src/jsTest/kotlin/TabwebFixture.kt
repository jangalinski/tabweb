package com.github.jangalinski.tabweb

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.toAttrs
import kotlinx.browser.document
import org.jetbrains.compose.web.dom.Div

data object KobwebTabwebFixture {

  /**
   * Applies [modifier] to a fixture element and exposes the classes rendered by Kobweb.
   *
   * This must be called from a Compose composition, for example inside
   * `runTest { composition { ... } }`. The returned state is populated after
   * the element has been applied to the DOM.
   */
  @Composable
  fun extractClasses(modifier: Modifier): State<List<String>> {
    val classes = remember { mutableStateOf<List<String>>(emptyList()) }

    Div(
      attrs = modifier.toAttrs {
        attr("data-tabweb-fixture", "modifier")
      },
    )

    SideEffect {
      val classAttribute = document
        .querySelector("[data-tabweb-fixture=modifier]")
        ?.getAttribute("class")
        .orEmpty()

      classes.value = classAttribute
        .split(Regex("\\s+"))
        .filter(String::isNotBlank)
    }

    return classes
  }
}
