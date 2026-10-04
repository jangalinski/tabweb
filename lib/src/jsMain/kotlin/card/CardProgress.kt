package com.github.jangalinski.tabweb.card

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebComponent
import com.github.jangalinski.tabweb._foundation.ariaLabel
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.compose.KSpan
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.github.jangalinski.tabweb._foundation.css.plus
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.attr
import com.varabyte.kobweb.compose.ui.modifiers.width
import org.jetbrains.compose.web.css.percent

/**
 * Progress bar rendered at the top of a card.
 */
interface CardProgress : TabwebComponent {
  val value: Int
  val color: BackgroundColor
  val label: String?

  @Composable
  override fun invoke(modifier: Modifier) {
    val progressLabel = label ?: "$value% Complete"
    KDiv(modifier = CardCss.CARD_PROGRESS + modifier) {
      KDiv(
        modifier = cssClass("progress-bar") + color +
          Modifier
            .width(value.percent)
            .attr("role", "progressbar")
            .attr("aria-valuenow", "$value")
            .attr("aria-valuemin", "0")
            .attr("aria-valuemax", "100")
            .ariaLabel(progressLabel),
      ) {
        KSpan(modifier = cssClass("visually-hidden")) {
          KText(progressLabel)
        }
      }
    }
  }

  companion object {
    operator fun invoke(
      value: Int,
      color: BackgroundColor = BackgroundColor.SEMANTIC.PRIMARY,
      label: String? = null,
    ): CardProgress = object : CardProgress {
      override val value: Int = value
      override val color: BackgroundColor = color
      override val label: String? = label
    }
  }
}
