package com.github.jangalinski.tabweb.navbar

import com.github.jangalinski.tabweb._foundation.css.CssClass
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames

enum class TablerNavbarCss(val value: String) : CssClass  {
  NAVBAR("navbar"),
  NAVBAR_NAV("navbar-nav"),
  NAVBAR_NAV_ITEM("navbar-nav-item"),
  NAVBAR_EXPAND_MD("navbar-expand-md"),
  NAVBAR_BRAND("navbar-brand"),
  NAVBAR_BRAND_AUTODARK("navbar-brand-autodark"),
  ;


  private val modifier: Modifier by lazy {
    Modifier.classNames(value)
  }

  override fun <R> fold(initial: R, operation: (R, Modifier.Element) -> R): R = modifier.fold(initial, operation)

  override fun then(other: Modifier): Modifier = modifier.then(other)


}
