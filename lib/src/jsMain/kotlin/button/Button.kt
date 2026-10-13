package com.github.jangalinski.tabweb.button

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.*
import com.github.jangalinski.tabweb._foundation.alignment.*
import com.github.jangalinski.tabweb._foundation.compose.KButton
import com.github.jangalinski.tabweb._foundation.compose.KSpan
import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.github.jangalinski.tabweb._foundation.css.plus
import com.github.jangalinski.tabweb._foundation.size.DEFAULT
import com.github.jangalinski.tabweb._foundation.size.SmallToLargeX
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.attr

/**
 * A Tabweb button for invoking a user action.
 */
interface Button : TabwebComponent {
  companion object {
    /**
     * Creates a configured text [Button].
     *
     * @param text label shown by the button.
     * @param color theme, palette, or social color applied to the button.
     * @param style visual treatment for the button.
     * @param size size of the button.
     * @param shape corner shape for the button.
     * @param loading whether the button shows the Tabweb loading indicator and is disabled.
     * @param disabled whether the button is disabled.
     * @return a configured [Button] component instance.
     */
    operator fun invoke(
      text: String,
      color: ButtonColor = ButtonColor.DEFAULT,
      style: ButtonStyle = ButtonStyle.DEFAULT,
      size: SmallToLargeX = DEFAULT,
      shape: ButtonShape = ButtonShape.DEFAULT,
      loading: Boolean = false,
      disabled: Boolean = loading,
    ): Button = button(
      content = ButtonContent.Text(text),
      color = color,
      style = style,
      size = size,
      shape = shape,
      loading = loading,
      disabled = disabled,
    )

    /**
     * Creates a text [Button] with an icon at either edge.
     *
     * @param text label shown by the button.
     * @param icon icon shown beside [text].
     * @param iconPosition edge at which [icon] is rendered.
     * @param color theme, palette, or social color applied to the button.
     * @param style visual treatment for the button.
     * @param size size of the button.
     * @param shape corner shape for the button.
     * @param loading whether the button shows the Tabweb loading indicator and is disabled.
     * @param disabled whether the button is disabled.
     * @return a configured [Button] component instance.
     */
    operator fun invoke(
      text: String,
      icon: com.github.jangalinski.tabweb.icon.Icon,
      iconPosition: Horizontal = LEFT,
      color: ButtonColor = ButtonColor.DEFAULT,
      style: ButtonStyle = ButtonStyle.DEFAULT,
      size: SmallToLargeX = DEFAULT,
      shape: ButtonShape = ButtonShape.DEFAULT,
      loading: Boolean = false,
      disabled: Boolean = loading,
    ): Button = button(
      content = ButtonContent.TextWithIcon(text, icon, iconPosition),
      color = color,
      style = style,
      size = size,
      shape = shape,
      loading = loading,
      disabled = disabled,
    )

    /**
     * Creates an icon-only [Button].
     *
     * @param icon icon shown by the button.
     * @param ariaLabel accessible name written to the button's `aria-label` attribute.
     * @param color theme, palette, or social color applied to the button.
     * @param style visual treatment for the button.
     * @param size size of the button.
     * @param shape corner shape for the button.
     * @param loading whether the button shows the Tabweb loading indicator and is disabled.
     * @param disabled whether the button is disabled.
     * @return a configured [Button] component instance.
     */
    operator fun invoke(
      icon: com.github.jangalinski.tabweb.icon.Icon,
      ariaLabel: String,
      color: ButtonColor = ButtonColor.DEFAULT,
      style: ButtonStyle = ButtonStyle.DEFAULT,
      size: SmallToLargeX = DEFAULT,
      shape: ButtonShape = ButtonShape.DEFAULT,
      loading: Boolean = false,
      disabled: Boolean = loading,
    ): Button = button(
      content = ButtonContent.IconOnly(icon, ariaLabel),
      color = color,
      style = style,
      size = size,
      shape = shape,
      loading = loading,
      disabled = disabled,
    )

    private fun button(
      content: ButtonContent,
      color: ButtonColor,
      style: ButtonStyle,
      size: SmallToLargeX,
      shape: ButtonShape,
      loading: Boolean,
      disabled: Boolean,
    ): Button = object : Button {
      override val content = content
      override val color = color
      override val style = style
      override val size = size
      override val shape = shape
      override val loading = loading
      override val disabled = disabled
    }
  }

  /**
   * Typed text and icon content rendered by the button.
   */
  val content: ButtonContent

  /**
   * Color applied to the button treatment.
   */
  val color: ButtonColor

  /**
   * Visual treatment applied to the button.
   */
  val style: ButtonStyle

  /**
   * Size applied to the button.
   */
  val size: SmallToLargeX

  /**
   * Corner shape applied to the button.
   */
  val shape: ButtonShape

  /**
   * Whether the button displays Tabweb's loading treatment.
   */
  val loading: Boolean

  /**
   * Whether the button is disabled.
   */
  val disabled: Boolean

  @Composable
  override fun invoke(modifier: Modifier) {
    val buttonModifier = ButtonCss.button + ButtonCss.color(color) + ButtonCss.style(style) + ButtonCss.size(size) +
      ButtonCss.shape(shape) + (if (content is ButtonContent.IconOnly) ButtonCss.iconOnly else Modifier) +
      (if (loading) ButtonCss.loading else Modifier)
    val stateModifier = modifier
      .ariaLabel((content as? ButtonContent.IconOnly)?.ariaLabel)
      .then(if (loading) Modifier.attr("aria-busy", "true") else Modifier)
      .then(if (disabled) Modifier.attr("disabled", "") else Modifier)

    KButton(modifier = buttonModifier + stateModifier) {
      when (val content = content) {
        is ButtonContent.Text -> KSpan(text = content.value)
        is ButtonContent.TextWithIcon -> {
          if (content.position == LEFT) content.icon(cssClass("me-2"))
          KSpan(text = content.text)
          if (content.position == RIGHT) content.icon(cssClass("ms-2"))
        }

        is ButtonContent.IconOnly -> {
          content.icon()
        }
      }
    }
  }
}
