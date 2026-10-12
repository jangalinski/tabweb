package com.github.jangalinski.tabweb.badge

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames

/**
 * Enumerates the supported Tabweb sizes for a [Badge].
 */
enum class BadgeSize(private val className: String) {
  S("badge-sm"),
  M(""),
  L("badge-lg"),
  ;

  companion object {
    val DEFAULT = M
  }

  private val modifier: Modifier by lazy {
    if (className.isEmpty()) Modifier else Modifier.classNames(className)
  }

  internal fun toBadgeModifier(): Modifier = modifier
}
