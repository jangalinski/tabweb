package com.github.jangalinski.tabweb.avatar

import com.github.jangalinski.tabweb._foundation.css.cssClass

/**
 * Maps the avatar model to the Tabweb CSS classes used by its renderers.
 */
internal data object AvatarCss {
  const val CLASS = "avatar"

  val avatar = cssClass(CLASS)
  val avatarList = cssClass("$CLASS-list")
  val avatarListStacked = cssClass("$CLASS-list-stacked")
}
