package com.github.jangalinski.tabweb.navigation

import com.github.jangalinski.tabweb._foundation.*
import com.github.jangalinski.tabweb._foundation.css.cssClass

/**
 * Selects the navbar's positioning behavior.
 */
enum class NavbarBehavior(
  private val htmlValue: String?,
  private val sessionValue: String?,
) : SideEffectBehavior {

  /**
   * Use Tabler's normal document-flow navbar behavior.
   */
  DEFAULT(null, null),

  /**
   * Keep the brand logo and nav-actions visible when page scrolls.
   */
  STICKY("sticky", "sticky"),

  /**
  * Keep the complete navbar section visible when page scrolls.
  */
  STICKY_TOP(null, "sticky-top"),
  ;

  companion object {
    private val SESSION_KEY = SessionKey("tabweb.navbarBehavior")
    private val HTML_ATTRIBUTE = HtmlAttributeName("data-bs-navbar")

    internal val CSS_STICKY_TOP = cssClass("sticky-top")

    /**
     * Restores the stored navbar behavior or returns [defaultValue] when no valid value is stored.
     *
     * @param sessionItem browser-session storage adapter.
     * @param defaultValue behavior used when the session has no recognized preference.
     * @return the stored navbar behavior or [defaultValue].
     */
    fun getOrDefault(
      sessionItem: SessionItemAdapter,
      defaultValue: NavbarBehavior = DEFAULT,
    ): NavbarBehavior = sessionItem[SESSION_KEY]
      ?.let { stored -> entries.firstOrNull { it.htmlValue == stored } }
      ?: defaultValue
  }

  override fun invoke(sessionItem: SessionItemAdapter, htmlAttribute: HtmlAttributeAdapter) {
    htmlAttribute[HTML_ATTRIBUTE] = htmlValue
    sessionItem[SESSION_KEY] = sessionValue
  }
}
