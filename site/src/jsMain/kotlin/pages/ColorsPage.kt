package com.github.jangalinski.tabweb.site.pages

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb.Tabweb.cardDeck
import com.github.jangalinski.tabweb.Tabweb.cardRow
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.css.GridWidth
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb.avatar.Avatar
import com.github.jangalinski.tabweb.avatar.AvatarStyle
import com.github.jangalinski.tabweb.site.SiteRoutes
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.dom.Text


@Composable
fun color(names: List<BackgroundColor>) {
  KDiv(Modifier.classNames("row", "g-3")) {

    names.forEach { name ->
      KDiv(GridWidth.FULL.modifier()) {
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
  cardDeck {
    card(width = GridWidth.QUARTER) {
      body {
        title(text = "Colors")
        Text("All colors, with hex values, and a gradient builder.")
        color(BackgroundColor.BASE.entries.toList())
      }
    }

    card(width = GridWidth.QUARTER) {
      body {
        title(text = "Light colors")
        Text("A tinted, low-contrast version of each color.")
        color(BackgroundColor.LIGHT.entries.toList())
      }
    }

    card(width = GridWidth.QUARTER) {
      body {
        title(text = "Gray colors")
        Text("The neutral gray scale used for text, borders, and backgrounds.")
        color(BackgroundColor.GRAY.entries.toList())
      }
    }

    card(width = GridWidth.QUARTER) {
      body {
        title(text = "Social colors")

        Text("Brand colors for social networks.")

        color(BackgroundColor.SOCIAL.entries.toList())
      }
    }

    card(width = GridWidth.QUARTER) {
      body {
        title(text = "Semantic colors")
        Text("Semantic colors.")
        color(BackgroundColor.SEMANTIC.entries.toList())
      }
    }
  }
}
