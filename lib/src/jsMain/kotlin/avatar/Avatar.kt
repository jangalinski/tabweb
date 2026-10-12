package com.github.jangalinski.tabweb.avatar

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.Image
import com.github.jangalinski.tabweb._foundation.Initials
import com.github.jangalinski.tabweb._foundation.Tabweb
import com.github.jangalinski.tabweb._foundation.TabwebComponent
import com.github.jangalinski.tabweb._foundation.compose.KSpan
import com.github.jangalinski.tabweb._foundation.css.plus
import com.github.jangalinski.tabweb._foundation.modifier
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb.icon.Icon
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * An avatar shows a user's picture, initials or an icon in a fixed-size
 * circle or square, with sizes, statuses and stacked lists.
 */
interface Avatar : TabwebComponent {

  companion object {
    /**
     * Creates an [Avatar] that renders an icon.
     *
     * @param content the icon shown by the avatar.
     * @param size the avatar size.
     * @param color the background color applied behind the icon.
     * @param style the avatar shape and presentation style.
     * @return an icon-backed [Avatar] component instance.
     */
    operator fun invoke(
      content: Icon,
      size: AvatarSize = AvatarSize.DEFAULT,
      color: BackgroundColor = BackgroundColor.DEFAULT,
      style: AvatarStyle = AvatarStyle.DEFAULT
    ) : Avatar = object : Avatar {
      override val content: AvatarContent = AvatarContent.Icon(content)
      override val size: AvatarSize = size
      override val color: BackgroundColor = color
      override val style: AvatarStyle = style
    }

    /**
     * Creates an [Avatar] that renders initials.
     *
     * @param content the initials shown by the avatar.
     * @param size the avatar size.
     * @param color the background color applied behind the initials.
     * @param style the avatar shape and presentation style.
     * @return an initials-backed [Avatar] component instance.
     */
    operator fun invoke(
      content: Initials,
      size: AvatarSize = AvatarSize.DEFAULT,
      color: BackgroundColor = BackgroundColor.DEFAULT,
      style: AvatarStyle = AvatarStyle.DEFAULT
    ) : Avatar = object : Avatar {
      override val content: AvatarContent = AvatarContent.Initials(content)
      override val size: AvatarSize = size
      override val color: BackgroundColor = color
      override val style: AvatarStyle = style
    }

    /**
     * Creates an [Avatar] that renders a resource image as its background.
     *
     * @param content the resource image used as the avatar background.
     * @param size the avatar size.
     * @param style the avatar shape and presentation style.
     * @return an image-backed [Avatar] component instance.
     */
    operator fun invoke(
      content: Image.Resource,
      size: AvatarSize = AvatarSize.DEFAULT,
      style: AvatarStyle = AvatarStyle.DEFAULT
    ) : Avatar = object : Avatar {
      override val content: AvatarContent = AvatarContent.Image(content)
      override val size: AvatarSize = size
      override val style: AvatarStyle = style
    }
  }

  /**
   * The typed image, icon, or initials rendered by this avatar.
   */
  val content: AvatarContent

  /**
   * The configured avatar size.
   */
  val size: AvatarSize get() = AvatarSize.DEFAULT

  /**
   * The background color used for icon and initials avatars.
   */
  val color: BackgroundColor get() = BackgroundColor.DEFAULT

  /**
   * The avatar shape and presentation style.
   */
  val style: AvatarStyle get() = AvatarStyle.DEFAULT

  @Composable
  override fun invoke(modifier: Modifier) {
    val modifiers = AvatarCss.avatar + size + style

    when (content) {
      is AvatarContent.Image -> {
        val backgroundImage = (content as AvatarContent.Image).content.backgroundImage.modifier
        KSpan(modifier = modifiers + backgroundImage + modifier)
      }

      is AvatarContent.Icon -> KSpan(modifier = modifiers + color + modifier) {
        (content as AvatarContent.Icon).content.invoke()
      }

      is AvatarContent.Initials -> KSpan(modifier = modifiers + color + modifier) {
        (content as AvatarContent.Initials).content.invoke()
      }
    }
  }
}
