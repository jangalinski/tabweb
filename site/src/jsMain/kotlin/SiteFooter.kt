package com.github.jangalinski.tabweb.site

import com.github.jangalinski.tabweb._app.TablerFooter
import com.github.jangalinski.tabweb._app.TablerFooterItem
import com.github.jangalinski.tabweb._foundation.compose.KAnchor
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.css.ClassNames
import com.github.jangalinski.tabweb._foundation.css.ClassNames.modifier
import com.varabyte.kobweb.compose.ui.modifiers.attr

/**
 * Builds the structured footer content for the documentation site.
 *
 * The footer component owns the responsive columns, list markup, and dotted
 * separators. This site only supplies the items displayed on each side.
 */
fun siteFooter(): TablerFooter = TablerFooter(
  left = listOf(
    TablerFooterItem {
      KText("@ 2026 tabweb - jangalinski")
    },
    TablerFooterItem {
      KText("tabweb component documentation")
    },
  ),
  right = listOf(
    TablerFooterItem {
      KAnchor(
        href = "https://github.com/jangalinski/tabweb",
        modifier = ClassNames.footerLink.modifier()
          .attr("target", "_blank")
          .attr("rel", "noopener noreferrer"),
      ) {
        KText("GitHub")
      }
    },
  ),
)
