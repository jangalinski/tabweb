package com.github.jangalinski.tabweb.element

import com.github.jangalinski.tabweb._foundation.*
import com.github.jangalinski.tabweb._foundation.TabwebText.Companion.plain
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.modifiers.attr
import kotlinx.browser.window
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent
import kotlin.js.json

/**
 * Adds a Tabler tooltip without occupying the element's `data-bs-toggle` attribute.
 *
 * Initializes the tooltip when the element enters the composition and disposes it when the
 * modifier changes or the element leaves. Requires the Tabler JavaScript bundle to be loaded.
 * Pointer clicks dismiss the tooltip and release focus; keyboard activation retains focus.
 */
interface Tooltip : AsModifier {

  /**
   * Content displayed by the tooltip.
   */
  val text: TabwebText

  /**
   * Side of the element on which the tooltip appears.
   */
  val placement: Placement get() = Placement.BOTTOM

  companion object {
    internal const val CLASS = "tooltip"

    /** Creates a tooltip from plain text, HTML, or Markdown at the given [placement]. */
    operator fun invoke(text: TabwebText, placement: Placement = Placement.BOTTOM) = TooltipData(text, placement) as Tooltip

    /** Creates a plain-text tooltip at the given [placement]. */
    operator fun invoke(text: String, placement: Placement = Placement.BOTTOM) = TooltipData(plain(text), placement) as Tooltip

    internal data class TooltipData(
      override val text: TabwebText,
      override val placement: Placement
    ) : Tooltip {

      override val modifier: Modifier by lazy {
        val isHtml = text !is PlainText
        val title = if (text is MarkdownText) text.html else text.get()
        val options = TooltipOptions(title, placement.get(), isHtml)

        Modifier
          .attr("data-tblr-toggle", CLASS)
          .attr("data-bs-placement", placement.get())
          .attr("title", title)
          .attr("data-bs-html", "$isHtml")
          .attrsModifier {
            // Compose HTML refs only run on insertion; properties also run after recomposition.
            prop({ element: HTMLElement, value: TooltipOptions -> updateTooltip(element, value) }, options)
            ref { element ->
              updateTooltip(element, options)
              val onClick: (Event) -> Unit = { event ->
                if (event is MouseEvent && event.detail > 0) {
                  (element as? HTMLElement)?.blur()
                  activeTooltips[element]?.instance?.hide()
                }
              }
              element.addEventListener("click", onClick)
              onDispose {
                element.removeEventListener("click", onClick)
                activeTooltips.remove(element)?.instance?.dispose()
              }
            }
          }
      }
    }


    private data class TooltipOptions(val title: String, val placement: String, val html: Boolean)
    private data class ActiveTooltip(val options: TooltipOptions, val instance: TooltipInstance)

    private val activeTooltips = mutableMapOf<Element, ActiveTooltip>()

    private fun updateTooltip(element: Element, options: TooltipOptions) {
      if (activeTooltips[element]?.options == options) return
      val tooltip = window.asDynamic().tabler?.Tooltip
      if (tooltip == null) return
      val existing: TooltipInstance? = tooltip.getInstance(element)
      existing?.dispose()
      val instance: TooltipInstance = tooltip.getOrCreateInstance(
        element,
        json(
          "title" to options.title,
          "placement" to options.placement,
          "html" to options.html,
          "delay" to json("show" to 50, "hide" to 50),
        ),
      )
      activeTooltips[element] = ActiveTooltip(options, instance)
    }

  }

}

private external interface TooltipInstance {
  fun dispose()
  fun hide()
}
