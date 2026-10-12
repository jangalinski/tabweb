package com.github.jangalinski.tabweb.navigation

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._app.LocalTabwebAppState
import com.github.jangalinski.tabweb._foundation.compose.*
import com.github.jangalinski.tabweb._foundation.css.BaseCss.CONTAINER_XL
import com.github.jangalinski.tabweb._foundation.css.BaseCss.PRINT_NONE
import com.github.jangalinski.tabweb._foundation.css.plus
import com.github.jangalinski.tabweb.navigation.TabwebBrand.Brand
import com.github.jangalinski.tabweb.navigation.TabwebNavbarCss.NAVBAR
import com.github.jangalinski.tabweb.navigation.TabwebNavbarCss.NAVBAR_EXPAND_MD
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.ariaLabel
import com.varabyte.kobweb.compose.ui.modifiers.attr
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.dataAttr
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.title

/**
 * Renders the preview-style Tabweb navbar: a brand and action row followed by
 * a collapsible primary-navigation row.
 */
data object TabwebNavbar {
  private const val menuId = "navbar-menu"

  /**
   * Navbar block for the Tabweb page shell.
   *
   * This represents the preview's `<!-- BEGIN NAVBAR -->` region and renders
   * the primary link and dropdown navigation. It remains separate from the
   * sidebar and route content in the Kobweb layout.
   */
  @Composable
  operator fun invoke(
    brand: Brand,
    data: TabwebNavbarData,
    actions: @Composable () -> Unit = {},
  ) {

    @Composable
    fun renderNavbarRows() {

      KHeader(modifier = NAVBAR + NAVBAR_EXPAND_MD + PRINT_NONE) {
        KDiv(CONTAINER_XL) {

          KButton(
            modifier = Modifier.classNames("navbar-toggler")
              .attr("type", "button")
              .dataAttr("bs-toggle", "collapse")
              .dataAttr("bs-target", "#$menuId")
              .attr("aria-controls", menuId)
              .attr("aria-expanded", "false")
              .ariaLabel("Toggle primary navigation"),
          ) {
            KSpan(modifier = Modifier.classNames("navbar-toggler-icon")) {}
          }
          TabwebBrand(brand)
          KDiv(modifier = Modifier.classNames("navbar-nav", "flex-row", "order-md-last")) {
            actions()
          }
        }
      }
      KDiv(NAVBAR_EXPAND_MD) {
        KDiv(modifier = Modifier.classNames("collapse", "navbar-collapse").id(menuId)) {
          KDiv(NAVBAR) {
            KDiv(CONTAINER_XL) {
              KNav(label = "Primary") {
                KUl(modifier = Modifier.classNames("navbar-nav")) {
                  data.items.forEach { item ->
                    renderItem(item)
                  }
                }
              }
            }
          }
        }
      }
    }

    when (LocalTabwebAppState.current.settings.navbarBehavior) {
      NavbarBehavior.STICKY_TOP -> KDiv(modifier = NavbarBehavior.CSS_STICKY_TOP) {
        renderNavbarRows()
      }

      else -> renderNavbarRows()
    }
  }

  @Composable
  private fun renderItem(item: TabwebNavbarItem) {
    when (item) {
      is TabwebNavbarItem.Link -> renderLink(item)
      is TabwebNavbarItem.Section -> renderSection(item)
    }
  }

  @Composable
  private fun renderLink(item: TabwebNavbarItem.Link) {
    KLi(
      modifier = Modifier.classNames("nav-item")
        .then(if (item.active) Modifier.classNames("active") else Modifier),
    ) {
      KAnchor(
        href = item.url.get(),
        modifier = Modifier.classNames("nav-link").then(
          if (item.active) Modifier.classNames("active").attr("aria-current", "page") else Modifier,
        ).then(item.caption?.let { Modifier.title(it) } ?: Modifier),
      ) {
        renderItemContent(item)
      }
    }
  }

  @Composable
  private fun renderSection(item: TabwebNavbarItem.Section) {
    val active = item.active || item.hasActiveDescendant()
    KLi(
      modifier = Modifier.classNames("nav-item", "dropdown")
        .then(if (active) Modifier.classNames("active") else Modifier),
    ) {
      KAnchor(
        href = "#",
        modifier = Modifier.classNames("nav-link", "dropdown-toggle")
          .then(if (active) Modifier.classNames("active") else Modifier)
          .then(item.caption?.let { Modifier.title(it) } ?: Modifier)
          .dataAttr("bs-toggle", "dropdown")
          .dataAttr("bs-auto-close", "outside")
          .attr("role", "button")
          .attr("aria-expanded", "false"),
      ) {
        renderItemContent(item)
      }
      KUl(modifier = Modifier.classNames("dropdown-menu", "list-unstyled")) {
        renderDropdownItems(item.items, item.columns)
      }
    }
  }

  @Composable
  private fun renderDropdownItems(items: List<TabwebNavbarItem>, columns: Int) {
    if (columns == 1) {
      items.forEach { item ->
        renderDropdownItem(item)
      }
      return
    }

    val columnSize = (items.size + columns - 1) / columns
    KLi(modifier = Modifier.classNames("dropdown-menu-columns", "list-unstyled")) {
      items.chunked(columnSize).forEach { column ->
        KUl(modifier = Modifier.classNames("dropdown-menu-column", "list-unstyled")) {
          column.forEach { item ->
            renderDropdownItem(item)
          }
        }
      }
    }
  }

  @Composable
  private fun renderDropdownItem(item: TabwebNavbarItem) {
    when (item) {
      is TabwebNavbarItem.Link -> KLi {
        KAnchor(
          href = item.url.get(),
          modifier = Modifier.classNames("dropdown-item").then(
            if (item.active) Modifier.classNames("active").attr("aria-current", "page") else Modifier,
          ).then(item.caption?.let { Modifier.title(it) } ?: Modifier),
        ) {
          renderItemContent(item)
        }
      }

      is TabwebNavbarItem.Section -> {
        val active = item.active || item.hasActiveDescendant()
        KLi(
          modifier = Modifier.classNames("dropend")
            .then(if (active) Modifier.classNames("active") else Modifier),
        ) {
          KAnchor(
            href = "#",
            modifier = Modifier.classNames("dropdown-item", "dropdown-toggle")
              .then(if (active) Modifier.classNames("active") else Modifier)
              .then(item.caption?.let { Modifier.title(it) } ?: Modifier)
              .dataAttr("bs-toggle", "dropdown")
              .dataAttr("bs-auto-close", "false")
              .attr("role", "button")
              .attr("aria-expanded", "false"),
          ) {
            renderItemContent(item)
          }
          KUl(modifier = Modifier.classNames("dropdown-menu", "list-unstyled")) {
            renderDropdownItems(item.items, item.columns)
          }
        }
      }
    }
  }

  private fun TabwebNavbarItem.Section.hasActiveDescendant(): Boolean = items.any { item ->
    when (item) {
      is TabwebNavbarItem.Link -> item.active
      is TabwebNavbarItem.Section -> item.hasActiveDescendant()
    }
  }

  @Composable
  private fun renderItemContent(item: TabwebNavbarItem) {
    item.icon?.invoke(Modifier.classNames("nav-link-icon"))
    KDiv(modifier = Modifier.classNames("d-flex", "flex-column")) {
      KSpan(modifier = Modifier.classNames("nav-link-title"), text = item.title)
    }
    item.badge?.let { badge ->
      KSpan(modifier = Modifier.classNames("badge", "bg-${badge.color}"), text = badge.label)
    }
  }
}
