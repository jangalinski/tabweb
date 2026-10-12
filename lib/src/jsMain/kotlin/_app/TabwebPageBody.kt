package com.github.jangalinski.tabweb._app

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.compose.ContainerXL
import com.github.jangalinski.tabweb._foundation.compose.KMain
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.id

/**
 * Renders the semantic main-content block of a Tabweb page.
 *
 * This is the preview's `<!-- BEGIN PAGE BODY -->` region and emits
 * `<main id="content" class="page-body">`. The stable `content` id supports
 * the preview's skip link, while [ContainerXL] preserves Tabweb's standard
 * content width. In a Kobweb layout, route content is supplied through
 * [content] and remains inside the shared page shell.
 *
 * @param modifier additional attributes or classes applied to the `<main>`
 *   element; `page-body` is always added.
 * @param content route content rendered inside the standard Tabweb container.
 */
@Composable
fun TabwebPageBody(
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit,
) {
  KMain(
    modifier = modifier
      .then(Modifier.classNames("page-body"))
      .then(Modifier.id("content"))
  ) {
    ContainerXL {
      content()
    }
  }
}
