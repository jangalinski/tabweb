package com.github.jangalinski.tabweb._foundation.css

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames

fun cssClass(name: String) : CssClass {
  require(!name.contains(Regex("\\s"))) { "cssClass must not contain multiple values: '$name'. Combine them using '+'." }
  return object : AbstractLazyCssModifier(), CssClass {
    override val value: String = name
  }
}

/**
 * A marker interface for modifiers that represent a CSS class name.
 */
interface CssClass : Modifier

/**
 * A marker interface for modifiers that represent a CSS size, such as `width`, `height`, `max-width`, etc.
 */
interface CssSize : CssClass

/**
 * A marker interface for modifiers that represent a CSS color, such as `color`, `background-color`, etc.
 */
interface CssColor : CssClass

/**
 * A marker interface for modifiers that represent a CSS direction, such as `left`, `right`, `top`, `bottom`, etc.
 */
interface CssDirection : CssClass
