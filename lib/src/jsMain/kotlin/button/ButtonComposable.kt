package com.github.jangalinski.tabweb.button

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebComposable
import com.github.jangalinski.tabweb._foundation.TabwebDirection
import com.github.jangalinski.tabweb.icon.Icon
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Provides page-level composable entry points for the button concept.
 */
interface ButtonComposable : TabwebComposable {
  /**
   * Creates and renders a text [Button].
   *
   * @param text label shown by the button.
   * @param color theme, palette, or social color applied to the button.
   * @param style visual treatment for the button.
   * @param size size of the button.
   * @param shape corner shape for the button.
   * @param loading whether the button shows the Tabweb loading indicator and is disabled.
   * @param disabled whether the button is disabled.
   * @param onClick action invoked after a user clicks an enabled button.
   * @param modifier additional attributes and styles applied to the button root.
   * @return `Unit` after the button has been emitted into the current composition.
   */
  @Composable
  fun button(
    text: String,
    color: ButtonColor = ButtonColor.DEFAULT,
    style: ButtonStyle = ButtonStyle.DEFAULT,
    size: ButtonSize = ButtonSize.DEFAULT,
    shape: ButtonShape = ButtonShape.DEFAULT,
    loading: Boolean = false,
    disabled: Boolean = loading,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
  )

  /**
   * Creates and renders a text [Button] with an icon at either edge.
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
   * @param onClick action invoked after a user clicks an enabled button.
   * @param modifier additional attributes and styles applied to the button root.
   * @return `Unit` after the button has been emitted into the current composition.
   */
  @Composable
  fun button(
    text: String,
    icon: Icon,
    iconPosition: TabwebDirection.Horizontal = TabwebDirection.LEFT,
    color: ButtonColor = ButtonColor.DEFAULT,
    style: ButtonStyle = ButtonStyle.DEFAULT,
    size: ButtonSize = ButtonSize.DEFAULT,
    shape: ButtonShape = ButtonShape.DEFAULT,
    loading: Boolean = false,
    disabled: Boolean = loading,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
  )

  /**
   * Creates and renders an icon-only [Button].
   *
   * @param icon icon shown by the button.
   * @param ariaLabel accessible name written to the button's `aria-label` attribute.
   * @param color theme, palette, or social color applied to the button.
   * @param style visual treatment for the button.
   * @param size size of the button.
   * @param shape corner shape for the button.
   * @param loading whether the button shows the Tabweb loading indicator and is disabled.
   * @param disabled whether the button is disabled.
   * @param onClick action invoked after a user clicks an enabled button.
   * @param modifier additional attributes and styles applied to the button root.
   * @return `Unit` after the button has been emitted into the current composition.
   */
  @Composable
  fun button(
    icon: Icon,
    ariaLabel: String,
    color: ButtonColor = ButtonColor.DEFAULT,
    style: ButtonStyle = ButtonStyle.DEFAULT,
    size: ButtonSize = ButtonSize.DEFAULT,
    shape: ButtonShape = ButtonShape.DEFAULT,
    loading: Boolean = false,
    disabled: Boolean = loading,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
  )

  /**
   * Creates and renders a [ButtonList] from typed child declarations.
   *
   * @param modifier additional attributes and styles applied to the list root.
   * @param content DSL block that adds buttons to the list.
   * @return `Unit` after the button list has been emitted into the current composition.
   */
  @Composable
  fun buttons(
    modifier: Modifier = Modifier,
    content: ButtonListScope.() -> Unit,
  )
}
