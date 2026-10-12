package com.github.jangalinski.tabweb._foundation

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.compose.KHtmlDiv
import com.varabyte.kobweb.compose.ui.Modifier
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.CancellationToken
import org.intellij.markdown.parser.MarkdownParser

/**
 * A Tabweb component that renders Markdown text as HTML.
 */
data class Markdown(
  val markdown: String
) : TabwebFoundationComponent {

  companion object {

    internal val flavour = CommonMarkFlavourDescriptor()
    internal fun parse(markdown: String): ASTNode {
      val flavour = CommonMarkFlavourDescriptor()
      val parser = MarkdownParser(
        flavour = flavour,
        cancellationToken = CancellationToken.NonCancellable
      )

      return parser.parse(
        root = MarkdownElementTypes.MARKDOWN_FILE,
        text = markdown as CharSequence
      )
    }

    internal fun render(tree: ASTNode, markdown: String): String = HtmlGenerator(
      markdown,
      tree,
      flavour
    ).generateHtml()
  }

  private val html: String by lazy { render(parse(markdown), markdown) }

  @Composable
  override fun invoke(modifier: Modifier) {
    KHtmlDiv(html = html, modifier = modifier)
  }
}
