package com.github.jangalinski.tabweb.button

/**
 * Enumerates the Tabler theme, palette, and social colors available to a [Button].
 */
enum class ButtonColor(private val className: String) {
  PRIMARY("primary"),
  SECONDARY("secondary"),
  SUCCESS("success"),
  WARNING("warning"),
  DANGER("danger"),
  INFO("info"),
  DARK("dark"),
  LIGHT("light"),
  BLUE("blue"),
  AZURE("azure"),
  INDIGO("indigo"),
  PURPLE("purple"),
  PINK("pink"),
  RED("red"),
  ORANGE("orange"),
  YELLOW("yellow"),
  LIME("lime"),
  GREEN("green"),
  TEAL("teal"),
  CYAN("cyan"),
  FACEBOOK("facebook"),
  TWITTER("twitter"),
  X("x"),
  LINKEDIN("linkedin"),
  GOOGLE("google"),
  YOUTUBE("youtube"),
  VIMEO("vimeo"),
  DRIBBBLE("dribbble"),
  GITHUB("github"),
  INSTAGRAM("instagram"),
  PINTEREST("pinterest"),
  VK("vk"),
  RSS("rss"),
  FLICKR("flickr"),
  BITBUCKET("bitbucket"),
  TABLER("tabler"),
  ;

  companion object {
    /**
     * Default theme color used when no explicit color is supplied.
     */
    val DEFAULT = PRIMARY

    /**
     * Theme colors featured by the standard button examples.
     */
    val THEME = listOf(PRIMARY, SECONDARY, SUCCESS, WARNING, DANGER, INFO, DARK, LIGHT)

    /**
     * Additional Tabler palette colors.
     */
    val PALETTE = listOf(BLUE, AZURE, INDIGO, PURPLE, PINK, RED, ORANGE, YELLOW, LIME, GREEN, TEAL, CYAN)

    /**
     * Social and product brand colors.
     */
    val SOCIAL = listOf(
      FACEBOOK, TWITTER, X, LINKEDIN, GOOGLE, YOUTUBE, VIMEO, DRIBBBLE, GITHUB, INSTAGRAM, PINTEREST, VK, RSS, FLICKR,
      BITBUCKET, TABLER,
    )
  }

  /**
   * Returns the CSS suffix retained inside the button concept.
   *
   * @return the component-local CSS class suffix for this color.
   */
  internal fun cssName(): String = className
}
