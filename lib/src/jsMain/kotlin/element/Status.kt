package com.github.jangalinski.tabweb.element

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebElement
import com.github.jangalinski.tabweb._foundation.compose.KSpan
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.github.jangalinski.tabweb._foundation.css.plus
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * A status component that displays a text with a specific style.
 *
 * https://docs.tabler.io/ui/components/status
 */
interface Status : TabwebElement {

  companion object {
    /**
     * Creates a new instance of [Status] with the given [text].
     */
    operator fun invoke(text: String): Status = object : Status {
      override val text: String = text
    }

    internal data object Css {
      val status = cssClass("status")
    }
  }

  val text: String

  @Composable
  override fun invoke(modifier: Modifier) {
    KSpan(modifier = Css.status + modifier) {
      KText(text)
    }
  }
}
