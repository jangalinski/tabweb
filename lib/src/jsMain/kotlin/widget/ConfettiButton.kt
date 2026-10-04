package com.github.jangalinski.tabweb.widget

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb.button.Button
import com.github.jangalinski.tabweb.button.ButtonColor
import com.github.jangalinski.tabweb.button.ButtonContent
import com.github.jangalinski.tabweb.button.ButtonIconPosition
import com.github.jangalinski.tabweb.button.ButtonShape
import com.github.jangalinski.tabweb.button.ButtonSize
import com.github.jangalinski.tabweb.button.ButtonStyle
import com.github.jangalinski.tabweb.icon.Icon
import com.github.jangalinski.tabweb.icon.TablerIcon
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.attr

/**
 * A [Button] widget that triggers Tabler's confetti shower effect when clicked.
 *
 * @param content typed text or icon content rendered by the button.
 * @param color theme, palette, or social color applied to the button.
 * @param style visual treatment for the button.
 * @param size size of the button.
 * @param shape corner shape for the button.
 * @param loading whether the button displays Tabler's loading indicator and is disabled.
 * @param disabled whether the button is disabled.
 * @param count number of confetti pieces poured.
 * @param duration duration of the confetti shower in milliseconds.
 * @param colors list of custom colors (e.g. hex codes) for the confetti pieces.
 * @param speed speed scale for the fall (1.0 is default, lower floats, higher drops).
 * @param target selector of the element on which confetti events fire and are targeted.
 */
data class ConfettiButton(
  override val content: ButtonContent = ButtonContent.IconOnly(
    icon = TablerIcon.TI_CONFETTI,
    ariaLabel = "Confetti button",
  ),
  override val color: ButtonColor = ButtonColor.LIGHT,
  override val style: ButtonStyle = ButtonStyle.DEFAULT,
  override val size: ButtonSize = ButtonSize.DEFAULT,
  override val shape: ButtonShape = ButtonShape.DEFAULT,
  override val loading: Boolean = false,
  override val disabled: Boolean = loading,
  val count: Int? = null,
  val duration: Int? = null,
  val colors: List<String>? = null,
  val speed: Number? = null,
  val target: String? = null,
) : Button {

  companion object {
    /**
     * Creates a text-labeled [ConfettiButton].
     *
     * @param text label shown by the button.
     * @param color theme, palette, or social color applied to the button.
     * @param style visual treatment for the button.
     * @param size size of the button.
     * @param shape corner shape for the button.
     * @param loading whether the button shows the Tabler loading indicator and is disabled.
     * @param disabled whether the button is disabled.
     * @param count number of confetti pieces poured.
     * @param duration duration of the confetti shower in milliseconds.
     * @param colors list of custom colors for the confetti pieces.
     * @param speed speed scale for the fall.
     * @param target selector of the target element.
     * @return a configured [ConfettiButton] component instance.
     */
    operator fun invoke(
      text: String,
      color: ButtonColor = ButtonColor.LIGHT,
      style: ButtonStyle = ButtonStyle.DEFAULT,
      size: ButtonSize = ButtonSize.DEFAULT,
      shape: ButtonShape = ButtonShape.DEFAULT,
      loading: Boolean = false,
      disabled: Boolean = loading,
      count: Int? = null,
      duration: Int? = null,
      colors: List<String>? = null,
      speed: Number? = null,
      target: String? = null,
    ): ConfettiButton = ConfettiButton(
      content = ButtonContent.Text(text),
      color = color,
      style = style,
      size = size,
      shape = shape,
      loading = loading,
      disabled = disabled,
      count = count,
      duration = duration,
      colors = colors,
      speed = speed,
      target = target,
    )

    /**
     * Creates a text [ConfettiButton] with an icon at either edge.
     *
     * @param text label shown by the button.
     * @param icon icon shown beside [text].
     * @param iconPosition edge at which [icon] is rendered.
     * @param color theme, palette, or social color applied to the button.
     * @param style visual treatment for the button.
     * @param size size of the button.
     * @param shape corner shape for the button.
     * @param loading whether the button shows the Tabler loading indicator and is disabled.
     * @param disabled whether the button is disabled.
     * @param count number of confetti pieces poured.
     * @param duration duration of the confetti shower in milliseconds.
     * @param colors list of custom colors for the confetti pieces.
     * @param speed speed scale for the fall.
     * @param target selector of the target element.
     * @return a configured [ConfettiButton] component instance.
     */
    operator fun invoke(
      text: String,
      icon: Icon,
      iconPosition: ButtonIconPosition = ButtonIconPosition.LEFT,
      color: ButtonColor = ButtonColor.LIGHT,
      style: ButtonStyle = ButtonStyle.DEFAULT,
      size: ButtonSize = ButtonSize.DEFAULT,
      shape: ButtonShape = ButtonShape.DEFAULT,
      loading: Boolean = false,
      disabled: Boolean = loading,
      count: Int? = null,
      duration: Int? = null,
      colors: List<String>? = null,
      speed: Number? = null,
      target: String? = null,
    ): ConfettiButton = ConfettiButton(
      content = ButtonContent.TextWithIcon(text, icon, iconPosition),
      color = color,
      style = style,
      size = size,
      shape = shape,
      loading = loading,
      disabled = disabled,
      count = count,
      duration = duration,
      colors = colors,
      speed = speed,
      target = target,
    )

    /**
     * Creates an icon-only [ConfettiButton].
     *
     * @param icon icon shown by the button.
     * @param ariaLabel accessible name written to the button's `aria-label` attribute.
     * @param color theme, palette, or social color applied to the button.
     * @param style visual treatment for the button.
     * @param size size of the button.
     * @param shape corner shape for the button.
     * @param loading whether the button shows the Tabler loading indicator and is disabled.
     * @param disabled whether the button is disabled.
     * @param count number of confetti pieces poured.
     * @param duration duration of the confetti shower in milliseconds.
     * @param colors list of custom colors for the confetti pieces.
     * @param speed speed scale for the fall.
     * @param target selector of the target element.
     * @return a configured [ConfettiButton] component instance.
     */
    operator fun invoke(
      icon: Icon = TablerIcon.TI_CONFETTI,
      ariaLabel: String = "Confetti button",
      color: ButtonColor = ButtonColor.LIGHT,
      style: ButtonStyle = ButtonStyle.DEFAULT,
      size: ButtonSize = ButtonSize.DEFAULT,
      shape: ButtonShape = ButtonShape.DEFAULT,
      loading: Boolean = false,
      disabled: Boolean = loading,
      count: Int? = null,
      duration: Int? = null,
      colors: List<String>? = null,
      speed: Number? = null,
      target: String? = null,
    ): ConfettiButton = ConfettiButton(
      content = ButtonContent.IconOnly(icon, ariaLabel),
      color = color,
      style = style,
      size = size,
      shape = shape,
      loading = loading,
      disabled = disabled,
      count = count,
      duration = duration,
      colors = colors,
      speed = speed,
      target = target,
    )
  }

  @Composable
  override fun invoke(modifier: Modifier) {
    val confettiModifier = modifier
      .attr("data-bs-toggle", "confetti")
      .then(if (count != null) Modifier.attr("data-bs-count", count.toString()) else Modifier)
      .then(if (duration != null) Modifier.attr("data-bs-duration", duration.toString()) else Modifier)
      .then(if (!colors.isNullOrEmpty()) Modifier.attr("data-bs-colors", colors.joinToString(", ")) else Modifier)
      .then(if (speed != null) Modifier.attr("data-bs-speed", speed.toString()) else Modifier)
      .then(if (target != null) Modifier.attr("data-bs-target", target) else Modifier)

    when (val content = content) {
      is ButtonContent.Text -> Button(
        text = content.value,
        color = color,
        style = style,
        size = size,
        shape = shape,
        loading = loading,
        disabled = disabled,
      )(confettiModifier)
      is ButtonContent.TextWithIcon -> Button(
        text = content.text,
        icon = content.icon,
        iconPosition = content.position,
        color = color,
        style = style,
        size = size,
        shape = shape,
        loading = loading,
        disabled = disabled,
      )(confettiModifier)
      is ButtonContent.IconOnly -> Button(
        icon = content.icon,
        ariaLabel = content.ariaLabel,
        color = color,
        style = style,
        size = size,
        shape = shape,
        loading = loading,
        disabled = disabled,
      )(confettiModifier)
    }
  }
}
