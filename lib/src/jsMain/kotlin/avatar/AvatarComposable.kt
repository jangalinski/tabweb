package com.github.jangalinski.tabweb.avatar

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.Image
import com.github.jangalinski.tabweb._foundation.Initials
import com.github.jangalinski.tabweb._foundation.TabwebComposable
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb.icon.Icon
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Provides page-level composable entry points for the avatar concept.
 */
interface AvatarComposable : TabwebComposable{

  /**
   * Creates and renders an icon [Avatar] without requiring an intermediate
   * component instance at the call site.
   *
   * @param content the icon shown by the avatar.
   * @param size the avatar size.
   * @param color the background color applied behind the icon.
   * @param style the avatar shape and presentation style.
   * @param modifier additional attributes and styles applied to the avatar root.
   * @return `Unit` after the avatar has been emitted into the current composition.
   */
  @Composable
  fun avatar(
    content: Icon,
    size: AvatarSize = AvatarSize.DEFAULT,
    color: BackgroundColor = BackgroundColor.DEFAULT,
    style: AvatarStyle = AvatarStyle.DEFAULT,
    modifier: Modifier = Modifier,
  )

  /**
   * Creates and renders an initials [Avatar] without requiring an intermediate
   * component instance at the call site.
   *
   * @param content the initials shown by the avatar.
   * @param size the avatar size.
   * @param color the background color applied behind the initials.
   * @param style the avatar shape and presentation style.
   * @param modifier additional attributes and styles applied to the avatar root.
   * @return `Unit` after the avatar has been emitted into the current composition.
   */
  @Composable
  fun avatar(
    content: Initials,
    size: AvatarSize = AvatarSize.DEFAULT,
    color: BackgroundColor = BackgroundColor.DEFAULT,
    style: AvatarStyle = AvatarStyle.DEFAULT,
    modifier: Modifier = Modifier,
  )

  /**
   * Creates and renders an image [Avatar] without requiring an intermediate
   * component instance at the call site.
   *
   * @param content the resource image used as the avatar background.
   * @param size the avatar size.
   * @param style the avatar shape and presentation style.
   * @param modifier additional attributes and styles applied to the avatar root.
   * @return `Unit` after the avatar has been emitted into the current composition.
   */
  @Composable
  fun avatar(
    content: Image.Resource,
    size: AvatarSize = AvatarSize.DEFAULT,
    style: AvatarStyle = AvatarStyle.DEFAULT,
    modifier: Modifier = Modifier,
  )

  /**
   * Creates and renders an [AvatarList] from typed child declarations.
   *
   * @param stacked whether child avatars overlap in the Tabweb stacked-list layout.
   * @param size the size applied to every avatar in the list.
   * @param modifier additional attributes and styles applied to the list root.
   * @param content the DSL block that adds avatars to the list.
   * @return `Unit` after the list has been emitted into the current composition.
   */
  @Composable
  fun avatars(
    stacked: Boolean = false,
    size: AvatarListSize = AvatarListSize.DEFAULT,
    modifier: Modifier = Modifier,
    content: AvatarListScope.() -> Unit,
  )
}
