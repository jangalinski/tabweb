package com.github.jangalinski.tabweb.table

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.doesNotContain
import assertk.assertions.isEqualTo
import com.github.jangalinski.tabweb.Tabweb.table
import com.github.jangalinski.tabweb._foundation.Initials
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb.avatar.Avatar
import com.github.jangalinski.tabweb.badge.Badge
import org.jetbrains.compose.web.testutils.ComposeWebExperimentalTestsApi
import org.jetbrains.compose.web.testutils.runTest
import kotlin.test.Test

@OptIn(ComposeWebExperimentalTestsApi::class)
class TableTest {

  @Test
  fun rendersTableDslWithHeaderBodyAndRows() = runTest {
    composition {
      table(
        responsive = TableResponsive.ALWAYS,
        hover = true,
        striped = true,
      ) {
        header {
          cell("Name")
          cell("Status")
          cell("Role")
        }
        body {
          row {
            cell("Paweł Kuna", muted = false, isRowHeader = true)
            cell {
              Badge(text = "Active", color = BackgroundColor.SEMANTIC.SUCCESS)()
            }
            cell("UI Designer")
          }
          row(variant = TableRowVariant.WARNING) {
            cell("Jane Doe")
            cell("Pending")
            cell("Developer")
          }
        }
      }
    }

    val html = root.innerHTML

    assertThat(html).contains("table-responsive")
    assertThat(html).contains("table")
    assertThat(html).contains("table-hover")
    assertThat(html).contains("table-striped")
    assertThat(html).contains("<thead")
    assertThat(html).contains("scope=\"col\"")
    assertThat(root.querySelectorAll("thead > tr > th[scope=col]").length).isEqualTo(3)
    assertThat(html).contains("Name")
    assertThat(html).contains("Status")
    assertThat(html).contains("Role")
    assertThat(html).contains("<tbody")
    assertThat(html).contains("scope=\"row\"")
    assertThat(html).contains("Paweł Kuna")
    assertThat(html).contains("Active")
    assertThat(html).contains("badge")
    assertThat(html).contains("bg-success")
    assertThat(html).contains("table-warning")
    assertThat(html).contains("Jane Doe")
  }

  @Test
  fun rendersExplicitHeaderRowsWithoutNestingThem() = runTest {
    composition {
      table {
        header {
          row {
            cell("Grouped", isRowHeader = true)
          }
          cell("Name")
          cell("Role")
        }
      }
    }

    assertThat(root.querySelectorAll("thead > tr").length).isEqualTo(2)
    assertThat(root.querySelectorAll("thead > tr:first-child > th").length).isEqualTo(1)
    assertThat(root.querySelectorAll("thead > tr:last-child > th[scope=col]").length).isEqualTo(2)
  }

  @Test
  fun rendersTableDataWithAllSealedCellTypes() = runTest {
    val tableData = TablerTableData(
      columns = listOf(
        TablerTableColumn("User"),
        TablerTableColumn("Status"),
        TablerTableColumn("Tags"),
        TablerTableColumn("Select"),
      ),
      rows = listOf(
        TablerTableRow(
          cells = listOf(
            TablerTableCell.AvatarName(
              avatar = Avatar(Initials("PK")),
              name = "Paweł Kuna",
              description = "pawel@example.com",
            ),
            TablerTableCell.Badge("Active", "bg-success"),
            TablerTableCell.Tags(listOf("admin", "staff")),
            TablerTableCell.Checkbox(checked = true, label = "Selected"),
          ),
        ),
      ),
    )

    composition {
      TablerTable(data = tableData)
    }

    val html = root.innerHTML

    assertThat(html).contains("table-responsive")
    assertThat(html).contains("table")
    assertThat(html).contains("User")
    assertThat(html).contains("avatar")
    assertThat(html).contains("PK")
    assertThat(html).contains("Paweł Kuna")
    assertThat(html).contains("pawel@example.com")
    assertThat(html).contains("badge")
    assertThat(html).contains("bg-success")
    assertThat(html).contains("Active")
    assertThat(html).contains("bg-azure-lt")
    assertThat(html).contains("admin")
    assertThat(html).contains("staff")
    assertThat(html).contains("form-check-input")
    assertThat(html).contains("checked")
    assertThat(html).contains("Selected")
  }

  @Test
  fun rendersTablerTableCardWithoutCardBody() = runTest {
    val tableData = TablerTableData(
      columns = listOf(
        TablerTableColumn("Name"),
        TablerTableColumn("Role"),
      ),
      rows = listOf(
        TablerTableRow(
          cells = listOf(
            TablerTableCell.Text("Alice"),
            TablerTableCell.Text("Engineer", muted = true),
          ),
        ),
      ),
    )

    val pagination = TablerPaginationData(
      currentPage = 1,
      totalPages = 5,
      pageSize = 10,
      totalItems = 50,
    )

    composition {
      TablerTableCard(
        title = "Team Members",
        subtitle = "Current active staff",
        data = tableData,
        pagination = pagination,
      )
    }

    val html = root.innerHTML

    assertThat(html).contains("card")
    assertThat(html).contains("card-header")
    assertThat(html).contains("Team Members")
    assertThat(html).contains("Current active staff")
    assertThat(root.querySelectorAll(".card-header > div > h2.card-title + p.card-subtitle").length).isEqualTo(1)
    assertThat(html).contains("card-table")
    assertThat(root.querySelectorAll(".card-table thead > tr > th:first-child").length).isEqualTo(1)
    assertThat(html).doesNotContain("card-body")
    assertThat(html).contains("Alice")
    assertThat(html).contains("text-secondary")
    assertThat(html).contains("Engineer")
    assertThat(html).contains("card-footer")
    assertThat(html).contains("Showing 1 to 10 of 50 entries")
    assertThat(html).contains("pagination")
  }

  @Test
  fun rendersEmbeddedComposablesInDslCells() = runTest {
    composition {
      table {
        header {
          cell("User")
          cell("Actions")
        }
        body {
          row {
            cell {
              Avatar(Initials("JD"), color = BackgroundColor.SEMANTIC.INFO)()
            }
            cell {
              KText("Custom Action Button")
            }
          }
        }
      }
    }

    val html = root.innerHTML

    assertThat(html).contains("avatar")
    assertThat(html).contains("JD")
    assertThat(html).contains("bg-info")
    assertThat(html).contains("Custom Action Button")
  }
}
