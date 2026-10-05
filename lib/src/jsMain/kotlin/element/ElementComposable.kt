package com.github.jangalinski.tabweb.element

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebComposable
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * A composable interface that provides methods for rendering Tabler UI elements.
 */
interface ElementComposable : TabwebComposable {

  /**
   * Renders a divider component that can optionally display text.
   *
   * @param text The optional text to display in the divider.
   * @param modifier The modifier to apply to the divider component.
   */
  @Composable
  fun divider(text: String? = null, modifier: Modifier = Modifier)

  /**
   * Renders a status component that displays a text with a specific style.
   *
   * @param text The text to display in the status component.
   * @param modifier The modifier to apply to the status component.
   */
  @Composable
  fun status(text: String, modifier: Modifier = Modifier)

  @Composable
  fun tracking(blocks: List<Tracking.Block>, square: Boolean = false, modifier: Modifier = Modifier)

  @Composable
  fun tracking(vararg blocks: Tracking.Block, square: Boolean = false, modifier: Modifier = Modifier) = tracking(blocks.toList(), square, modifier)
}
