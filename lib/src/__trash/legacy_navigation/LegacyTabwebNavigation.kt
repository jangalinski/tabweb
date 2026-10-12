@file:Suppress("DEPRECATION")

package com.github.jangalinski.tabweb.__trash.legacy_navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.varabyte.kobweb.compose.css.TextTransform
import com.varabyte.kobweb.compose.css.textTransform
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.AppGlobals
import com.varabyte.kobweb.navigation.Anchor
import com.varabyte.kobweb.navigation.BasePath
import com.varabyte.kobweb.navigation.remove
import com.github.jangalinski.tabweb.image.renderImage
import com.github.jangalinski.tabweb._foundation.css.ClassNames
import com.github.jangalinski.tabweb._foundation.css.ClassNames.modifier
import com.github.jangalinski.tabweb._foundation.Image
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Aside
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Header
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Nav
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Ul
import kotlinx.browser.document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.events.Event

/**
 * Sealed navigation specification for one Tabweb page-shell navigation region.
 *
 * A [HeaderNavigation] and a [SidebarNavigation] can be supplied together via
 * [com.github.jangalinski.tabweb._foundation.TabwebLayoutData] for the
 * standard shell. Supplying one value through the legacy `navigation` slot is
 * still useful for condensed layouts.
 */
@Deprecated(
  message = "Use navbar.TabwebNavbarData for new navbar work. This legacy model remains for sidebar compatibility.",
)
sealed interface TabwebNavigation {

  /**
   * Modifier applied to the navigation component container.
   */
  val modifier: Modifier

  /**
   * No navigation block.
   */
  data object None : TabwebNavigation {
    override val modifier: Modifier = Modifier
  }

  /**
 * Header-style navigation rendered in the preview's `BEGIN NAVBAR` region.
   */
  data class HeaderNavigation(
    override val modifier: Modifier = Modifier,
    val title: String = AppGlobals["title"] ?: throw IllegalArgumentException("Title is required for header navigation"),
    val caption: String? = null,
    val logo: Image = Image.None,
    val href: String? = null,
    val items: List<NavigationItem> = emptyList(),
    val content: @Composable () -> Unit = {},
  ) : TabwebNavigation

  /**
 * Sidebar-style navigation rendered in the preview's `BEGIN SIDEBAR` region.
   */
  data class SidebarNavigation(
    override val modifier: Modifier = Modifier,
    val title: String,
    val caption: String? = null,
    val logo: Image = Image.None,
    val href: String? = null,
    val items: List<NavigationItem> = emptyList(),
    val content: @Composable () -> Unit = {},
  ) : TabwebNavigation
}

private const val SIDEBAR_MENU_ID = "sidebar-menu"
private const val NAVBAR_MENU_ID = "navbar-menu"
private const val CLOSE_DROPDOWNS_ON_CLICK =
  "document.querySelectorAll('.dropdown-menu.show').forEach(function(menu){menu.classList.remove('show');});"

/**
 * Renders the selected navigation block.
 */
@Composable
internal fun TabwebNavigation.render() {
  when (this) {
    TabwebNavigation.None -> Unit
    is TabwebNavigation.HeaderNavigation -> renderNavbar()
    is TabwebNavigation.SidebarNavigation -> renderSidebar()
  }
}

@Composable
internal fun TabwebNavigation.HeaderNavigation.renderNavbar(includeBrand: Boolean = true) {
  Div(attrs = modifier.toAttrs()) {
    Header(attrs = { attr("class", ClassNames.navbar) }) {
      Div(attrs = { attr("class", ClassNames.containerXl) }) {
        Button(attrs = {
          attr("class", ClassNames.navbarToggler)
          attr("type", "button")
          attr("data-bs-toggle", "collapse")
          attr("data-bs-target", "#$NAVBAR_MENU_ID")
          attr("aria-controls", NAVBAR_MENU_ID)
          attr("aria-expanded", "false")
          attr("aria-label", "Toggle primary navigation")
        }) {
          Span(attrs = { attr("class", ClassNames.navbarTogglerIcon) })
        }
        if (includeBrand) {
          renderBrand(
            brandClass = ClassNames.navbarBrand,
            title = title,
            caption = caption,
            logo = logo,
            href = href,
          )
        }
        Div(attrs = { attr("class", "${ClassNames.navbarNav} ${ClassNames.msAuto}") }) {
          content()
        }
      }
    }
    Div(attrs = { attr("class", ClassNames.navbarExpandMd) }) {
      Div(attrs = {
        attr("class", ClassNames.navbarCollapse)
        attr("id", NAVBAR_MENU_ID)
      }) {
        Div(attrs = { attr("class", ClassNames.navbar) }) {
          Div(attrs = { attr("class", ClassNames.containerXl) }) {
            Nav(attrs = { attr("aria-label", "Primary") }) {
              Ul(attrs = { attr("class", ClassNames.navbarNavPrimary) }) {
                renderNavItems(items)
              }
            }
          }
        }
      }
    }
  }
}

@Composable
internal fun TabwebNavigation.SidebarNavigation.renderSidebar() {
  Div(attrs = modifier.toAttrs()) {
    Aside(attrs = {
      attr("class", ClassNames.navbarVertical)
      attr("data-bs-theme", "dark")
    }) {
      Div(attrs = ClassNames.containerFluid.modifier().toAttrs()) {
        Button(
          attrs = {
            attr("class", ClassNames.navbarToggler)
            attr("type", "button")
            attr("data-bs-toggle", "collapse")
            attr("data-bs-target", "#$SIDEBAR_MENU_ID")
            attr("aria-controls", SIDEBAR_MENU_ID)
            attr("aria-expanded", "false")
            attr("aria-label", "Toggle navigation")
          },
        ) {
          Span(attrs = { attr("class", ClassNames.navbarTogglerIcon) })
        }

        renderBrand(
          brandClass = ClassNames.navbarBrandSidebar,
          title = title,
          caption = caption,
          logo = logo,
          href = href,
        )

        Nav(attrs = {
          attr("class", ClassNames.navbarCollapse)
          attr("id", SIDEBAR_MENU_ID)
          attr("aria-label", "Sidebar")
        }) {
          Ul(attrs = { attr("class", ClassNames.navbarNavSidebar) }) {
            renderNavItems(items)
            content()
          }
        }
      }
    }
  }
}

@Composable
private fun renderBrand(
  brandClass: String,
  title: String,
  caption: String?,
  logo: Image,
  href: String?,
) {
  if (href.isNullOrBlank()) {
    A(attrs = {
      attr(
        "class",
        "$brandClass ${ClassNames.navLink} ${ClassNames.textReset} ${ClassNames.textDecorationNone}",
      )
    }) {
      renderBrandContent(title, caption, logo)
    }
  } else {
    Anchor(
      href = BasePath.remove(href),
      attrs = {
        attr(
          "class",
          "$brandClass ${ClassNames.navLink} ${ClassNames.textReset} ${ClassNames.textDecorationNone}",
        )
      },
    ) {
      renderBrandContent(title, caption, logo)
    }
  }
}

@Composable
private fun renderBrandContent(
  title: String,
  caption: String?,
  logo: Image,
) {
  Div(attrs = {
    attr("class", "${ClassNames.dFlex} ${ClassNames.alignItemsCenter}")
  }) {
    renderImage(logo, defaultAlt = title, className = ClassNames.navbarBrandImage)
    Div(attrs = { attr("class", ClassNames.dFlexColumn) }) {
      Span(
        attrs = {
          style {
            textTransform(TextTransform.Lowercase)
          }
        }
      ) {
        Text(title)
      }
      caption?.let {
        Span(attrs = { attr("class", ClassNames.smallTextSecondary) }) {
          Text(it)
        }
      }
    }
  }
}

@Composable
private fun renderNavItems(items: List<NavigationItem>) {
  items.forEach { item ->
    when (item) {
      is NavigationItem.Link -> renderNavLink(item)
      is NavigationItem.Dropdown -> renderNavDropdown(item)
    }
  }
}

/**
 * Builds the navigation-item hierarchy used by a Tabweb shell.
 *
 * The resulting items can be rendered in either the preview's
 * `BEGIN NAVBAR` or `BEGIN SIDEBAR` region through [TabwebNavigation]. This
 * keeps navigation data independent from the Kobweb layout slot that displays
 * it.
 *
 * @param block declarations of links and dropdowns in navigation order.
 */
fun navItems(block: NavigationItemsBuilder.() -> Unit) =
  navigationItems(block)

@Composable
private fun renderNavLink(item: NavigationItem.Link) {
  val linkClass = if (item.active) {
    "${ClassNames.navLink} ${ClassNames.navLinkActive}"
  } else {
    ClassNames.navLink
  }
  Li(attrs = {
    attr("class", if (item.active) "${ClassNames.navItem} ${ClassNames.navItemActive}" else ClassNames.navItem)
  }) {
    Anchor(
      href = BasePath.remove(item.href),
      attrs = {
        attr("class", linkClass)
        attr("onclick", CLOSE_DROPDOWNS_ON_CLICK)
        if (item.active) {
          attr("aria-current", "page")
        }
      },
    ) {
      renderImage(item.icon, defaultAlt = item.name, className = ClassNames.navItemIcon)
      Span(attrs = { attr("class", ClassNames.navLinkTitle) }) {
        Text(item.name)
      }
    }
  }
}

@Composable
private fun renderNavDropdown(item: NavigationItem.Dropdown) {
  val dropdownId = dropdownId(item.name)
  DisposableEffect(dropdownId) {
    val listener = fun(event: Event) {
      val root = document.getElementById(dropdownId) ?: return
      val target = event.target as? Node ?: return
      if (root.contains(target)) return
      root.querySelector(".dropdown-menu")?.classList?.remove("show")
    }

    document.addEventListener("click", listener)
    onDispose {
      document.removeEventListener("click", listener)
    }
  }

  Li(attrs = {
    attr(
      "class",
      if (item.active) {
        "${ClassNames.navItemDropdown} ${ClassNames.positionRelative} ${ClassNames.navItemActive}"
      } else {
        "${ClassNames.navItemDropdown} ${ClassNames.positionRelative}"
      },
    )
    attr("id", dropdownId)
  }) {
    Div(attrs = {
      attr("class", "${ClassNames.dFlex} ${ClassNames.alignItemsCenter}")
    }) {
      Anchor(
        href = BasePath.remove(item.href),
        attrs = {
          attr(
            "class",
            if (item.active) {
              "${ClassNames.navLink} ${ClassNames.navLinkActive}"
            } else {
              ClassNames.navLink
            },
          )
          attr("onclick", CLOSE_DROPDOWNS_ON_CLICK)
          if (item.active) {
            attr("aria-current", "page")
          }
        },
      ) {
        renderImage(item.icon, defaultAlt = item.name, className = ClassNames.navItemIcon)
        Span(attrs = { attr("class", ClassNames.navLinkTitle) }) {
          Text(item.name)
        }
      }
      Button(attrs = {
        attr("type", "button")
        attr(
          "class",
          if (item.active) {
            "${ClassNames.navLinkDropdownToggleButton} ${ClassNames.navLinkActive}"
          } else {
            ClassNames.navLinkDropdownToggleButton
          },
        )
        attr("aria-expanded", "false")
        attr("aria-label", "Open ${item.name} menu")
        attr(
          "onclick",
          "const root=document.getElementById('$dropdownId'); const menu=root && root.querySelector('.dropdown-menu'); if(menu){menu.classList.toggle('show');}",
        )
      }) {
      }
    }

    Div(attrs = {
      attr(
        "class",
        buildString {
          append(ClassNames.dropdownMenu)
          append(' ')
          append(ClassNames.dropdownMenuArrow)
          append(' ')
          append(ClassNames.dropdownMenuBelow)
        },
      )
    }) {
      item.items.forEach { dropdownItem ->
        Anchor(
          href = BasePath.remove(dropdownItem.href),
          attrs = {
            attr(
              "class",
              if (dropdownItem.active) {
                "${ClassNames.dropdownItem} ${ClassNames.dropdownItemActive}"
              } else {
                ClassNames.dropdownItem
              },
            )
            attr("onclick", CLOSE_DROPDOWNS_ON_CLICK)
            if (dropdownItem.active) {
              attr("aria-current", "page")
            }
          },
        ) {
          renderImage(dropdownItem.icon, defaultAlt = dropdownItem.name, className = ClassNames.dropdownItemIcon)
          Text(dropdownItem.name)
        }
      }
    }
  }
}

private fun dropdownId(name: String): String =
  "nav-dropdown-" + name.lowercase().replace(Regex("[^a-z0-9]+"), "-").trim('-')
