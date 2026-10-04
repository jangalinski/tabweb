package com.github.jangalinski.tabweb.site.pages

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.css.GridWidth
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb.avatar.AvatarStyle
import com.github.jangalinski.tabweb.avatar.Avatar
import com.github.jangalinski.tabweb.card.TablerCards
import com.github.jangalinski.tabweb.site.SiteRoutes
import com.github.jangalinski.tabweb.site.siteLayoutData
import com.github.jangalinski.tabweb.site.sitePageMeta
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import org.jetbrains.compose.web.dom.Text

@InitRoute
fun initColorsPage(ctx: InitRouteContext) {
  ctx.data.add(sitePageMeta("Colors", "The full color palette, with hex values, and a gradient builder."))
  ctx.data.add(siteLayoutData(SiteRoutes.Colors))
}


@Composable
fun color(names: List<BackgroundColor>) {
  KDiv(Modifier.classNames("row", "g-3")) {

    names.forEach { name ->
      KDiv(Modifier.classNames("col-12")) {
        KDiv(Modifier.classNames("row", "align-items-center")) {
          KDiv(Modifier.classNames("col-auto")) {

            if (name is BackgroundColor.SOCIAL) {
              Avatar(
                content = name.icon,
                color = name,
                style = AvatarStyle.SQUARE,
              )(name.textFg)
            } else {
              Avatar(
                content = name.initials,
                color = name,
                style = AvatarStyle.SQUARE,
              )(name.textFg)
            }
          }
          KDiv(Modifier.classNames("col")) {
            Text(name.displayName)
            KDiv(Modifier.classNames("text-muted")) {
              Text("#${name.value}")
            }
          }
        }
      }
    }
  }
}

@Page(routeOverride = SiteRoutes.Colors)
@Composable
fun Colors() {
  TablerCards {
    card(title = "Colors", width = GridWidth.QUARTER) {
      Text("All colors, with hex values, and a gradient builder.")
      color(BackgroundColor.BASE.entries.toList())
    }
    card(title = "Light colors", width = GridWidth.QUARTER) {
      Text("A tinted, low-contrast version of each color.")
      color(BackgroundColor.LIGHT.entries.toList())
    }
    card(title = "Gray colors", width = GridWidth.QUARTER) {
      Text("The neutral gray scale used for text, borders, and backgrounds.")
      color(BackgroundColor.GRAY.entries.toList())

    }
    card(title = "Social colors", width = GridWidth.QUARTER) {
      Text("Brand colors for social networks.")
      color(BackgroundColor.SOCIAL.entries.toList())
    }
    card(title = "Semantic colors", width = GridWidth.QUARTER) {
      Text("Semantic colors.")
      color(BackgroundColor.SEMANTIC.entries.toList())
    }
  }

}
