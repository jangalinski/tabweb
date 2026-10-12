@file:Suppress("DEPRECATION")

package com.github.jangalinski.tabweb.__trash.legacy_navigation

import com.github.jangalinski.tabweb._foundation.TabwebDsl
import com.github.jangalinski.tabweb._foundation.Image

/**
 * Hierarchical navigation model for Tabweb menus.
 */
@Deprecated(
  message = "Use navbar.TabwebNavbarItem for new navbar work. This legacy model remains for sidebar compatibility.",
)
sealed interface NavigationItem {

  /**
   * Human-readable label shown in the navbar.
   */
  val name: String

  /**
   * Optional icon shown next to the label.
   */
  val icon: Image?

  /**
   * Simple navigation link.
   */
  data class Link(
    override val name: String,
    val href: String,
    override val icon: Image? = null,
    val active: Boolean = false,
  ) : NavigationItem

  /**
   * Dropdown navigation item with nested links.
   */
  data class Dropdown(
    override val name: String,
    val href: String,
    override val icon: Image? = null,
    val active: Boolean = false,
    val items: List<Link>,
  ) : NavigationItem
}

/**
 * Builder for hierarchical Tabweb navigation items.
 */
@TabwebDsl
class NavigationItemsBuilder internal constructor() {

  private val _items = mutableListOf<NavigationItem>()

  /**
   * Builds the immutable item list.
   */
  fun build(): List<NavigationItem> = _items.toList()

  /**
   * Adds a simple link item.
   */
  fun link(
    name: String,
    href: String,
    icon: Image? = null,
    active: Boolean = false,
  ) {
    _items += NavigationItem.Link(
      name = name,
      href = href,
      icon = icon,
      active = active,
    )
  }

  /**
   * Adds a dropdown item.
   */
  fun dropdown(
    name: String,
    href: String,
    icon: Image? = null,
    active: Boolean = false,
    items: NavigationDropdownItemsBuilder.() -> Unit,
  ) {
    _items += NavigationItem.Dropdown(
      name = name,
      href = href,
      icon = icon,
      active = active,
      items = NavigationDropdownItemsBuilder().apply(items).build(),
    )
  }
}

/**
 * Builder for dropdown children. Only simple links are allowed here.
 */
@TabwebDsl
class NavigationDropdownItemsBuilder internal constructor() {

  private val _items = mutableListOf<NavigationItem.Link>()

  /**
   * Builds the immutable dropdown item list.
   */
  fun build(): List<NavigationItem.Link> = _items.toList()

  /**
   * Adds a dropdown link item.
   */
  fun link(
    name: String,
    href: String,
    icon: Image? = null,
    active: Boolean = false,
  ) {
    _items += NavigationItem.Link(
      name = name,
      href = href,
      icon = icon,
      active = active,
    )
  }
}

/**
 * Builds a hierarchy of navigation items.
 */
fun navigationItems(block: NavigationItemsBuilder.() -> Unit): List<NavigationItem> =
  NavigationItemsBuilder().apply(block).build()
