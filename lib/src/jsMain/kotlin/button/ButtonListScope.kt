package com.github.jangalinski.tabweb.button

import com.github.jangalinski.tabweb._foundation.TabwebDsl
import com.github.jangalinski.tabweb.icon.Icon

/**
 * Provides the children for a button list DSL.
 */
@TabwebDsl
class ButtonListScope internal constructor() {
  internal val buttons = mutableListOf<Button>()

  /**
   * Adds an already configured [Button] to this list.
   *
   * @param button component instance to add in display order.
   * @return `Unit` after [button] has been added to this list.
   */
  fun button(button: Button) {
    buttons += button
  }

  /**
   * Creates and adds a text [Button] to this list.
   *
   * @param text label shown by the button.
   * @param color theme, palette, or social color applied to the button.
   * @param style visual treatment for the button.
   * @param size size of the button.
   * @param shape corner shape for the button.
   * @param loading whether the button shows the Tabweb loading indicator and is disabled.
   * @param disabled whether the button is disabled.
   * @return `Unit` after the configured button has been added to this list.
   */
  fun button(
    text: String,
    color: ButtonColor = ButtonColor.DEFAULT,
    style: ButtonStyle = ButtonStyle.DEFAULT,
    size: ButtonSize = ButtonSize.DEFAULT,
    shape: ButtonShape = ButtonShape.DEFAULT,
    loading: Boolean = false,
    disabled: Boolean = loading,
  ) {
    button(Button(text, color, style, size, shape, loading, disabled))
  }

  /**
   * Creates and adds a text [Button] with an icon at either edge.
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
   * @return `Unit` after the configured button has been added to this list.
   */
  fun button(
    text: String,
    icon: Icon,
    iconPosition: ButtonIconPosition = ButtonIconPosition.LEFT,
    color: ButtonColor = ButtonColor.DEFAULT,
    style: ButtonStyle = ButtonStyle.DEFAULT,
    size: ButtonSize = ButtonSize.DEFAULT,
    shape: ButtonShape = ButtonShape.DEFAULT,
    loading: Boolean = false,
    disabled: Boolean = loading,
  ) {
    button(Button(text, icon, iconPosition, color, style, size, shape, loading, disabled))
  }

  /**
   * Creates and adds an icon-only [Button] to this list.
   *
   * @param icon icon shown by the button.
   * @param ariaLabel accessible name written to the button's `aria-label` attribute.
   * @param color theme, palette, or social color applied to the button.
   * @param style visual treatment for the button.
   * @param size size of the button.
   * @param shape corner shape for the button.
   * @param loading whether the button shows the Tabweb loading indicator and is disabled.
   * @param disabled whether the button is disabled.
   * @return `Unit` after the configured button has been added to this list.
   */
  fun button(
    icon: Icon,
    ariaLabel: String,
    color: ButtonColor = ButtonColor.DEFAULT,
    style: ButtonStyle = ButtonStyle.DEFAULT,
    size: ButtonSize = ButtonSize.DEFAULT,
    shape: ButtonShape = ButtonShape.DEFAULT,
    loading: Boolean = false,
    disabled: Boolean = loading,
  ) {
    button(Button(icon, ariaLabel, color, style, size, shape, loading, disabled))
  }
}
