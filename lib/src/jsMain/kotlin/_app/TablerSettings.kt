package com.github.jangalinski.tabweb._app

import androidx.compose.runtime.Immutable
import com.github.jangalinski.tabweb._foundation.SideEffectBehavior
import com.github.jangalinski.tabweb._foundation.htmlAttribute
import com.github.jangalinski.tabweb._foundation.sessionItem
import com.github.jangalinski.tabweb.navigation.NavbarBehavior
import org.w3c.dom.Document
import org.w3c.dom.Window

/**
 * Immutable snapshot of Tabler presentation settings for the current application state.
 *
 * @param theme Selects the color mode applied to the document for Tabler components.
 * @param navbarBehavior Selects the behavior of the navbar.
 */
@Immutable
data class TablerSettings(
  val theme: TablerTheme = TablerTheme.SYSTEM,
  val navbarBehavior: NavbarBehavior = NavbarBehavior.DEFAULT,
) {
  fun sideEffect(document: Document, window: Window) {
    val sessionItem = window.sessionItem
    val htmlAttribute = document.htmlAttribute

    listOf<SideEffectBehavior>(theme, navbarBehavior).forEach {
      it.invoke(sessionItem, htmlAttribute)
    }
  }
}
