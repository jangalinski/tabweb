package com.github.jangalinski.tabweb._foundation.compose

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.Form
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Label

/**
 * Internal DOM adapter for a form with Kobweb modifier support.
 */
@Composable
fun KForm(modifier: Modifier = Modifier, content: @Composable () -> Unit = {}) {
  Form(attrs = modifier.toAttrs()) {
    content()
  }
}

/**
 * Internal DOM adapter for a label with Kobweb modifier support.
 */
@Composable
fun KLabel(modifier: Modifier = Modifier, content: @Composable () -> Unit = {}) {
  Label(attrs = modifier.toAttrs()) {
    content()
  }
}

/**
 * Internal DOM adapter for an input element with Kobweb modifier support.
 */
@Composable
fun KInput(
  type: InputType<*> = InputType.Text,
  value: String? = null,
  modifier: Modifier = Modifier,
) {
  Input(
    type = type,
    attrs = modifier.toAttrs {
      if (value != null) {
        value(value)
      }
    },
  )
}
