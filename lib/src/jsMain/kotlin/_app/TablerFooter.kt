package com.github.jangalinski.tabweb._app

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebComponent
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.compose.KFooter
import com.github.jangalinski.tabweb._foundation.compose.KLi
import com.github.jangalinski.tabweb._foundation.compose.KUl
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames

/**
 * A single item rendered inside one of the footer's dotted inline lists.
 *
 * Items can either adapt an existing [TabwebComponent] or render arbitrary
 * composable content. The footer owns the list-item wrapper and separator
 * structure, so item implementations only describe their visible content.
 */
interface TablerFooterItem : TabwebComponent {
  companion object {
    /**
     * Creates a footer item that renders an existing Tabweb component.
     *
     * @param component component rendered as the item's content.
     * @return a footer item backed by [component].
     */
    operator fun invoke(component: TabwebComponent): TablerFooterItem = object : TablerFooterItem {
      @Composable
      override fun invoke(modifier: Modifier) {
        component(modifier)
      }
    }

    /**
     * Creates a footer item from arbitrary composable content.
     *
     * @param content composable content rendered inside the footer list item.
     * @return a footer item backed by [content].
     */
    operator fun invoke(content: @Composable () -> Unit): TablerFooterItem = object : TablerFooterItem {
      @Composable
      override fun invoke(modifier: Modifier) {
        content()
      }
    }
  }
}

/**
 * Structured content for a Tabler page footer.
 *
 * The left and right lists are rendered in Tabler's responsive footer layout.
 * Each list item is separated by the `list-inline-dots` style.
 */
interface TablerFooter : TabwebComponent {
  /**
   * Items shown on the left side of the footer.
   */
  val left: List<TablerFooterItem>

  /**
   * Items shown on the right side of the footer.
   */
  val right: List<TablerFooterItem>

  companion object {
    /**
     * Creates a structured footer component.
     *
     * @param left items shown on the left side of the footer.
     * @param right items shown on the right side of the footer.
     * @return a renderable footer component.
     */
    operator fun invoke(
      left: List<TablerFooterItem> = emptyList(),
      right: List<TablerFooterItem> = emptyList(),
    ): TablerFooter = object : TablerFooter {
      override val left: List<TablerFooterItem> = left
      override val right: List<TablerFooterItem> = right

      @Composable
      override fun invoke(modifier: Modifier) {
        renderTablerFooter(this, modifier)
      }
    }
  }
}

@Composable
private fun renderTablerFooter(footer: TablerFooter, modifier: Modifier) {
  KFooter(modifier) {
    KDiv(modifier = Modifier.classNames("row", "text-center", "align-items-center", "flex-row-reverse")) {
      KDiv(modifier = Modifier.classNames("col-lg-auto", "ms-lg-auto")) {
        FooterItemList(footer.right)
      }
      KDiv(modifier = Modifier.classNames("col-12", "col-lg-auto", "mt-3", "mt-lg-0")) {
        FooterItemList(footer.left)
      }
    }
  }
}

@Composable
private fun FooterItemList(items: List<TablerFooterItem>) {
  KUl(modifier = Modifier.classNames("list-inline", "list-inline-dots", "mb-0")) {
    items.forEach { item ->
      KLi(modifier = Modifier.classNames("list-inline-item")) {
        item()
      }
    }
  }
}
