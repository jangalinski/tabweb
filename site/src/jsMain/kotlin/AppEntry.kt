package com.github.jangalinski.tabweb.site

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.github.jangalinski.tabweb.Tabweb.KobwebTablerApp
import com.github.jangalinski.tabweb._app.LocalTablerAppState
import com.github.jangalinski.tabweb._app.TablerShellConfig
import com.github.jangalinski.tabweb._app.TablerSiteConfig
import com.github.jangalinski.tabweb._app.TablerTheme
import com.github.jangalinski.tabweb._foundation.TabwebText.Companion.markdown
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb.button.ButtonColor
import com.github.jangalinski.tabweb.element.Tooltip
import com.github.jangalinski.tabweb.widget.ConfettiButton
import com.github.jangalinski.tabweb.widget.DarkModeButton
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.core.App
import kotlinx.browser.document

/**
 * Installs the Kobweb application wrapper for the repository documentation site.
 */
@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
  LaunchedEffect(Unit) {
    document.body?.className = "bg-body"
  }

  KobwebTablerApp(
    site = TablerSiteConfig(
      shell = TablerShellConfig(
        navbar = ::siteNavbar,
        navbarActions = {
          KDiv(modifier = Modifier.classNames("d-none", "d-md-flex", "me-3", "btn-list")) {
            KDiv(modifier = Modifier.classNames("nav-item")) {
              DarkModeButton()
            }
            KDiv(modifier = Modifier.classNames("nav-item")) {
              val color = if (LocalTablerAppState.current.settings.theme == TablerTheme.Dark) {
                ButtonColor.DARK
              } else {
                ButtonColor.LIGHT
              }
              ConfettiButton(color = color)(Tooltip("Confetti!").modifier)
            }
          }
        },
        footer = siteFooter(),
      ),
    ),
  ) {
    content()
  }
}
