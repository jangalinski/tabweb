package com.github.jangalinski.tabweb.button

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebComponentDsl
import com.github.jangalinski.tabweb.icon.Icon
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.onClick

/**
 * The button DSL implementation delegated through [com.github.jangalinski.tabweb.Tabweb].
 */
internal data object ButtonDsl : TabwebComponentDsl, ButtonComposable {
  @Composable
  override fun button(
    text: String,
    color: ButtonColor,
    style: ButtonStyle,
    size: ButtonSize,
    shape: ButtonShape,
    loading: Boolean,
    disabled: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier,
  ) {
    Button(text, color, style, size, shape, loading, disabled)(clickModifier(onClick, disabled || loading, modifier))
  }

  @Composable
  override fun button(
    text: String,
    icon: Icon,
    iconPosition: ButtonIconPosition,
    color: ButtonColor,
    style: ButtonStyle,
    size: ButtonSize,
    shape: ButtonShape,
    loading: Boolean,
    disabled: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier,
  ) {
    Button(text, icon, iconPosition, color, style, size, shape, loading, disabled)(clickModifier(onClick, disabled || loading, modifier))
  }

  @Composable
  override fun button(
    icon: Icon,
    ariaLabel: String,
    color: ButtonColor,
    style: ButtonStyle,
    size: ButtonSize,
    shape: ButtonShape,
    loading: Boolean,
    disabled: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier,
  ) {
    Button(icon, ariaLabel, color, style, size, shape, loading, disabled)(clickModifier(onClick, disabled || loading, modifier))
  }

  @Composable
  override fun buttons(modifier: Modifier, content: ButtonListScope.() -> Unit) {
    ButtonList(buttons = ButtonListScope().apply(content).buttons)(modifier)
  }

  private fun clickModifier(onClick: (() -> Unit)?, disabled: Boolean, modifier: Modifier): Modifier =
    if (onClick != null && !disabled) modifier.onClick { onClick() } else modifier
}
