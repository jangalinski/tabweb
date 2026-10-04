package com.github.jangalinski.tabweb.icon

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebFoundationComponent
import com.github.jangalinski.tabweb._foundation.compose.KI
import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.github.jangalinski.tabweb._foundation.css.plus
import com.varabyte.kobweb.compose.ui.Modifier

val CSS_ICON = cssClass("icon")

/**
 * A Tabler icon component.
 *
 * @see [TablerIcon] for a list of all available icons.
 * @see https://docs.tabler.io/ui/components/icons for more information about Tabler icons.
 */
interface Icon : TabwebFoundationComponent

@Composable
fun icon(icon: Modifier) = object : Icon {

  @Composable
  override fun invoke(modifier: Modifier) {
    val allModifier = CSS_ICON + icon + modifier

    KI(allModifier)
  }
}
