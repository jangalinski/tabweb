package com.github.jangalinski.tabweb.card

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebDsl
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.github.jangalinski.tabweb._foundation.css.ClassNames
import com.github.jangalinski.tabweb._foundation.css.ClassNames.modifier
import com.github.jangalinski.tabweb.table.TablerPaginationData
import com.github.jangalinski.tabweb._foundation.css.GridWidth
import com.github.jangalinski.tabweb._foundation.css.GridWidth.QUARTER

/**
 * Scoped builder for card-only Tabler grids.
 */
@TabwebDsl
@Deprecated("Use Card")
class TablerCardsScope internal constructor() {

  /**
   * Adds a regular Tabler card to the grid.
   */
  @Composable
  fun card(
    title: String? = null,
    width: GridWidth = GridWidth.FULL,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
  ) {
    Box(modifier = modifier.then(width.classNames.modifier())) {
      TablerCard(
        title = title,
        modifier = ClassNames.cardH100.modifier(),
        content = content,
      )
    }
  }


  /**
   * Adds a compact stat card to the grid.
   */
  @Composable
  @Deprecated("Use Card")
  fun statCard(
    title: String,
    value: String,
    note: String? = null,
    badgeText: String? = null,
    width: GridWidth = QUARTER,
    modifier: Modifier = Modifier,
  ) {
    TablerStatCard(
      title = title,
      value = value,
      note = note,
      badgeText = badgeText,
      width = width,
      modifier = modifier,
    )
  }
}


/**
 * Lays out Tabler cards in a responsive deck-style grid.
 */
@Composable
@Deprecated("Use Card")
fun TablerCards(
  modifier: Modifier = Modifier,
  content: @Composable TablerCardsScope.() -> Unit,
) {
  Row(
    modifier = modifier.then(
      ClassNames.row.modifier()
        .then(ClassNames.rowDeck.modifier())
        .then(ClassNames.rowCardsOnly.modifier())
        .then(ClassNames.g3.modifier())
        .then(ClassNames.mb4.modifier()),
    ),
    content = {
      TablerCardsScope().content()
    },
  )
}
