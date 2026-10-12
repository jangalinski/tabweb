package com.github.jangalinski.tabweb.avatar

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb.Tabweb.publicResourcePath
import com.github.jangalinski.tabweb.avatar.TabwebAvatarContent
import com.github.jangalinski.tabweb.avatar.TabwebAvatarData
import com.github.jangalinski.tabweb.avatar.TabwebAvatarListData
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.compose.KImg
import com.github.jangalinski.tabweb._foundation.compose.KSpan
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.css.ClassNames
import com.github.jangalinski.tabweb.image.svgDataUri
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.attr
import com.varabyte.kobweb.compose.ui.modifiers.classNames

/** Renders a single Tabweb avatar from pure [TabwebAvatarData] configuration. */
@Composable
fun TabwebAvatar(data: TabwebAvatarData) {
  val content = data.content
  val avatarModifier = Modifier.classNames(ClassNames.avatar)
    .then(data.color?.let { Modifier.classNames(it.backgroundClassName, it.foregroundClassName) } ?: Modifier)
    .then(data.size.className?.let(Modifier::classNames) ?: Modifier)
    .then(data.shape.className?.let(Modifier::classNames) ?: Modifier)
    .then(data.ariaLabel?.let { Modifier.attr("aria-label", it) } ?: Modifier)
    .then(if (content is TabwebAvatarContent.ImageResource) Modifier.attr("style", "background-color: var(--tblr-bg-surface)") else Modifier)
  KSpan(avatarModifier) {
    when (content) {
      is TabwebAvatarContent.ImageResource -> KImg(
        src = publicResourcePath(content.resource),
        alt = content.altText.orEmpty(),
        modifier = Modifier.attr("style", "display: block; width: 100%; height: 100%; object-fit: cover; border-radius: inherit"),
      )
      is TabwebAvatarContent.Icon -> KImg(
        src = svgDataUri(content.svg),
        alt = content.altText.orEmpty(),
        modifier = Modifier.classNames(ClassNames.icon),
      )
      is TabwebAvatarContent.Initials -> KText(content.value)
    }
    data.status?.let { status ->
      KSpan(
        Modifier.classNames(ClassNames.badge, status.color.className)
          .then(status.label?.let {
            Modifier.attr("style", "display: flex; align-items: center; justify-content: center; width: 14px; height: 14px; min-width: 0; padding: 0; font-size: 8px; line-height: 1; border-radius: 50%")
          } ?: Modifier),
      ) {
        status.label?.let { KText(it) }
      }
    }
  }
}

/** Renders Tabweb's `avatar-list`, optionally using its overlapping stacked variant. */
@Composable
fun TabwebAvatarList(data: TabwebAvatarListData) {
  KDiv(Modifier.classNames(ClassNames.avatarList).then(
    if (data.stacked) Modifier.classNames(ClassNames.avatarListStacked) else Modifier,
  )) {
    data.avatars.forEach { avatar ->
      TabwebAvatar(avatar)
    }
  }
}
