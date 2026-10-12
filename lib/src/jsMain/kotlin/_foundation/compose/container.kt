package com.github.jangalinski.tabweb._foundation.compose

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.css.BaseCss.CONTAINER_XL
import com.github.jangalinski.tabweb._foundation.css.plus
import com.varabyte.kobweb.compose.ui.Modifier


/**
 * Applies Tabweb's standard extra-large content container.
 *
 * This is the repeated `.container-xl` block visible inside the preview's
 * page header, page body, and footer regions. It is kept as a small Kobweb
 * layout primitive so those regions share the same responsive width.
 *
 * @param modifier additional attributes, classes, and styles for the container.
 * @param content composable content placed inside the container.
 */
@Composable
fun ContainerXL(
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit
) = KDiv(modifier = modifier + CONTAINER_XL, content = content)
