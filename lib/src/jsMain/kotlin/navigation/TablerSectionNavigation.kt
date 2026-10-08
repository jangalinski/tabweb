package com.github.jangalinski.tabweb.navigation

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.compose.KAnchor
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.compose.KNav
import com.github.jangalinski.tabweb._foundation.compose.KSpan
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.attr
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.dataAttr
import com.varabyte.kobweb.compose.ui.modifiers.id

/**
 * Renders a documentation-style section navigation beside page content.
 *
 * The structure follows Tabler's docs menu: labelled groups contain vertical
 * links and nested groups expand when one of their descendants is active.
 *
 * @param data route-specific navigation data.
 * @param modifier additional attributes or classes for the navigation.
 */
@Composable
fun TablerSectionNavigation(
  data: TablerSectionNavigationData,
  modifier: Modifier = Modifier,
) {
  KNav(
    label = "Documentation",
    modifier = modifier.then(Modifier.classNames("space-y", "space-y-5")),
  ) {
    data.sections.forEach { section ->
      KDiv {
        KDiv(modifier = Modifier.classNames("subheader", "mb-2")) {
          KSpan(text = section.title)
        }
        KDiv(modifier = Modifier.classNames("nav", "nav-vertical")) {
          section.items.forEach { item -> renderItem(item) }
        }
      }
    }
  }
}

@Composable
private fun renderItem(item: TablerSectionNavigationData.Item) {
  when (item) {
    is TablerSectionNavigationData.Item.Link -> {
      KDiv {
        KAnchor(
          href = item.url,
          modifier = Modifier.classNames("nav-link").active(item.active),
        ) {
          KSpan(modifier = Modifier.classNames("nav-link-title"), text = item.title)
        }
      }
    }

    is TablerSectionNavigationData.Item.Group -> {
      val collapseId = "section-navigation-${item.title.toIdPart()}"
      KDiv {
        KAnchor(
          href = item.url ?: com.github.jangalinski.tabweb._foundation.Url.Hash("#$collapseId"),
          modifier = Modifier.classNames("nav-link", "d-flex", "align-items-center")
            .active(item.active)
            .dataAttr("bs-toggle", "collapse")
            .dataAttr("bs-target", "#$collapseId")
            .attr("aria-expanded", item.expanded.toString()),
        ) {
          KSpan(modifier = Modifier.classNames("nav-link-title"), text = item.title)
          KSpan(modifier = Modifier.classNames("nav-link-toggle")) {}
        }
        KDiv(
          modifier = Modifier.classNames("nav", "nav-vertical", "collapse")
            .then(if (item.expanded) Modifier.classNames("show") else Modifier)
            .id(collapseId),
        ) {
          item.items.forEach { renderItem(it) }
        }
      }
    }
  }
}

private fun Modifier.active(active: Boolean): Modifier = then(
  if (active) Modifier.classNames("active").attr("aria-current", "page") else Modifier,
)

private fun String.toIdPart(): String = lowercase()
  .replace(Regex("[^a-z0-9]+"), "-")
  .trim('-')
