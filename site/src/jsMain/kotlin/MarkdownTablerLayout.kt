package com.github.jangalinski.tabweb.site

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._app.TablerLayout
import com.github.jangalinski.tabweb._app.TablerLayoutData
import com.github.jangalinski.tabweb._app.TablerPageMeta
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobwebx.markdown.markdown

/**
 * Supplies the route data required by the shared Tabler layout for generated
 * Kobweb Markdown pages.
 *
 * Markdown pages do not run the site's Kotlin `@InitRoute` helpers, so this
 * adapter maps their front matter and current route into the same data objects
 * used by regular site pages before delegating to [TablerLayout].
 *
 * @param ctx page context for the generated Markdown page.
 * @param content generated Markdown page content.
 */
@Layout
@Composable
fun MarkdownTablerLayout(
  ctx: PageContext,
  content: @Composable () -> Unit,
) {
  val markdown = ctx.markdown ?: error("MarkdownTablerLayout requires a generated Markdown page")
  val frontMatter = markdown.frontMatter
  val title = frontMatter["title"]?.singleOrNull()
    ?: markdown.path.substringAfterLast('/').substringBeforeLast('.').toPageTitle()
  val subtitle = frontMatter["subtitle"]?.singleOrNull()
    ?: frontMatter["description"]?.singleOrNull()

  TablerLayout(
    ctx = ctx,
    layoutData = TablerLayoutData(activeRoute = ctx.route.path),
    pageMeta = TablerPageMeta(title = title, subtitle = subtitle),
    content = content,
  )
}

private fun String.toPageTitle(): String = replace('-', ' ')
  .replace('_', ' ')
  .split(' ')
  .filter(String::isNotBlank)
  .joinToString(" ") { word -> word.replaceFirstChar(Char::titlecase) }
