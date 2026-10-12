package com.github.jangalinski.tabweb._app

import com.github.jangalinski.tabweb._foundation.HtmlAttributeAdapter
import com.github.jangalinski.tabweb._foundation.HtmlAttributeName
import com.github.jangalinski.tabweb._foundation.SessionItemAdapter
import com.github.jangalinski.tabweb._foundation.SessionKey
import com.github.jangalinski.tabweb._foundation.SideEffectBehavior

/**
 * Selects the color mode applied to the document for Tabweb components.
 */
enum class TabwebTheme(private val value: String?) : SideEffectBehavior {
  /**
   * Leaves `data-bs-theme` unset so Tabweb uses its system/default behavior.
   */
  SYSTEM(null),

  /**
   * Applies Tabweb's light color mode.
   */
  LIGHT("light"),

  /**
   * Applies Tabweb's dark color mode.
   */
  DARK("dark"),
  ;

  companion object {
    internal val SESSION_KEY = SessionKey("tabweb.theme")
    private val HTML_ATTRIBUTE = HtmlAttributeName("data-bs-theme")

    /**
     * Restores the stored theme or returns [defaultValue] when no valid value is stored.
     *
     * @param sessionItem browser-session storage adapter.
     * @param defaultValue theme used when the session has no recognized preference.
     * @return the stored theme or [defaultValue].
     */
    fun getOrDefault(
      sessionItem: SessionItemAdapter,
      defaultValue: TabwebTheme = SYSTEM,
    ): TabwebTheme = sessionItem[SESSION_KEY]
      ?.let { stored -> entries.firstOrNull { it.value == stored } }
      ?: defaultValue
  }

  override fun invoke(sessionItem: SessionItemAdapter, htmlAttribute: HtmlAttributeAdapter) {
    htmlAttribute[HTML_ATTRIBUTE] = value
    sessionItem[SESSION_KEY] = value
  }
}
