package com.github.jangalinski.tabweb.site.pages.interfaces

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb.Tabweb.buttons
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.css.GridWidth
import com.github.jangalinski.tabweb.button.ButtonColor
import com.github.jangalinski.tabweb.button.ButtonIconPosition
import com.github.jangalinski.tabweb.button.ButtonShape
import com.github.jangalinski.tabweb.button.ButtonSize
import com.github.jangalinski.tabweb.button.ButtonStyle
import com.github.jangalinski.tabweb.card.TablerCards
import com.github.jangalinski.tabweb.icon.TablerIcon
import com.github.jangalinski.tabweb.site.SiteRoutes
import com.github.jangalinski.tabweb.site.siteLayoutData
import com.github.jangalinski.tabweb.site.sitePageMeta
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames

@InitRoute
fun initButtonsPage(ctx: InitRouteContext) {
  ctx.data.add(sitePageMeta("Buttons", "Buttons invoke user actions with Tabler colors, sizes, shapes, and icons."))
  ctx.data.add(siteLayoutData(SiteRoutes.Buttons))
}

@Page(routeOverride = SiteRoutes.Buttons)
@Composable
fun ButtonsPage() {
  TablerCards {
    card(title = "Basic", width = GridWidth.THIRD) {
      buttons {
        button(text = "Button")
        button(text = "Left icon", icon = TablerIcon.TI_STAR)
        button(text = "Right icon", icon = TablerIcon.TI_ARROW_RIGHT, iconPosition = ButtonIconPosition.RIGHT)
        button(icon = TablerIcon.TI_STAR, ariaLabel = "Icon only")
      }
    }
    card(title = "Shapes", width = GridWidth.THIRD) {
      buttons {
        button(text = "Default")
        button(text = "Pill", shape = ButtonShape.PILL)
        button(text = "Square", shape = ButtonShape.SQUARE)
        button(icon = TablerIcon.TI_STAR, ariaLabel = "Default icon")
        button(icon = TablerIcon.TI_STAR, ariaLabel = "Pill icon", shape = ButtonShape.PILL)
        button(icon = TablerIcon.TI_STAR, ariaLabel = "Square icon", shape = ButtonShape.SQUARE)
      }
    }
    card(title = "Loading", width = GridWidth.THIRD) {
      buttons {
        button(text = "Loading", loading = true)
        button(text = "Disabled", disabled = true)
      }
    }
    card(title = "Sizes", width = GridWidth.HALF) {
      KDiv(modifier = Modifier.classNames("space-y")) {
        ButtonSize.entries.forEach { size ->
          buttons {
            button(text = "Button", size = size)
            button(icon = TablerIcon.TI_STAR, ariaLabel = "${size.name.lowercase()} icon", size = size)
            button(text = "Icon", icon = TablerIcon.TI_STAR, size = size)
          }
        }
      }
    }
    card(title = "Standard", width = GridWidth.HALF) {
      buttons {
        ButtonColor.THEME.forEach { color ->
          button(text = color.name.lowercase().replaceFirstChar(Char::uppercase), color = color)
        }
      }
    }
    card(title = "Outline", width = GridWidth.FULL) {
      buttons {
        ButtonColor.THEME.forEach { color ->
          button(text = color.name.lowercase().replaceFirstChar(Char::uppercase), color = color, style = ButtonStyle.OUTLINE)
        }
      }
    }
    card(title = "Ghost", width = GridWidth.FULL) {
      buttons {
        ButtonColor.THEME.forEach { color ->
          button(text = color.name.lowercase().replaceFirstChar(Char::uppercase), color = color, style = ButtonStyle.GHOST)
        }
      }
    }
    card(title = "Pill", width = GridWidth.FULL) {
      buttons {
        ButtonColor.THEME.forEach { color ->
          button(text = color.name.lowercase().replaceFirstChar(Char::uppercase), color = color, shape = ButtonShape.PILL)
        }
      }
    }
    card(title = "Square", width = GridWidth.FULL) {
      buttons {
        ButtonColor.THEME.forEach { color ->
          button(text = color.name.lowercase().replaceFirstChar(Char::uppercase), color = color, shape = ButtonShape.SQUARE)
        }
      }
    }
    card(title = "Extra colors", width = GridWidth.FULL) {
      buttons {
        ButtonColor.PALETTE.forEach { color ->
          button(text = color.name.lowercase().replaceFirstChar(Char::uppercase), color = color)
        }
      }
    }
    card(title = "Social colors", width = GridWidth.FULL) {
      buttons {
        button(text = "Facebook", icon = TablerIcon.TI_BRAND_FACEBOOK, color = ButtonColor.FACEBOOK)
        button(text = "X", icon = TablerIcon.TI_BRAND_X, color = ButtonColor.X)
        button(text = "LinkedIn", icon = TablerIcon.TI_BRAND_LINKEDIN, color = ButtonColor.LINKEDIN)
        button(text = "GitHub", icon = TablerIcon.TI_BRAND_GITHUB, color = ButtonColor.GITHUB)
        button(text = "YouTube", icon = TablerIcon.TI_BRAND_YOUTUBE, color = ButtonColor.YOUTUBE)
      }
    }
    card(title = "Icon buttons", width = GridWidth.FULL) {
      buttons {
        button(icon = TablerIcon.TI_BRAND_FACEBOOK, ariaLabel = "Facebook", color = ButtonColor.FACEBOOK)
        button(icon = TablerIcon.TI_BRAND_TWITTER, ariaLabel = "Twitter", color = ButtonColor.TWITTER)
        button(icon = TablerIcon.TI_BRAND_X, ariaLabel = "X", color = ButtonColor.X)
        button(icon = TablerIcon.TI_BRAND_LINKEDIN, ariaLabel = "LinkedIn", color = ButtonColor.LINKEDIN)
        button(icon = TablerIcon.TI_BRAND_GOOGLE, ariaLabel = "Google", color = ButtonColor.GOOGLE)
        button(icon = TablerIcon.TI_BRAND_YOUTUBE, ariaLabel = "YouTube", color = ButtonColor.YOUTUBE)
        button(icon = TablerIcon.TI_BRAND_GITHUB, ariaLabel = "GitHub", color = ButtonColor.GITHUB)
        button(icon = TablerIcon.TI_BRAND_INSTAGRAM, ariaLabel = "Instagram", color = ButtonColor.INSTAGRAM)
      }
    }
  }
}
