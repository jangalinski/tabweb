package com.github.jangalinski.tabweb.widget

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._app.LocalTabwebAppState
import com.github.jangalinski.tabweb._app.TabwebTheme
import com.github.jangalinski.tabweb._foundation.TabwebComponent
import com.github.jangalinski.tabweb.button.Button
import com.github.jangalinski.tabweb.button.ButtonColor
import com.github.jangalinski.tabweb.element.Tooltip
import com.github.jangalinski.tabweb.icon.TabwebIcon
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.onClick

/**
 * A widget button that toggles between light and dark modes in the application.
 */
data object DarkModeButton : TabwebComponent {

  @Composable
  override fun invoke(modifier: Modifier) {
    val tabweb = LocalTabwebAppState.current
    val darkMode = tabweb.settings.theme == TabwebTheme.DARK
    val targetMode = if (darkMode) TabwebTheme.LIGHT else TabwebTheme.DARK
    val targetName = if (darkMode) "light" else "dark"

    Button(
      icon = if (darkMode) TabwebIcon.TI_SUN else TabwebIcon.TI_MOON,
      ariaLabel = "Enable $targetName mode",
      color = if (darkMode) ButtonColor.DARK else ButtonColor.LIGHT,
    )(
      Tooltip("Enable $targetName mode").modifier
        .onClick { tabweb.setTheme(targetMode) }
        .then(modifier)
    )
  }
}
