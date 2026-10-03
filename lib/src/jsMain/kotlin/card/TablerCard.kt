package com.github.jangalinski.tabweb.card

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.ui.Modifier
import com.github.jangalinski.tabweb._foundation.compose.KH3
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.css.ClassNames
import com.github.jangalinski.tabweb._foundation.css.ClassNames.modifier

/**
 * Renders a standard Tabler card with an optional title.
 */
@Composable
@Deprecated("Use Card")
fun TablerCard(
  title: String? = null,
  modifier: Modifier = Modifier,
  content: @Composable ColumnScope.() -> Unit,
) {
  Column(modifier = modifier.then(ClassNames.card.modifier())) {
    title?.let {
      Column(modifier = ClassNames.cardHeader.modifier()) {
        KH3(modifier = ClassNames.cardTitle.modifier()) {
          KText(it)
        }
      }
    }

    Column(
      modifier = ClassNames.cardBody.modifier(),
      content = content,
    )
  }
}
