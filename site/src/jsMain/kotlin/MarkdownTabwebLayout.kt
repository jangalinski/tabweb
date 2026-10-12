package com.github.jangalinski.tabweb.site

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._app.TabwebLayout
import com.github.jangalinski.tabweb._app.TabwebLayoutData
import com.github.jangalinski.tabweb._app.TabwebPageMeta
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb.navigation.sectionNavigationFactory
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.navigation.BasePath
import com.varabyte.kobweb.navigation.remove
import com.varabyte.kobwebx.markdown.markdown

/**
 * Supplies the route data required by the shared Tabweb layout for generated
 * Kobweb Markdown pages.
 *
 * Markdown pages do not run the site's Kotlin `@InitRoute` helpers, so this
 * adapter maps their front matter and current route into the same data objects
 * used by regular site pages before delegating to [TabwebLayout].
 *
 * @param ctx page context for the generated Markdown page.
 * @param content generated Markdown page content.
 */
@Layout
@Composable
fun MarkdownTabwebLayout(
  ctx: PageContext,
  content: @Composable () -> Unit,
) {
  val markdown = ctx.markdown ?: error("MarkdownTabwebLayout requires a generated Markdown page")
  val frontMatter = markdown.frontMatter
  val title = frontMatter["title"]?.singleOrNull()
    ?: markdown.path.substringAfterLast('/').substringBeforeLast('.').toPageTitle()
  val subtitle = frontMatter["subtitle"]?.singleOrNull()
    ?: frontMatter["description"]?.singleOrNull()

  TabwebLayout(
    ctx = ctx,
    layoutData = TabwebLayoutData(
      activeRoute = BasePath.remove(ctx.route.path),
      sectionNavigation = GeneratedSiteNavigation.data.sectionNavigationFactory(),
    ),
    pageMeta = TabwebPageMeta(title = title, subtitle = subtitle),
    content = {
      KDiv("card", "card-lg") {
        KDiv("card-body") {
          KDiv("prose") {
            content()
          }
        }
      }
    },
  )
}

private fun String.toPageTitle(): String = replace('-', ' ')
  .replace('_', ' ')
  .split(' ')
  .filter(String::isNotBlank)
  .joinToString(" ") { word -> word.replaceFirstChar(Char::titlecase) }
