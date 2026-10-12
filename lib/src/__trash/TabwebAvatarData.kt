package com.github.jangalinski.tabweb.avatar

/**
 * Pure configuration for a Tabweb avatar.
 */
data class TabwebAvatarData(
  val content: TabwebAvatarContent,
  val color: TabwebAvatarColor? = null,
  val size: TabwebAvatarSize = TabwebAvatarSize.DEFAULT,
  val shape: TabwebAvatarShape = TabwebAvatarShape.DEFAULT,
  val status: TabwebAvatarStatus? = null,
  val ariaLabel: String? = null,
)

/** Pure configuration for a horizontal collection of Tabweb avatars. */
data class TabwebAvatarListData(
  val avatars: List<TabwebAvatarData>,
  val stacked: Boolean = false,
)

/** Content rendered inside a Tabweb avatar. */
sealed interface TabwebAvatarContent {
  /** An image from the consuming Kobweb application's public resources. */
  data class ImageResource(val resource: String, val altText: String? = null) : TabwebAvatarContent

  /** An inline SVG icon. */
  data class Icon(val svg: String, val altText: String? = null) : TabwebAvatarContent

  /** Text initials, such as `JG`. */
  data class Initials(val value: String) : TabwebAvatarContent
}

/** Opaque Tabweb background colors for initials and icons. */
enum class TabwebAvatarColor(
  internal val backgroundClassName: String,
  internal val foregroundClassName: String = "text-white",
) {
  BLUE("bg-blue"),
  AZURE("bg-azure"),
  INDIGO("bg-indigo"),
  PURPLE("bg-purple"),
  PINK("bg-pink"),
  RED("bg-red"),
  ORANGE("bg-orange"),
  YELLOW("bg-yellow", "text-dark"),
  LIME("bg-lime", "text-dark"),
  GREEN("bg-green"),
  TEAL("bg-teal"),
  CYAN("bg-cyan"),
  GRAY("bg-secondary"),
  PRIMARY("bg-primary"),
}

/** Tabweb's documented avatar sizes. */
enum class TabwebAvatarSize(internal val className: String?) {
  EXTRA_SMALL("avatar-xs"),
  SMALL("avatar-sm"),
  DEFAULT(null),
  LARGE("avatar-lg"),
  EXTRA_LARGE("avatar-xl"),
}

/** Tabweb and Bootstrap avatar shapes. */
enum class TabwebAvatarShape(internal val className: String?) {
  DEFAULT(null),
  ROUNDED("rounded"),
  CIRCLE("rounded-circle"),
  SQUARE("rounded-0"),
  LARGE_ROUNDED("rounded-3"),
}

/** Optional badge overlaid on an avatar. */
data class TabwebAvatarStatus(
  val color: TabwebAvatarStatusColor,
  val label: String? = null,
)

/** Tabweb background colors used by an avatar status badge. */
enum class TabwebAvatarStatusColor(internal val className: String) {
  SUCCESS("bg-success"),
  WARNING("bg-warning"),
  DANGER("bg-danger"),
  INFO("bg-info"),
  GRAY("bg-gray"),
}
