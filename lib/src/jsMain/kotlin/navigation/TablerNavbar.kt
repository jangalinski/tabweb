package com.github.jangalinski.tabweb.navbar

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._app.LocalTablerAppState
import com.github.jangalinski.tabweb._foundation.compose.*
import com.github.jangalinski.tabweb._foundation.css.BaseCss.CONTAINER_XL
import com.github.jangalinski.tabweb._foundation.css.BaseCss.PRINT_NONE
import com.github.jangalinski.tabweb._foundation.css.plus
import com.github.jangalinski.tabweb.navbar.TablerBrand.Brand
import com.github.jangalinski.tabweb.navbar.TablerNavbarCss.NAVBAR
import com.github.jangalinski.tabweb.navbar.TablerNavbarCss.NAVBAR_EXPAND_MD
import com.github.jangalinski.tabweb.navigation.NavbarBehavior
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.attr
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.dataAttr

/**
 * Renders the preview-style Tabler navbar: a brand and action row followed by
 * a collapsible primary-navigation row.
 */
data object TablerNavbar {
  private const val menuId = "navbar-menu"

  /**
   * Navbar block for the Tabler page shell.
   *
   * This represents the preview's `<!-- BEGIN NAVBAR -->` region and renders
   * the primary link and dropdown navigation. It remains separate from the
   * sidebar and route content in the Kobweb layout.
   */
  @Composable
  operator fun invoke(
    brand: Brand,
    data: TablerNavbarData,
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
              .attr("aria-label", "Toggle primary navigation"),
          ) {
            KSpan(modifier = Modifier.classNames("navbar-toggler-icon")) {}
          }
          TablerBrand(brand)
          KDiv(modifier = Modifier.classNames("navbar-nav", "flex-row", "order-md-last")) {
            actions()
          }
        }
      }
      KDiv(NAVBAR_EXPAND_MD) {
        KDiv(modifier = Modifier.classNames("collapse", "navbar-collapse").attr("id", menuId)) {
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

    when (LocalTablerAppState.current.settings.navbarBehavior) {
      NavbarBehavior.STICKY_TOP -> KDiv(modifier = NavbarBehavior.CSS_STICKY_TOP) {
        renderNavbarRows()
      }

      else -> renderNavbarRows()
    }
  }

  @Composable
  private fun renderItem(item: TablerNavbarItem) {
    when (item) {
      is TablerNavbarItem.Link -> renderLink(item)
      is TablerNavbarItem.Section -> renderSection(item)
    }
  }

  @Composable
  private fun renderLink(item: TablerNavbarItem.Link) {
    KLi(
      modifier = Modifier.classNames("nav-item")
        .then(if (item.active) Modifier.classNames("active") else Modifier),
    ) {
      KAnchor(
        href = item.url.get(),
        modifier = Modifier.classNames("nav-link").then(
          if (item.active) Modifier.classNames("active").attr("aria-current", "page") else Modifier,
        ),
      ) {
        renderItemContent(item)
      }
    }
  }

  @Composable
  private fun renderSection(item: TablerNavbarItem.Section) {
    val active = item.hasActiveDescendant()
    KLi(
      modifier = Modifier.classNames("nav-item", "dropdown")
        .then(if (active) Modifier.classNames("active") else Modifier),
    ) {
      KAnchor(
        href = "#",
        modifier = Modifier.classNames("nav-link", "dropdown-toggle")
          .then(if (active) Modifier.classNames("active") else Modifier)
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
  private fun renderDropdownItems(items: List<TablerNavbarItem>, columns: Int) {
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
  private fun renderDropdownItem(item: TablerNavbarItem) {
    when (item) {
      is TablerNavbarItem.Link -> KLi {
        KAnchor(
          href = item.url.get(),
          modifier = Modifier.classNames("dropdown-item").then(
            if (item.active) Modifier.classNames("active").attr("aria-current", "page") else Modifier,
          ),
        ) {
          renderItemContent(item)
        }
      }

      is TablerNavbarItem.Section -> {
        val active = item.hasActiveDescendant()
        KLi(
          modifier = Modifier.classNames("dropend")
            .then(if (active) Modifier.classNames("active") else Modifier),
        ) {
          KAnchor(
            href = "#",
            modifier = Modifier.classNames("dropdown-item", "dropdown-toggle")
              .then(if (active) Modifier.classNames("active") else Modifier)
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

  private fun TablerNavbarItem.Section.hasActiveDescendant(): Boolean = items.any { item ->
    when (item) {
      is TablerNavbarItem.Link -> item.active
      is TablerNavbarItem.Section -> item.hasActiveDescendant()
    }
  }

  @Composable
  private fun renderItemContent(item: TablerNavbarItem) {
    item.icon?.invoke(Modifier.classNames("nav-link-icon"))
    KDiv(modifier = Modifier.classNames("d-flex", "flex-column")) {
      KSpan(modifier = Modifier.classNames("nav-link-title"), text = item.title)
      item.caption?.let { caption ->
        KSpan(modifier = Modifier.classNames("text-secondary", "small"), text = caption)
      }
    }
    item.badge?.let { badge ->
      KSpan(modifier = Modifier.classNames("badge", "bg-${badge.color}"), text = badge.label)
    }
  }
}
