package com.github.jangalinski.tabweb.table

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebComponentScope
import com.github.jangalinski.tabweb._foundation.TabwebDsl
import com.github.jangalinski.tabweb._foundation.compose.*
import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.github.jangalinski.tabweb._foundation.css.plus
import com.github.jangalinski.tabweb.avatar.Avatar
import com.github.jangalinski.tabweb.badge.Badge
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.attr
import org.jetbrains.compose.web.attributes.InputType

/**
 * Receiver scope for building a Tabler table.
 */
@TabwebDsl
interface TableScope : TabwebComponentScope {
  /**
   * Defines the table header.
   */
  @Composable
  fun header(
    sticky: Boolean = false,
    modifier: Modifier = Modifier,
    content: @Composable TableHeaderScope.() -> Unit,
  )

  /**
   * Alias for [header].
   */
  @Composable
  fun thead(
    sticky: Boolean = false,
    modifier: Modifier = Modifier,
    content: @Composable TableHeaderScope.() -> Unit,
  ) = header(sticky = sticky, modifier = modifier, content = content)

  /**
   * Defines the table body.
   */
  @Composable
  fun body(
    modifier: Modifier = Modifier,
    content: @Composable TableBodyScope.() -> Unit,
  )

  /**
   * Alias for [body].
   */
  @Composable
  fun tbody(
    modifier: Modifier = Modifier,
    content: @Composable TableBodyScope.() -> Unit,
  ) = body(modifier = modifier, content = content)

  /**
   * Defines the table footer.
   */
  @Composable
  fun footer(
    modifier: Modifier = Modifier,
    content: @Composable TableFooterScope.() -> Unit,
  )

  /**
   * Alias for [footer].
   */
  @Composable
  fun tfoot(
    modifier: Modifier = Modifier,
    content: @Composable TableFooterScope.() -> Unit,
  ) = footer(modifier = modifier, content = content)

  /**
   * Adds a row directly to the table.
   */
  @Composable
  fun row(
    variant: TableRowVariant? = null,
    active: Boolean = false,
    modifier: Modifier = Modifier,
    content: @Composable TableRowScope.() -> Unit,
  )
}

/**
 * Receiver scope for building a table header.
 */
@TabwebDsl
interface TableHeaderScope : TabwebComponentScope {
  /**
   * Adds a column header cell with text.
   */
  @Composable
  fun cell(text: String, noWrap: Boolean = false, modifier: Modifier = Modifier)

  /**
   * Adds a column header cell with arbitrary composable content.
   */
  @Composable
  fun cell(noWrap: Boolean = false, modifier: Modifier = Modifier, content: @Composable () -> Unit)

  /**
   * Adds a column header cell with text.
   */
  @Composable
  fun th(text: String, noWrap: Boolean = false, modifier: Modifier = Modifier) = cell(text, noWrap, modifier)

  /**
   * Adds a column header cell with arbitrary composable content.
   */
  @Composable
  fun th(noWrap: Boolean = false, modifier: Modifier = Modifier, content: @Composable () -> Unit) =
    cell(noWrap = noWrap, modifier = modifier, content = content)

  /**
   * Adds a custom header row.
   */
  @Composable
  fun row(modifier: Modifier = Modifier, content: @Composable TableRowScope.() -> Unit)

  /**
   * Adds a custom header row.
   */
  @Composable
  fun tr(modifier: Modifier = Modifier, content: @Composable TableRowScope.() -> Unit) = row(modifier, content)
}

/**
 * Receiver scope for building table body rows.
 */
@TabwebDsl
interface TableBodyScope : TabwebComponentScope {
  /**
   * Adds a row to the table body.
   */
  @Composable
  fun row(
    variant: TableRowVariant? = null,
    active: Boolean = false,
    modifier: Modifier = Modifier,
    content: @Composable TableRowScope.() -> Unit,
  )

  /**
   * Adds a row to the table body.
   */
  @Composable
  fun tr(
    variant: TableRowVariant? = null,
    active: Boolean = false,
    modifier: Modifier = Modifier,
    content: @Composable TableRowScope.() -> Unit,
  ) = row(variant, active, modifier, content)
}

/**
 * Receiver scope for building table footer rows.
 */
@TabwebDsl
interface TableFooterScope : TabwebComponentScope {
  /**
   * Adds a cell to the footer row.
   */
  @Composable
  fun cell(text: String, modifier: Modifier = Modifier)

  /**
   * Adds a cell to the footer row with arbitrary composable content.
   */
  @Composable
  fun cell(modifier: Modifier = Modifier, content: @Composable () -> Unit)

  /**
   * Adds a row to the table footer.
   */
  @Composable
  fun row(modifier: Modifier = Modifier, content: @Composable TableRowScope.() -> Unit)

  /**
   * Adds a row to the table footer.
   */
  @Composable
  fun tr(modifier: Modifier = Modifier, content: @Composable TableRowScope.() -> Unit) = row(modifier, content)
}

/**
 * Receiver scope for building cells inside a table row.
 */
@TabwebDsl
interface TableRowScope : TabwebComponentScope {
  /**
   * Adds a data cell (`<td>`) or row header cell (`<th scope="row">`) with text.
   */
  @Composable
  fun cell(
    text: String,
    muted: Boolean = false,
    isRowHeader: Boolean = false,
    modifier: Modifier = Modifier,
  )

  /**
   * Adds a data cell (`<td>`) or row header cell (`<th scope="row">`) with arbitrary composable content.
   */
  @Composable
  fun cell(
    isRowHeader: Boolean = false,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
  )

  /**
   * Adds a data cell (`<td>`) with text.
   */
  @Composable
  fun td(text: String, muted: Boolean = false, modifier: Modifier = Modifier) =
    cell(text = text, muted = muted, isRowHeader = false, modifier = modifier)

  /**
   * Adds a data cell (`<td>`) with arbitrary composable content.
   */
  @Composable
  fun td(modifier: Modifier = Modifier, content: @Composable () -> Unit) =
    cell(isRowHeader = false, modifier = modifier, content = content)

  /**
   * Adds a row header cell (`<th scope="row">`) with text.
   */
  @Composable
  fun th(text: String, modifier: Modifier = Modifier) =
    cell(text = text, isRowHeader = true, modifier = modifier)

  /**
   * Adds a row header cell (`<th scope="row">`) with arbitrary composable content.
   */
  @Composable
  fun th(modifier: Modifier = Modifier, content: @Composable () -> Unit) =
    cell(isRowHeader = true, modifier = modifier, content = content)

  /**
   * Adds a checkbox cell.
   */
  @Composable
  fun checkbox(
    checked: Boolean = false,
    label: String? = null,
    modifier: Modifier = Modifier,
    onCheckedChange: ((Boolean) -> Unit)? = null,
  )

  /**
   * Adds an avatar cell with name.
   */
  @Composable
  fun avatar(
    avatar: Avatar,
    name: String,
    description: String? = null,
    modifier: Modifier = Modifier,
  )

  /**
   * Adds a badge cell.
   */
  @Composable
  fun badge(
    badge: Badge,
    modifier: Modifier = Modifier,
  )

  /**
   * Adds a tags cell.
   */
  @Composable
  fun tags(
    tags: List<String>,
    modifier: Modifier = Modifier,
  )
}

internal data object DefaultTableScope : TableScope {
  @Composable
  override fun header(
    sticky: Boolean,
    modifier: Modifier,
    content: @Composable TableHeaderScope.() -> Unit,
  ) {
    val stickyModifier = if (sticky) TableCss.STICKY_TOP else Modifier
    KThead(modifier = stickyModifier + modifier) {
      val header = DefaultTableHeaderScope()
      header.content()
      header.render()
    }
  }

  @Composable
  override fun body(
    modifier: Modifier,
    content: @Composable TableBodyScope.() -> Unit,
  ) {
    KTbody(modifier = modifier) {
      DefaultTableBodyScope.content()
    }
  }

  @Composable
  override fun footer(
    modifier: Modifier,
    content: @Composable TableFooterScope.() -> Unit,
  ) {
    KTfoot(modifier = modifier) {
      DefaultTableFooterScope.content()
    }
  }

  @Composable
  override fun row(
    variant: TableRowVariant?,
    active: Boolean,
    modifier: Modifier,
    content: @Composable TableRowScope.() -> Unit,
  ) {
    val variantModifier = variant?.modifier ?: Modifier
    val activeModifier = if (active) TableCss.TABLE_ACTIVE else Modifier
    KTr(modifier = variantModifier + activeModifier + modifier) {
      DefaultTableRowScope.content()
    }
  }
}

internal class DefaultTableHeaderScope : TableHeaderScope {
  private val rows = mutableListOf<@Composable () -> Unit>()
  private var cells: MutableList<@Composable () -> Unit>? = null

  private fun addCell(content: @Composable () -> Unit) {
    val currentCells = cells ?: mutableListOf<@Composable () -> Unit>().also { newCells ->
      cells = newCells
      rows += { KTr { newCells.forEach { it() } } }
    }
    currentCells += content
  }

  @Composable
  override fun cell(text: String, noWrap: Boolean, modifier: Modifier) {
    val noWrapModifier = if (noWrap) TableCss.TEXT_NOWRAP else Modifier
    addCell {
      KTh(modifier = Modifier.attr("scope", "col") + noWrapModifier + modifier) {
        KText(text)
      }
    }
  }

  @Composable
  override fun cell(noWrap: Boolean, modifier: Modifier, content: @Composable () -> Unit) {
    val noWrapModifier = if (noWrap) TableCss.TEXT_NOWRAP else Modifier
    addCell {
      KTh(modifier = Modifier.attr("scope", "col") + noWrapModifier + modifier) {
        content()
      }
    }
  }

  @Composable
  override fun row(modifier: Modifier, content: @Composable TableRowScope.() -> Unit) {
    cells = null
    rows += { KTr(modifier = modifier) { DefaultTableRowScope.content() } }
  }

  @Composable
  fun render() {
    rows.forEach { it() }
  }
}

internal data object DefaultTableBodyScope : TableBodyScope {
  @Composable
  override fun row(
    variant: TableRowVariant?,
    active: Boolean,
    modifier: Modifier,
    content: @Composable TableRowScope.() -> Unit,
  ) {
    val variantModifier = variant?.modifier ?: Modifier
    val activeModifier = if (active) TableCss.TABLE_ACTIVE else Modifier
    KTr(modifier = variantModifier + activeModifier + modifier) {
      DefaultTableRowScope.content()
    }
  }
}

internal data object DefaultTableFooterScope : TableFooterScope {
  @Composable
  override fun cell(text: String, modifier: Modifier) {
    KTd(modifier = modifier) {
      KText(text)
    }
  }

  @Composable
  override fun cell(modifier: Modifier, content: @Composable () -> Unit) {
    KTd(modifier = modifier) {
      content()
    }
  }

  @Composable
  override fun row(modifier: Modifier, content: @Composable TableRowScope.() -> Unit) {
    KTr(modifier = modifier) {
      DefaultTableRowScope.content()
    }
  }
}

internal data object DefaultTableRowScope : TableRowScope {
  @Composable
  override fun cell(
    text: String,
    muted: Boolean,
    isRowHeader: Boolean,
    modifier: Modifier,
  ) {
    val mutedModifier = if (muted) TableCss.TEXT_SECONDARY else Modifier
    if (isRowHeader) {
      KTh(modifier = Modifier.attr("scope", "row") + mutedModifier + modifier) {
        KText(text)
      }
    } else {
      KTd(modifier = mutedModifier + modifier) {
        KText(text)
      }
    }
  }

  @Composable
  override fun cell(
    isRowHeader: Boolean,
    modifier: Modifier,
    content: @Composable () -> Unit,
  ) {
    if (isRowHeader) {
      KTh(modifier = Modifier.attr("scope", "row") + modifier) {
        content()
      }
    } else {
      KTd(modifier = modifier) {
        content()
      }
    }
  }

  @Composable
  override fun checkbox(
    checked: Boolean,
    label: String?,
    modifier: Modifier,
    onCheckedChange: ((Boolean) -> Unit)?,
  ) {
    KTd(modifier = modifier) {
      KLabel(modifier = cssClass("form-check")) {
        KInput(
          type = InputType.Checkbox,
          modifier = cssClass("form-check-input").then(
            if (checked) Modifier.attr("checked", "") else Modifier,
          ),
        )
        if (label != null) {
          KSpan(modifier = cssClass("form-check-label")) {
            KText(label)
          }
        }
      }
    }
  }

  @Composable
  override fun avatar(
    avatar: Avatar,
    name: String,
    description: String?,
    modifier: Modifier,
  ) {
    KTd(modifier = modifier) {
      KDiv(modifier = cssClass("d-flex") + cssClass("py-1") + cssClass("align-items-center")) {
        avatar.invoke(Modifier)
        KDiv(modifier = cssClass("flex-fill") + cssClass("ms-2")) {
          KDiv(modifier = cssClass("font-weight-medium")) {
            KText(name)
          }
          if (description != null) {
            KDiv(modifier = cssClass("text-secondary")) {
              KText(description)
            }
          }
        }
      }
    }
  }

  @Composable
  override fun badge(badge: Badge, modifier: Modifier) {
    KTd(modifier = modifier) {
      badge.invoke(Modifier)
    }
  }

  @Composable
  override fun tags(tags: List<String>, modifier: Modifier) {
    KTd(modifier = modifier) {
      tags.forEach { tag ->
        KSpan(modifier = cssClass("badge") + cssClass("bg-azure-lt") + cssClass("me-1")) {
          KText(tag)
        }
      }
    }
  }
}
