package com.github.jangalinski.tabweb._foundation

import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames

/**
 * A marker interface for modifiers that represent a CSS class name.
 */
interface CssClass : Modifier

data object TabwebCss {
  internal val cache = mutableMapOf<String, Modifier>()

  internal fun split(name: String) = name.split(" ").map { it.trim() }.filter { it.isNotBlank() }

  internal fun key(name: String) : String = key(split(name))

  internal fun key(classNames: List<String>) : String = classNames
    .filter { it.isNotBlank() }
    .sorted()
    .distinct()
    .joinToString(" ")


  internal operator fun get(name: String) : Modifier = with(key(name)) {
    cache.getOrPut(this) {
      if (this.isNotBlank()) Modifier.classNames(split(this)) else Modifier
    }
  }

  internal val VALID_CLASS_NAME_REGEX = Regex("""^[a-zA-Z0-9-]+( [a-zA-Z0-9-]+)*$""")

  value class ClassNameModifier(val name: String) : TabwebValue<String>, CssClass {
    init {
      require(VALID_CLASS_NAME_REGEX.matches(name)) {
        "CSS class name must only contain letters, digits, and hyphens separated by single spaces, but was '$name'."
      }
    }

    override fun get(): String = name
    override fun <R> fold(initial: R, operation: (R, Modifier.Element) -> R): R = TabwebCss[name].fold(initial, operation)
    override fun then(other: Modifier): Modifier = TabwebCss[name].then(other)

    operator fun plus(other: ClassNameModifier): ClassNameModifier = this + other.name
    operator fun plus(other: String): ClassNameModifier = ClassNameModifier(key("$name $other"))
  }


  val row = cssClass("row")

}
