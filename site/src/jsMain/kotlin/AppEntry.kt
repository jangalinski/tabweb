package com.github.jangalinski.tabweb.site

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.github.jangalinski.tabweb.Tabweb.TabwebApp
import com.github.jangalinski.tabweb._app.LocalTabwebAppState
import com.github.jangalinski.tabweb._app.TabwebSettings
import com.github.jangalinski.tabweb._app.TabwebShellConfig
import com.github.jangalinski.tabweb._app.TabwebSiteConfig
import com.github.jangalinski.tabweb._app.TabwebTheme
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb.button.ButtonColor
import com.github.jangalinski.tabweb.element.Tooltip
import com.github.jangalinski.tabweb.navigation.NavbarBehavior
import com.github.jangalinski.tabweb.navigation.navbarFactory
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

  TabwebApp(
    site = TabwebSiteConfig(
      shell = TabwebShellConfig(
        navbar = GeneratedSiteNavigation.navbarFactory(),
        navigation = GeneratedSiteNavigation,
        navbarActions = {
          KDiv(modifier = Modifier.classNames("d-none", "d-md-flex", "me-3", "btn-list")) {
            KDiv(modifier = Modifier.classNames("nav-item")) {
              DarkModeButton()
            }
            KDiv(modifier = Modifier.classNames("nav-item")) {
              val color = if (LocalTabwebAppState.current.settings.theme == TabwebTheme.DARK) {
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
    settings = TabwebSettings(
      navbarBehavior = NavbarBehavior.STICKY_TOP,
    ),
  ) {
    content()
  }
}
