package com.github.jangalinski.tabweb.widget

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._app.LocalTablerAppState
import com.github.jangalinski.tabweb._app.TablerTheme
import com.github.jangalinski.tabweb._foundation.TabwebComponent
import com.github.jangalinski.tabweb.button.Button
import com.github.jangalinski.tabweb.button.ButtonColor
import com.github.jangalinski.tabweb.icon.TablerIcon
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.title

/**
 * A widget button that toggles between light and dark modes in the application.
 */
data object DarkModeButton : TabwebComponent {

  @Composable
  override fun invoke(modifier: Modifier) {
    val tabler = LocalTablerAppState.current
    val darkMode = tabler.settings.theme == TablerTheme.Dark
    val targetMode = if (darkMode) TablerTheme.Light else TablerTheme.Dark
    val targetName = if (darkMode) "light" else "dark"

    Button(
      icon = if (darkMode) TablerIcon.TI_SUN else TablerIcon.TI_MOON,
      ariaLabel = "Enable $targetName mode",
      color = if (darkMode) ButtonColor.DARK else ButtonColor.LIGHT,
    )(
      Modifier
        .title("Enable $targetName mode")
        .onClick { tabler.setTheme(targetMode) }
        .then(modifier)
    )
  }
}
