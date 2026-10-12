package com.github.jangalinski.tabweb._app

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Stable, observable owner of the current [TabwebSettings].
 *
 * Settings changes replace the immutable snapshot, allowing Compose and the
 * document synchronization layer to react consistently.
 */
@Stable
class TabwebAppState(initialSettings: TabwebSettings) {

  var settings: TabwebSettings by mutableStateOf(initialSettings)
    private set

  /** Replaces [settings] with the result of [transform]. */
  fun update(transform: (TabwebSettings) -> TabwebSettings) {
    settings = transform(settings)
  }

  /** Selects [theme] as the current Tabweb color mode. */
  fun setTheme(theme: TabwebTheme) {
    update { it.copy(theme = theme) }
  }
}
