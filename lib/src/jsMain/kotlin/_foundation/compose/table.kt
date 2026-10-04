package com.github.jangalinski.tabweb._foundation.compose

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.dom.*

/**
 * Internal DOM adapter for a table with Kobweb modifier support.
 *
 * @param modifier attributes, classes, and styles applied to the table.
 * @param content composable table content.
 */
@Composable
fun KTable(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
  Table(attrs = modifier.toAttrs()) { content() }
}

/**
 * Internal DOM adapter for a table header section with Kobweb modifier support.
 *
 * @param modifier attributes, classes, and styles applied to the table header.
 * @param content composable table-header content.
 */
@Composable
fun KThead(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
  Thead(attrs = modifier.toAttrs()) { content() }
}

/**
 * Internal DOM adapter for a table body section with Kobweb modifier support.
 *
 * @param modifier attributes, classes, and styles applied to the table body.
 * @param content composable table-body content.
 */
@Composable
fun KTbody(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
  Tbody(attrs = modifier.toAttrs()) { content() }
}

/**
 * Internal DOM adapter for a table footer section with Kobweb modifier support.
 *
 * @param modifier attributes, classes, and styles applied to the table footer.
 * @param content composable table-footer content.
 */
@Composable
fun KTfoot(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
  Tfoot(attrs = modifier.toAttrs()) { content() }
}

/**
 * Internal DOM adapter for a table row with Kobweb modifier support.
 *
 * @param modifier attributes, classes, and styles applied to the row.
 * @param content composable table-row content.
 */
@Composable
fun KTr(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
  Tr(attrs = modifier.toAttrs()) { content() }
}

/**
 * Internal DOM adapter for a table header cell with Kobweb modifier support.
 *
 * @param modifier attributes, classes, and styles applied to the header cell.
 * @param content composable table-header-cell content.
 */
@Composable
fun KTh(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
  Th(attrs = modifier.toAttrs()) { content() }
}

/**
 * Internal DOM adapter for a table data cell with Kobweb modifier support.
 *
 * @param modifier attributes, classes, and styles applied to the data cell.
 * @param content composable table-data-cell content.
 */
@Composable
fun KTd(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
  Td(attrs = modifier.toAttrs()) { content() }
}
