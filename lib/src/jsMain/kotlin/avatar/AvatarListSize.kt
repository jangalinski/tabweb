package com.github.jangalinski.tabweb.avatar

import com.github.jangalinski.tabweb._foundation.TabwebSize
import com.github.jangalinski.tabweb._foundation.TabwebValue
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames

/**
 * Enumerates the supported Tabweb sizes for an [AvatarList].
 */
enum class AvatarListSize(private val value: String) : TabwebSize, TabwebValue<String>, Modifier {
  XXS("avatar-list-xxs"),
  XS("avatar-list-xs"),
  S("avatar-list-sm"),
  M("avatar-list-md"),
  L("avatar-list-lg"),
  XL("avatar-list-xl"),
  XXL("avatar-list-2xl"),
  ;

  companion object {
    val DEFAULT = M
  }

  private val modifier: Modifier by lazy {
    if (value.isEmpty()) Modifier else Modifier.classNames(value)
  }

  override fun <R> fold(initial: R, operation: (R, Modifier.Element) -> R): R = modifier.fold(initial, operation)

  override fun then(other: Modifier): Modifier = modifier.then(other)

  override fun get() = value
}
