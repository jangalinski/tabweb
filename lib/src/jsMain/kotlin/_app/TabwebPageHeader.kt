package com.github.jangalinski.tabweb._app

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb.breadcrumb.TabwebBreadcrumbs
import com.github.jangalinski.tabweb.breadcrumb.BreadcrumbItem
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.compose.KH1
import com.github.jangalinski.tabweb._foundation.compose.KP
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.css.ClassNames
import com.github.jangalinski.tabweb._foundation.css.ClassNames.modifier
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Renders the main Tabweb page header block.
 * Page-header block for the current Tabweb route.
 *
 * This corresponds to the preview's `<!-- BEGIN PAGE HEADER -->` region. It
 * is layout chrome owned by the shared Kobweb layout, while title, subtitle,
 * breadcrumbs, and header actions are route-level data or content.
 * Kobweb layouts use it for route metadata that should remain outside the
 * route's main content, such as the title, subtitle, breadcrumbs, and future
 * header actions.
 *
 * @param title visible page title.
 * @param subtitle optional supporting text below the title.
 * @param breadcrumbs optional breadcrumb trail rendered above the title.
 * @param modifier additional attributes or classes applied to the header block.
 */
@Composable
fun TabwebPageHeader(
  title: String,
  subtitle: String? = null,
  breadcrumbs: List<BreadcrumbItem> = emptyList(),
  modifier: Modifier = Modifier,
) {
  KDiv(modifier = modifier.then(ClassNames.pageHeader.modifier())) {
    KDiv(modifier = ClassNames.containerXl.modifier()) {
      if (breadcrumbs.isNotEmpty()) {
        TabwebBreadcrumbs(
          items = breadcrumbs,
          modifier = Modifier.then(ClassNames.mb2.modifier()),
        )
      }
      KDiv(
        modifier = ClassNames.row.modifier()
          .then(ClassNames.g2.modifier())
          .then(ClassNames.alignItemsCenter.modifier()),
      ) {
        KDiv(modifier = ClassNames.pageHeaderCol.modifier()) {
          KH1(modifier = ClassNames.pageTitle.modifier()) {
            KText(title)
          }
          subtitle?.takeIf { it.isNotBlank() }?.let {
            KP(modifier = ClassNames.textSecondarySubheader.modifier()) {
              KText(it)
            }
          }
        }
      }
    }
  }
}
