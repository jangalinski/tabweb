package com.github.jangalinski.tabweb.element

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.Placement
import com.github.jangalinski.tabweb._foundation.TabwebComponent
import com.github.jangalinski.tabweb._foundation.TabwebText
import com.github.jangalinski.tabweb._foundation.TabwebText.Companion.plain
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb._foundation.plus
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Renders Tabler's compact activity-monitoring display.
 *
 * When a [Tracking.Block.text] is supplied, the block is configured for Tabler's top-positioned
 * Bootstrap tooltip and retains its native `title` text as a non-JavaScript fallback.
 *
 * https://docs.tabler.io/ui/components/tracking
 */
interface Tracking : TabwebComponent {

  val blocks: List<Block>
  val square: Boolean

  companion object {

    operator fun invoke(
      blocks: List<Block>,
      square: Boolean
    ) = object : Tracking {
      override val blocks: List<Block> = blocks
      override val square: Boolean = square
    }

    internal val CLASS = cssClass("tracking")
    internal val SQUARE = cssClass("tracking-squares")
    internal val BLOCK = cssClass("tracking-block")
  }

  data class Block(
    val text: TabwebText,
    val color: BackgroundColor
  ) {
    constructor(text: String, color: BackgroundColor) : this(plain(text), color)
  }

  @Composable
  override fun invoke(modifier: Modifier) = KDiv(modifier = CLASS + (if (square) SQUARE else Modifier) + modifier) {
    // IR breaks if we try to tune this for loop, leave as is!
    for (block in blocks) {
      val tooltip = Tooltip(text = block.text, placement = Placement.TOP)

      KDiv(
        modifier = BLOCK + block.color + tooltip.modifier + modifier
      )
    }
  }
}
