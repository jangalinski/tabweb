package com.github.jangalinski.tabweb.button

/**
 * Enumerates the supported Tabweb visual treatments for a [Button].
 */
enum class ButtonStyle {
  SOLID,
  OUTLINE,
  GHOST,
  LINK,
  ;

  companion object {
    /**
     * Default filled treatment used when no explicit style is supplied.
     */
    val DEFAULT = SOLID
  }
}
