package com.github.jangalinski.tabweb.navbar

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb.Tabweb.HOME
import com.github.jangalinski.tabweb._foundation.TabwebComponent
import com.github.jangalinski.tabweb._foundation.compose.KAnchor
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.compose.KImg
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.css.plus
import com.github.jangalinski.tabweb._foundation.Url
import com.github.jangalinski.tabweb.navbar.TablerNavbarCss.NAVBAR_BRAND
import com.github.jangalinski.tabweb.navbar.TablerNavbarCss.NAVBAR_BRAND_AUTODARK
import com.github.jangalinski.tabweb._foundation.css.PE_0
import com.github.jangalinski.tabweb._foundation.css.PE_MD_3
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.attr
import com.varabyte.kobweb.compose.ui.modifiers.classNames


/** Renders the brand area used by a Tabler navbar. */
data object TablerBrand {
  sealed interface Brand : TabwebComponent {
    val image: Url
    val href: Url get() = HOME

    /** A compact logo accompanied by a textual caption. */
    data class Logo(override val image: Url, val caption: String = "Tabler") : Brand {
      @Composable
      override fun invoke(modifier: Modifier) {
        KAnchor(
          href = href.get(),
          modifier = Modifier.classNames("text-reset", "text-decoration-none").attr("aria-label", caption),
        ) {
          KImg(src = image.get(), alt = "", modifier = Modifier.classNames("navbar-brand-image"))
          KText(caption)
        }
      }
    }

    /** A wordmark image without additional text. */
    data class Wordmark(override val image: Url) : Brand {
      @Composable
      override fun invoke(modifier: Modifier) {
        KAnchor(
          href = href.get(),
          modifier = Modifier.classNames("text-reset", "text-decoration-none").attr("aria-label", "Home"),
        ) {
          KImg(src = image.get(), alt = "", modifier = Modifier.classNames("navbar-brand-image"))
        }
      }
    }
  }

  @Composable
  operator fun invoke(brand: Brand) {
    KDiv(NAVBAR_BRAND + NAVBAR_BRAND_AUTODARK + PE_0 + PE_MD_3) {
      brand()
    }
  }
}
//internal fun TablerNavigation.HeaderNavigation.renderNavbar(includeBrand: Boolean = true) {
//  Div(attrs = modifier.toAttrs()) {
//    Header(attrs = { attr("class", ClassNames.navbar) }) {
//      Div(attrs = { attr("class", ClassNames.containerXl) }) {
//        Button(attrs = {
//          attr("class", ClassNames.navbarToggler)
//          attr("type", "button")
//          attr("data-bs-toggle", "collapse")
//          attr("data-bs-target", "#$NAVBAR_MENU_ID")
//          attr("aria-controls", NAVBAR_MENU_ID)
//          attr("aria-expanded", "false")
//          attr("aria-label", "Toggle primary navigation")
//        }) {
//          Span(attrs = { attr("class", ClassNames.navbarTogglerIcon) })
//        }
//        if (includeBrand) {
//          renderBrand(
//            brandClass = ClassNames.navbarBrand,
//            title = title,
//            caption = caption,
//            logo = logo,
//            href = href,
//          )
//        }
//        Div(attrs = { attr("class", "${ClassNames.navbarNav} ${ClassNames.msAuto}") }) {
//          content()
//        }
//      }
//    }
//    Div(attrs = { attr("class", ClassNames.navbarExpandMd) }) {
//      Div(attrs = {
//        attr("class", ClassNames.navbarCollapse)
//        attr("id", NAVBAR_MENU_ID)
//      }) {
//        Div(attrs = { attr("class", ClassNames.navbar) }) {
//          Div(attrs = { attr("class", ClassNames.containerXl) }) {
//            Nav(attrs = { attr("aria-label", "Primary") }) {
//              Ul(attrs = { attr("class", ClassNames.navbarNavPrimary) }) {
//                renderNavItems(items)
//              }
//            }
//          }
//        }
//      }
//    }
//  }
//}
