package com.github.jangalinski.tabweb.avatar

import com.github.jangalinski.tabweb._foundation.Tabweb
import com.github.jangalinski.tabweb._foundation.TabwebComponent
import com.github.jangalinski.tabweb._foundation.TabwebContent
import com.github.jangalinski.tabweb._foundation.TabwebFoundationComponent
import com.github.jangalinski.tabweb._foundation.Initials as AvatarInitials
import com.github.jangalinski.tabweb._foundation.Image as AvatarImage
import com.github.jangalinski.tabweb.icon.Icon as AvatarIcon

/**
 * Represents the supported typed content of an [Avatar].
 */
sealed interface AvatarContent : TabwebContent{
  /**
   * Represents an icon rendered inside an avatar.
   *
   * @property content the icon rendered by the avatar.
   */
  data class Icon(val content: AvatarIcon) : AvatarContent, TabwebFoundationComponent by content

  /**
   * Represents initials rendered inside an avatar.
   *
   * @property content the initials rendered by the avatar.
   */
  data class Initials(val content: AvatarInitials) : AvatarContent, TabwebFoundationComponent by content

  /**
   * Represents a resource image rendered as an avatar background.
   *
   * @property content the image rendered by the avatar.
   */
  data class Image(val content: AvatarImage.Resource) : AvatarContent, TabwebFoundationComponent by content
}
