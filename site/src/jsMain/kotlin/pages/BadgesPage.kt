package com.github.jangalinski.tabweb.site.pages

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb.Tabweb.badge
import com.github.jangalinski.tabweb.Tabweb.badges
import com.github.jangalinski.tabweb._foundation.Link
import com.github.jangalinski.tabweb._foundation.Url
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.compose.KH1
import com.github.jangalinski.tabweb._foundation.compose.KH2
import com.github.jangalinski.tabweb._foundation.compose.KH3
import com.github.jangalinski.tabweb._foundation.compose.KH4
import com.github.jangalinski.tabweb._foundation.compose.KH5
import com.github.jangalinski.tabweb._foundation.compose.KH6
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.css.GridWidth
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb.badge.BadgeIconPosition
import com.github.jangalinski.tabweb.badge.BadgeShape
import com.github.jangalinski.tabweb.badge.BadgeSize
import com.github.jangalinski.tabweb.badge.BadgeStyle
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

private data object BadgesPageLink : Link {
  override val href = Url(SiteRoutes.Badges)
  override val text = "Badges"
}

@InitRoute
fun initBadgesPage(ctx: InitRouteContext) {
  ctx.data.add(sitePageMeta("Badges", "Badges highlight statuses, counts, and categories with a compact Tabler label."))
  ctx.data.add(siteLayoutData(SiteRoutes.Badges))
}

@Page(routeOverride = SiteRoutes.Badges)
@Composable
fun BadgesPage() {
  TablerCards {
    card(title = "Basic", width = GridWidth.HALF) {
      badges {
        BackgroundColor.BASE.entries.forEach { color ->
          badge(text = color.displayName, color = color)
        }
      }
    }
    card(title = "Light", width = GridWidth.HALF) {
      badges {
        BackgroundColor.LIGHT.entries.forEach { color ->
          badge(text = color.displayName.removeSuffix(" Light"), color = color)
        }
      }
    }
    card(title = "Outline", width = GridWidth.HALF) {
      badges {
        BackgroundColor.BASE.entries.forEach { color ->
          badge(text = color.displayName, color = color, style = BadgeStyle.OUTLINE)
        }
      }
    }
    card(title = "With icons", width = GridWidth.HALF) {
      badges {
        BackgroundColor.BASE.entries.forEach { color ->
          badge(text = color.displayName, icon = TablerIcon.TI_STAR, color = color)
        }
      }
    }
    card(title = "Badge with link", width = GridWidth.HALF) {
      badges {
        badge(text = "Primary", color = BackgroundColor.SEMANTIC.PRIMARY, link = BadgesPageLink)
        badge(text = "Success", color = BackgroundColor.SEMANTIC.SUCCESS, link = BadgesPageLink)
        badge(text = "Pill", color = BackgroundColor.BASE.PURPLE, shape = BadgeShape.PILL, link = BadgesPageLink)
        badge(text = "Icon", icon = TablerIcon.TI_STAR, color = BackgroundColor.BASE.YELLOW, link = BadgesPageLink)
        badge(text = "Small", size = BadgeSize.S, link = BadgesPageLink)
        badge(text = "Large", size = BadgeSize.L, link = BadgesPageLink)
      }
    }
    card(title = "In headings", width = GridWidth.HALF) {
      KH1 { KText("Example heading "); badge("New") }
      KH2 { KText("Example heading "); badge("New") }
      KH3 { KText("Example heading "); badge("New") }
      KH4 { KText("Example heading "); badge("New") }
      KH5 { KText("Example heading "); badge("New") }
      KH6 { KText("Example heading "); badge("New") }
    }
    card(title = "Sizes", width = GridWidth.HALF) {
      KDiv(modifier = Modifier.classNames("space-y")) {
        BadgeSize.entries.forEach { size ->
          badges {
            badge(text = "Default", size = size)
            badge(text = "Left icon", icon = TablerIcon.TI_CHECK, size = size)
            badge(
              text = "Right icon",
              icon = TablerIcon.TI_ARROW_RIGHT,
              iconPosition = BadgeIconPosition.RIGHT,
              size = size,
            )
            badge(icon = TablerIcon.TI_STAR, size = size)
          }
        }
      }
    }
  }
}
