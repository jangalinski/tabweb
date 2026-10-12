package com.github.jangalinski.tabweb.avatar

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebComponent
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.css.plus
import com.github.jangalinski.tabweb._foundation.takeIf
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * A Tabweb avatar list component keeps all [Avatar]s in the same line together.
 */
interface AvatarList : TabwebComponent {
  companion object {
    /**
     * Creates a list containing one configured [Avatar].
     *
     * @param avatars the single avatar in the list.
     * @param stacked whether child avatars overlap in the Tabweb stacked-list layout.
     * @param size the size applied to every avatar in the list.
     * @return an [AvatarList] containing [avatars].
     */
    operator fun invoke(
      avatars: Avatar,
      stacked: Boolean = false,
      size: AvatarListSize = AvatarListSize.DEFAULT,
    ): AvatarList = invoke(stacked, size, listOf(avatars))

    /**
     * Creates a list containing the supplied avatars.
     *
     * @param stacked whether child avatars overlap in the Tabweb stacked-list layout.
     * @param size the size applied to every avatar in the list.
     * @param avatars the avatars in display order.
     * @return an [AvatarList] containing [avatars].
     */
    operator fun invoke(
      stacked: Boolean = false,
      size: AvatarListSize = AvatarListSize.DEFAULT,
      vararg avatars: Avatar
    ) : AvatarList = invoke(stacked, size, avatars.toList())

    /**
     * Creates a list containing the supplied avatars.
     *
     * @param stacked whether child avatars overlap in the Tabweb stacked-list layout.
     * @param size the size applied to every avatar in the list.
     * @param avatars the avatars in display order.
     * @return an [AvatarList] containing [avatars].
     */
    operator fun invoke(
      stacked: Boolean = false,
      size: AvatarListSize = AvatarListSize.DEFAULT,
      avatars: List<Avatar>
    ) : AvatarList = object : AvatarList {
      override val stacked: Boolean = stacked
      override val size: AvatarListSize = size
      override val avatars: List<Avatar> = avatars
    }
  }

  /**
   * Whether child avatars overlap in the Tabweb stacked-list layout.
   */
  val stacked: Boolean

  /**
   * The size applied to every avatar in the list.
   */
  val size: AvatarListSize

  /**
   * The avatars rendered in display order.
   */
  val avatars: List<Avatar>

  @Composable
  override fun invoke(modifier: Modifier) {
    KDiv(modifier = AvatarCss.avatarList + AvatarCss.avatarListStacked.takeIf(stacked) + size + modifier) {
      avatars.forEach { it.invoke() }
    }
  }
}
