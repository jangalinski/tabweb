package com.github.jangalinski.tabweb._app

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.compose.KButton
import com.github.jangalinski.tabweb._foundation.compose.KSpan
import com.github.jangalinski.tabweb.navbar.TablerNavbar
import com.github.jangalinski.tabweb.navigation.TablerSectionNavigation
import com.github.jangalinski.tabweb._foundation.css.ClassNames
import com.github.jangalinski.tabweb._foundation.css.ClassNames.modifier
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.attr
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.dataAttr
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.data.getValue
import com.varabyte.kobweb.core.layout.Layout

/**
 * Kobweb layout that renders the shared Tabler page shell around a route.
 *
 * The composition follows the preview's major regions: `BEGIN SIDEBAR` or
 * `BEGIN NAVBAR`, then `page-wrapper`, `BEGIN PAGE HEADER`, `BEGIN PAGE BODY`,
 * and `BEGIN FOOTER`. The route itself is supplied through [content], while
 * shared navigation, footer content, and page metadata arrive through
 * `@InitRoute` data.
 *
 * Keeping this shell in a Kobweb `@Layout` lets pages focus on their own
 * content while preserving one stable DOM boundary for Tabler CSS and
 * responsive behavior.
 *
 * ```
 * TablerPage
 *  ├─ TablerSidebar and TablerNavbar
 *  ├─ TablerPageWrapper
 *  │  ├─ TablerPageHeader
 *  │  ├─ PagePart (TablerPageBody)
 *  │  └─ TablerFooter
 * ```
 *
 * @param ctx Kobweb page context containing route-scoped layout data.
 * @param content route content rendered in the page body.
 */
@Layout
@Composable
fun TablerLayout(
  ctx: PageContext,
  content: @Composable () -> Unit
) {
  val layoutData = ctx.data.getValue<TablerLayoutData>()
  val pageMeta = ctx.data.getValue<TablerPageMeta>()

  TablerLayout(
    ctx = ctx,
    layoutData = layoutData,
    pageMeta = pageMeta,
    content = content,
  )
}

/**
 * Renders the shared Tabler page shell with explicitly supplied route data.
 *
 * This overload is useful for generated page sources, such as Kobweb Markdown,
 * whose layout data is available to the layout but cannot be added to Kobweb's
 * read-only page data store during composition.
 *
 * @param ctx Kobweb page context for the current route.
 * @param layoutData route-specific navigation and footer data.
 * @param pageMeta title, subtitle, and breadcrumbs for the page header.
 * @param content route content rendered in the page body.
 */
@Composable
fun TablerLayout(
  ctx: PageContext,
  layoutData: TablerLayoutData,
  pageMeta: TablerPageMeta,
  content: @Composable () -> Unit,
) {
  val site = LocalTablerSiteConfig.current
  val footer = layoutData.footer ?: site.shell.footer

  TablerPage {

    TablerNavbar(
      brand = site.shell.brand,
      data = site.shell.navbar.create(layoutData.activeRoute),
      actions = site.shell.navbarActions,
    )

    TablerPageWrapper {

      TablerPageHeader(
        title = pageMeta.title,
        subtitle = pageMeta.subtitle,
        breadcrumbs = pageMeta.breadcrumbs,
      )

      TablerPageBody {
        val sectionNavigation = layoutData.sectionNavigation
          ?.create(layoutData.activeRoute)

        if (sectionNavigation == null) {
          content()
        } else {
          KDiv(modifier = Modifier.classNames("row", "g-0")) {
            KDiv(modifier = Modifier.classNames("col-lg-3", "pe-lg-4", "mb-4", "mb-lg-0")) {
              KButton(
                modifier = Modifier.classNames("btn", "btn-outline-secondary", "d-lg-none", "mb-3")
                  .dataAttr("bs-toggle", "offcanvas")
                  .dataAttr("bs-target", "#section-navigation-offcanvas")
                  .attr("aria-controls", "section-navigation-offcanvas"),
              ) {
                KSpan(text = "Open documentation menu")
              }
              KDiv(
                modifier = Modifier.classNames("offcanvas-lg", "offcanvas-start")
                  .attr("id", "section-navigation-offcanvas")
                  .attr("aria-label", "Documentation"),
              ) {
                KDiv(modifier = Modifier.classNames("offcanvas-header", "d-lg-none")) {
                  KSpan(modifier = Modifier.classNames("offcanvas-title"), text = "Documentation")
                  KButton(
                    modifier = Modifier.classNames("btn-close")
                      .dataAttr("bs-dismiss", "offcanvas")
                      .dataAttr("bs-target", "#section-navigation-offcanvas")
                      .attr("aria-label", "Close documentation menu"),
                  ) {}
                }
                KDiv(modifier = Modifier.classNames("offcanvas-body")) {
                  TablerSectionNavigation(sectionNavigation)
                }
              }
            }
            KDiv(modifier = Modifier.classNames("col-lg-9")) {
              content()
            }
          }
        }
      }

      footer(Modifier.classNames("footer", "footer-transparent", "d-print-none"))
    }
  }
}

/**
 * Root container for a Tabler page shell.
 *
 * This corresponds to the preview's outer `<!-- BEGIN PAGE -->` region and
 * normally contains the sidebar, navbar, and [TablerPageWrapper]. In Kobweb
 * terms, it is the stable composable boundary around route content rather
 * than a page route itself.
 */
@Composable
private fun TablerPage(content: @Composable () -> Unit) {
  KDiv(modifier = ClassNames.page.modifier(), content = content)
}

/**
 * Wrapper for the route-facing portion of a Tabler page.
 *
 * This is the preview's `.page-wrapper` block. It groups the page header,
 * [TablerPageBody], and footer while the outer [TablerPage] keeps global
 * navigation beside it. That separation mirrors Kobweb's layout design:
 * shared chrome surrounds the composable content supplied by a route.
 */
@Composable
private fun TablerPageWrapper(content: @Composable () -> Unit) {
  KDiv(modifier = ClassNames.pageWrapper.modifier(), content = content)
}
