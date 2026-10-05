package com.github.jangalinski.tabweb._app

import androidx.compose.runtime.Immutable

/**
 * Immutable snapshot of Tabler presentation settings for the current application state.
 */
@Immutable
data class TablerSettings(
  val theme: TablerTheme = TablerTheme.System,
)

/**
 * Selects the color mode applied to the document for Tabler components.
 */
enum class TablerTheme {
  /**
   * Leaves `data-bs-theme` unset so Tabler uses its system/default behavior.
   */
  System,

  /**
   * Applies Tabler's light color mode.
   */
  Light,

  /**
   * Applies Tabler's dark color mode.
   */
  Dark,
  ;

}
