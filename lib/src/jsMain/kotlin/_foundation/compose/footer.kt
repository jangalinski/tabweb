package com.github.jangalinski.tabweb._foundation.compose

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.css.BaseCss.PRINT_NONE
import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.github.jangalinski.tabweb._foundation.css.plus
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.dom.Footer

private val CSS_FOOTER = cssClass("footer")
private val CSS_FOOTER_TRANSPARENT = cssClass("footer")

/**
 * Renders the standard Tabweb footer landmark and its responsive container.
 *
 * @param modifier additional attributes, classes, and styles for the footer.
 * @param content composable footer content.
 */
@Composable
fun KFooter(
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit,
) {
  Footer(attrs = (modifier + CSS_FOOTER + CSS_FOOTER_TRANSPARENT + PRINT_NONE).toAttrs()) {
    ContainerXL {
      content()
    }
  }
}
