package com.github.jangalinski.tabweb.button

/**
 * Enumerates the supported Tabweb sizes for a [Button].
 */
enum class ButtonSize {
  SMALL,
  MEDIUM,
  LARGE,
  EXTRA_LARGE,
  ;

  companion object {
    /**
     * Default size used when no explicit size is supplied.
     */
    val DEFAULT = MEDIUM
  }
}
