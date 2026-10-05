package com.github.jangalinski.tabweb._foundation

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.compose.KHtmlDiv
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.varabyte.kobweb.compose.ui.Modifier
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.CancellationToken
import org.intellij.markdown.parser.MarkdownParser

sealed interface TabwebText : TabwebTextComponent {
  companion object {
    fun html(value: String) = HtmlText(value)
    fun plain(value: String) = PlainText(value)
    fun markdown(value: String) = MarkdownText(value)
  }
}

data class HtmlText(override val value: String) : TabwebText {
  @Composable
  override fun invoke(modifier: Modifier) {
    KHtmlDiv(html = value, modifier = modifier)
  }
}

data class PlainText(override val value: String) : TabwebText {
  @Composable
  override fun invoke(modifier: Modifier) {
    KText(value)
  }
}

data class MarkdownText(override val value: String) : TabwebText {

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

  val html by lazy {
    render(parse(value), value)
  }

  @Composable
  override fun invoke(modifier: Modifier) {
    HtmlText(html).invoke(modifier)
  }
}
