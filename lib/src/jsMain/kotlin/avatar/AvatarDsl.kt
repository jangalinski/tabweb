package com.github.jangalinski.tabweb.avatar

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.Image
import com.github.jangalinski.tabweb._foundation.Initials
import com.github.jangalinski.tabweb._foundation.TabwebComponentDsl
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb.icon.Icon
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * The avatar DSL implementation delegated through [com.github.jangalinski.tabweb.Tabweb].
 */
internal data object AvatarDsl : TabwebComponentDsl, AvatarComposable {

  @Composable
  override fun avatar(
    content: Icon,
    size: AvatarSize,
    color: BackgroundColor,
    style: AvatarStyle,
    modifier: Modifier,
  ) {
    Avatar(content = content, size = size, color = color, style = style)(modifier)
  }

  @Composable
  override fun avatar(
    content: Initials,
    size: AvatarSize,
    color: BackgroundColor,
    style: AvatarStyle,
    modifier: Modifier,
  ) {
    Avatar(content = content, size = size, color = color, style = style)(modifier)
  }

  @Composable
  override fun avatar(
    content: Image.Resource,
    size: AvatarSize,
    style: AvatarStyle,
    modifier: Modifier,
  ) {
    Avatar(content = content, size = size, style = style)(modifier)
  }

  @Composable
  override fun avatars(
    stacked: Boolean,
    size: AvatarListSize,
    modifier: Modifier,
    content: AvatarListScope.() -> Unit,
  ) {
    val scope = AvatarListScope().apply(content)
    AvatarList(stacked = stacked, size = size, avatars = scope.avatars)(modifier)
  }
}
