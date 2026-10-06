package com.github.jangalinski.tabweb._foundation.css

import com.github.jangalinski.tabweb._foundation.TabwebValue
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames

abstract class AbstractLazyCssModifier : Modifier, TabwebValue<String> {
  abstract val value: String

  private val modifier: Modifier by lazy {
    if (value.isNotBlank()) Modifier.classNames(value) else Modifier
  }

  override fun <R> fold(initial: R, operation: (R, Modifier.Element) -> R): R = modifier.fold(initial, operation)

  override fun then(other: Modifier): Modifier = modifier + other

  override fun get() = value
}
