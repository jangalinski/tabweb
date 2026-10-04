package com.github.jangalinski.tabweb.site.pages

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb.Tabweb.avatars
import com.github.jangalinski.tabweb.Tabweb.cardDeck
import com.github.jangalinski.tabweb.Tabweb.markdown
import com.github.jangalinski.tabweb._foundation.Image
import com.github.jangalinski.tabweb._foundation.Initials
import com.github.jangalinski.tabweb._foundation.css.GridWidth
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor.BASE.WHITE
import com.github.jangalinski.tabweb.avatar.Avatar
import com.github.jangalinski.tabweb.avatar.AvatarList
import com.github.jangalinski.tabweb.avatar.AvatarListSize
import com.github.jangalinski.tabweb.icon.TablerIcon
import com.github.jangalinski.tabweb.site.SiteRoutes
import com.github.jangalinski.tabweb.site.siteLayoutData
import com.github.jangalinski.tabweb.site.sitePageMeta
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext

@InitRoute
fun initAvatarsPage(ctx: InitRouteContext) {
  ctx.data.add(sitePageMeta("Avatars", "Avatars display a photo, icon, or initials to represent a person, brand, or status."))
  ctx.data.add(siteLayoutData(SiteRoutes.Avatars))
}

@Page(routeOverride = SiteRoutes.Avatars)
@Composable
fun AvatarsPage() {
  cardDeck {
    card(width = GridWidth.THIRD) {
      header(title = "Default Avatar")
      body {
        markdown("The base `.avatar` element — a placeholder box for a photo, icon, or initials.")
        AvatarList(
          avatars = arrayOf(Avatar(content = TablerIcon.TI_USER), Avatar(content = Initials("AB")))
        )()
      }
    }

    card(width = GridWidth.THIRD) {
      header(title = "Avatar with icon")
      body {
        markdown("Put an icon inside the `avatar` instead of a photo.")
        AvatarList(
          stacked = false,
          size = AvatarListSize.DEFAULT,
          Avatar(content = TablerIcon.TI_USER, color = WHITE),
          Avatar(content = TablerIcon.TI_SETTINGS, color = WHITE),
          Avatar(content = TablerIcon.TI_CAR, color = WHITE),
          Avatar(content = TablerIcon.TI_BALLOON, color = WHITE),
          Avatar(content = TablerIcon.TI_USERS, color = WHITE),
          Avatar(content = TablerIcon.TI_USERS_GROUP, color = WHITE),
          Avatar(content = TablerIcon.TI_APPS, color = WHITE),
          Avatar(content = TablerIcon.TI_GHOST, color = WHITE),
        )()
      }
    }

    card(width = GridWidth.THIRD) {
      header(title = "Avatar icon colors")
      body {
        markdown("Combine an icon avatar with any theme color.")
        AvatarList(
          stacked = false,
          size = AvatarListSize.DEFAULT,
          avatars = BackgroundColor.LIGHT.entries.map {
            Avatar(content = TablerIcon.TI_USER, color = it)
          }
        )()
      }
    }
  }

  cardDeck {
    card(width = GridWidth.THIRD) {
      header(title = "Simple avatar")
      body {
        markdown("Show a photo by setting it as the `background-image` of the avatar.")
        avatars {
          avatar(content = Image("https://randomuser.me/api/portraits/women/91.jpg"))
          avatar(content = Image("https://randomuser.me/api/portraits/men/11.jpg"))
          avatar(content = Image("https://randomuser.me/api/portraits/women/68.jpg"))
          avatar(content = Image("https://randomuser.me/api/portraits/men/20.jpg"))
          avatar(content = Image("https://randomuser.me/api/portraits/women/12.jpg"))
          avatar(content = Image("https://randomuser.me/api/portraits/men/12.jpg"))
          avatar(content = Image("https://randomuser.me/api/portraits/women/16.jpg"))
          avatar(content = Image("https://randomuser.me/api/portraits/men/16.jpg"))
        }
      }
    }
  }
}
