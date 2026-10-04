package com.github.jangalinski.tabweb.site.pages

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb.Tabweb.button
import com.github.jangalinski.tabweb.Tabweb.cardRow
import com.github.jangalinski.tabweb.Tabweb.table
import com.github.jangalinski.tabweb._foundation.Initials
import com.github.jangalinski.tabweb._foundation.compose.KAnchor
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.compose.KInput
import com.github.jangalinski.tabweb._foundation.compose.KSpan
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.github.jangalinski.tabweb._foundation.css.plus
import com.github.jangalinski.tabweb._foundation.css.GridWidth
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb.avatar.Avatar
import com.github.jangalinski.tabweb.badge.Badge
import com.github.jangalinski.tabweb.button.ButtonColor
import com.github.jangalinski.tabweb.button.ButtonStyle
import com.github.jangalinski.tabweb.site.SiteRoutes
import com.github.jangalinski.tabweb.site.siteLayoutData
import com.github.jangalinski.tabweb.site.sitePageMeta
import com.github.jangalinski.tabweb.table.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.attr
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import org.jetbrains.compose.web.attributes.InputType

private data class InvoiceRow(
  val number: String,
  val subject: String,
  val client: String,
  val vatNumber: String,
  val created: String,
  val status: String,
  val price: String,
)

@InitRoute
fun initTablesPage(ctx: InitRouteContext) {
  ctx.data.add(sitePageMeta("Tables", "Tables display information in a grid-like format of rows and columns."))
  ctx.data.add(siteLayoutData(SiteRoutes.Tables))
}

@Page(routeOverride = SiteRoutes.Tables)
@Composable
fun TablesPage() {
  val sampleRows = rememberPaginatedTableRows(
    rows = listOf(
      TablerTableRow(
        listOf(
          TablerTableCell.AvatarName(Avatar(Initials("PK")), "Paweł Kuna", "UI Designer"),
          TablerTableCell.Text("Operations"),
          TablerTableCell.Badge("Active", "bg-success"),
          TablerTableCell.Text("pawel@example.com", muted = true),
        ),
      ),
      TablerTableRow(
        listOf(
          TablerTableCell.AvatarName(Avatar(Initials("ML")), "Mallory Lu", "Frontend Developer"),
          TablerTableCell.Text("Engineering"),
          TablerTableCell.Badge("Active", "bg-success"),
          TablerTableCell.Text("mallory@example.com", muted = true),
        ),
      ),
      TablerTableRow(
        listOf(
          TablerTableCell.AvatarName(Avatar(Initials("DK")), "Dunn Kearney", "Backend Engineer"),
          TablerTableCell.Text("Engineering"),
          TablerTableCell.Badge("Pending", "bg-warning"),
          TablerTableCell.Text("dunn@example.com", muted = true),
        ),
      ),
      TablerTableRow(
        listOf(
          TablerTableCell.AvatarName(Avatar(Initials("ER")), "Emmy Levet", "Product Manager"),
          TablerTableCell.Text("Management"),
          TablerTableCell.Badge("Active", "bg-success"),
          TablerTableCell.Text("emmy@example.com", muted = true),
        ),
      ),
      TablerTableRow(
        listOf(
          TablerTableCell.AvatarName(Avatar(Initials("MR")), "Maryjo Reisen", "Office Manager"),
          TablerTableCell.Text("Operations"),
          TablerTableCell.Badge("Inactive", "bg-secondary"),
          TablerTableCell.Text("maryjo@example.com", muted = true),
        ),
      ),
      TablerTableRow(
        listOf(
          TablerTableCell.AvatarName(Avatar(Initials("JL")), "Jeffie Lewzey", "Chemical Engineer"),
          TablerTableCell.Text("Support"),
          TablerTableCell.Badge("Active", "bg-success"),
          TablerTableCell.Text("jeffie@example.com", muted = true),
        ),
      ),
    ),
    pageSize = 3,
  )

  cardRow {
    // 1. Basic table
    card(width = GridWidth.LG_TWO_THIRDS) {
      header(
        title = "Basic table",
        subtitle = "A clean list of rows and columns, ready to drop into any card.",
      )
      table(responsive = TableResponsive.ALWAYS, vcenter = true, cardTable = true) {
        header {
          cell("Name")
          cell("Title")
          cell("Email")
          cell("Role")
          cell("", modifier = cssClass("w-1"))
        }
        body {
          row {
            cell("Paweł Kuna", isRowHeader = true)
            cell("UI Designer, Training", muted = true)
            cell {
              KAnchor(href = "#", modifier = cssClass("text-reset")) {
                KText("pawel@example.com")
              }
            }
            cell("User", muted = true)
            cell {
              KAnchor(href = "#") { KText("Edit") }
            }
          }
          row {
            cell("Jeffie Lewzey", isRowHeader = true)
            cell("Chemical Engineer, Support", muted = true)
            cell {
              KAnchor(href = "#", modifier = cssClass("text-reset")) {
                KText("jeffie@example.com")
              }
            }
            cell("Admin", muted = true)
            cell {
              KAnchor(href = "#") { KText("Edit") }
            }
          }
          row {
            cell("Mallory Lu", isRowHeader = true)
            cell("Software Engineer, Dev", muted = true)
            cell {
              KAnchor(href = "#", modifier = cssClass("text-reset")) {
                KText("mallory@example.com")
              }
            }
            cell("User", muted = true)
            cell {
              KAnchor(href = "#") { KText("Edit") }
            }
          }
          row {
            cell("Dunn Kearney", isRowHeader = true)
            cell("SRE, Infrastructure", muted = true)
            cell {
              KAnchor(href = "#", modifier = cssClass("text-reset")) {
                KText("dunn@example.com")
              }
            }
            cell("Owner", muted = true)
            cell {
              KAnchor(href = "#") { KText("Edit") }
            }
          }
          row {
            cell("Emmy Levet", isRowHeader = true)
            cell("VP Accounting, Finance", muted = true)
            cell {
              KAnchor(href = "#", modifier = cssClass("text-reset")) {
                KText("emmy@example.com")
              }
            }
            cell("User", muted = true)
            cell {
              KAnchor(href = "#") { KText("Edit") }
            }
          }
        }
      }
    }

    // 2. Top Pages
    card(width = GridWidth.THIRD) {
      body {
        title("Top Pages")
        table(sm = true, borderless = true) {
          header {
            cell("Page")
            cell("Visitors", modifier = cssClass("text-end"))
          }
          body {
            val topPages = listOf(
              Triple("/", "82.54%", "4,896"),
              Triple("/form-elements.html", "76.29%", "3,652"),
              Triple("/index.html", "72.65%", "3,256"),
              Triple("/icons.html", "44.89%", "986"),
              Triple("/docs/", "41.12%", "912"),
              Triple("/accordion.html", "32.65%", "855"),
              Triple("/datagrid.html", "16.22%", "764"),
              Triple("/datatables.html", "8.69%", "686"),
            )
            topPages.forEach { (page, width, visitors) ->
              row {
                cell {
                  KDiv(modifier = cssClass("progressbg")) {
                    KDiv(modifier = cssClass("progress") + cssClass("progressbg-progress")) {
                      KDiv(
                        modifier = cssClass("progress-bar") + cssClass("bg-primary-lt") +
                          Modifier
                            .attr("style", "width: $width")
                            .attr("role", "progressbar")
                            .attr("aria-valuenow", width.removeSuffix("%"))
                            .attr("aria-valuemin", "0")
                            .attr("aria-valuemax", "100")
                            .attr("aria-label", "$width Complete"),
                      ) {
                        KSpan(modifier = cssClass("visually-hidden")) {
                          KText("$width Complete")
                        }
                      }
                    }
                    KDiv(modifier = cssClass("progressbg-text")) {
                      KText(page)
                    }
                  }
                }
                cell(modifier = cssClass("w-1") + cssClass("fw-medium") + cssClass("text-end")) {
                  KText(visitors)
                }
              }
            }
          }
        }
      }
    }

    // 3. Striped rows
    card(width = GridWidth.FULL) {
      header(
        title = "Striped rows",
        subtitle = "Alternate row shading with table-striped to make wide tables easier to scan.",
      )
      table(
        responsive = TableResponsive.ALWAYS,
        striped = true,
        hover = true,
        vcenter = true,
        cardTable = true,
      ) {
        header {
          cell("Name")
          cell("Title")
          cell("Email")
          cell("Role")
          cell("", modifier = cssClass("w-1"))
        }
        body {
          val employees = listOf(
            listOf("Maryjo Lebarree", "Civil Engineer, Product Management", "maryjo@example.com", "User"),
            listOf("Egan Poetz", "Research Nurse, Engineering", "egan@example.com", "Admin"),
            listOf("Kellie Marquardt", "Financial Analyst, Accounting", "kellie@example.com", "Member"),
            listOf("Paweł Kuna", "UI Designer, Training", "pawel@example.com", "User"),
            listOf("Jeffie Lewzey", "Chemical Engineer, Support", "jeffie@example.com", "User"),
            listOf("Dunn Kearney", "DevOps, Operations", "dunn@example.com", "Owner"),
          )
          employees.forEach { (name, title, email, role) ->
            row {
              cell(name, isRowHeader = true)
              cell(title, muted = true)
              cell {
                KAnchor(href = "#", modifier = cssClass("text-reset")) {
                  KText(email)
                }
              }
              cell(role, muted = true)
              cell {
                KAnchor(href = "#") { KText("Edit") }
              }
            }
          }
        }
      }
    }

    // 4. With avatars
    card(width = GridWidth.FULL) {
      header(
        title = "With avatars",
        subtitle = "Swap the plain name column for an avatar, title, and department.",
      )
      table(
        responsive = TableResponsive.ALWAYS,
        hover = true,
        vcenter = true,
        cardTable = true,
      ) {
        header {
          cell("Name")
          cell("Title")
          cell("Role")
          cell("", modifier = cssClass("w-1"))
        }
        body {
          row {
            avatar(
              avatar = Avatar(Initials("LM"), color = BackgroundColor.SEMANTIC.PRIMARY),
              name = "Lorry Mion",
              description = "lorry@example.com",
            )
            cell("Automation Specialist IV, Accounting", muted = true)
            cell("User", muted = true)
            cell {
              KAnchor(href = "#") { KText("Edit") }
            }
          }
          row {
            avatar(
              avatar = Avatar(Initials("LB"), color = BackgroundColor.SEMANTIC.SUCCESS),
              name = "Leesa Beaty",
              description = "leesa@example.com",
            )
            cell("Editor, Services", muted = true)
            cell("Admin", muted = true)
            cell {
              KAnchor(href = "#") { KText("Edit") }
            }
          }
          row {
            avatar(
              avatar = Avatar(Initials("PP"), color = BackgroundColor.SEMANTIC.WARNING),
              name = "Perla Pannell",
              description = "perla@example.com",
            )
            cell("Software Consultant, Support", muted = true)
            cell("User", muted = true)
            cell {
              KAnchor(href = "#") { KText("Edit") }
            }
          }
          row {
            avatar(
              avatar = Avatar(Initials("PK"), color = BackgroundColor.SEMANTIC.INFO),
              name = "Paweł Kuna",
              description = "pawel@example.com",
            )
            cell("UI Designer, Training", muted = true)
            cell("User", muted = true)
            cell {
              KAnchor(href = "#") { KText("Edit") }
            }
          }
          row {
            avatar(
              avatar = Avatar(Initials("ML"), color = BackgroundColor.SEMANTIC.DANGER),
              name = "Mallory Lu",
              description = "mallory@example.com",
            )
            cell("Software Engineer, Dev", muted = true)
            cell("Admin", muted = true)
            cell {
              KAnchor(href = "#") { KText("Edit") }
            }
          }
          row {
            avatar(
              avatar = Avatar(Initials("DK"), color = BackgroundColor.BASE.PURPLE),
              name = "Dunn Kearney",
              description = "dunn@example.com",
            )
            cell("Site Reliability, DevOps", muted = true)
            cell("Owner", muted = true)
            cell {
              KAnchor(href = "#") { KText("Edit") }
            }
          }
        }
      }
    }

    // 5. Responsive with actions
    card(width = GridWidth.FULL) {
      header(
        title = "Responsive with actions",
        subtitle = "Collapses to stacked rows on mobile and adds an actions button per row.",
      )
      table(
        responsive = TableResponsive.ALWAYS,
        hover = true,
        vcenter = true,
        cardTable = true,
        modifier = cssClass("table-mobile-md"),
      ) {
        header {
          cell("Name")
          cell("Title")
          cell("Role")
          cell("", modifier = cssClass("w-1"))
        }
        body {
          row {
            avatar(
              avatar = Avatar(Initials("LM"), color = BackgroundColor.SEMANTIC.PRIMARY),
              name = "Lorry Mion",
              description = "lorry@example.com",
            )
            cell("Automation Specialist IV, Accounting", muted = true)
            cell("User", muted = true)
            cell {
              button(text = "Action", style = ButtonStyle.OUTLINE, color = ButtonColor.DEFAULT)
            }
          }
          row {
            avatar(
              avatar = Avatar(Initials("LB"), color = BackgroundColor.SEMANTIC.SUCCESS),
              name = "Leesa Beaty",
              description = "leesa@example.com",
            )
            cell("Editor, Services", muted = true)
            cell("Admin", muted = true)
            cell {
              button(text = "Action", style = ButtonStyle.OUTLINE, color = ButtonColor.DEFAULT)
            }
          }
          row {
            avatar(
              avatar = Avatar(Initials("PP"), color = BackgroundColor.SEMANTIC.WARNING),
              name = "Perla Pannell",
              description = "perla@example.com",
            )
            cell("Software Consultant, Support", muted = true)
            cell("User", muted = true)
            cell {
              button(text = "Action", style = ButtonStyle.OUTLINE, color = ButtonColor.DEFAULT)
            }
          }
          row {
            avatar(
              avatar = Avatar(Initials("PK"), color = BackgroundColor.SEMANTIC.INFO),
              name = "Paweł Kuna",
              description = "pawel@example.com",
            )
            cell("UI Designer, Training", muted = true)
            cell("User", muted = true)
            cell {
              button(text = "Action", style = ButtonStyle.OUTLINE, color = ButtonColor.DEFAULT)
            }
          }
        }
      }
    }

    // 6. Invoices (Data table with selectable rows)
    card(width = GridWidth.FULL) {
      header(title = "Invoices")
      table(
        responsive = TableResponsive.ALWAYS,
        selectable = true,
        hover = true,
        vcenter = true,
        cardTable = true,
        noWrap = true,
      ) {
        header {
          cell(modifier = cssClass("w-1")) {
            KInput(type = InputType.Checkbox, modifier = cssClass("form-check-input"))
          }
          cell("No.")
          cell("Invoice Subject")
          cell("Client")
          cell("VAT No.")
          cell("Created")
          cell("Status")
          cell("Price")
          cell("", modifier = cssClass("w-1"))
        }
        body {
          val invoices = listOf(
            InvoiceRow("001401", "Design Works", "Carlson Limited", "87956621", "15 Dec 2024", "Paid", "$887"),
            InvoiceRow("001402", "UX Wireframes", "Adobe", "87956421", "12 Apr 2024", "Pending", "$1,200"),
            InvoiceRow("001403", "New Dashboard", "Bluewolf", "87952621", "23 Oct 2024", "Pending", "$534"),
            InvoiceRow("001404", "Landing Page", "Salesforce", "87953421", "2 Sep 2024", "Paid", "$1,500"),
            InvoiceRow("001405", "Marketing Site", "Printic", "87956621", "29 Jan 2024", "Paid", "$890"),
            InvoiceRow("001406", "Sales App", "Tabler", "87956621", "4 Feb 2024", "Failed", "$2,400"),
            InvoiceRow("001407", "Brand Guidelines", "Apple", "87956621", "22 Mar 2024", "Paid", "$3,100"),
            InvoiceRow("001408", "Mobile App", "Google", "87956621", "18 May 2024", "Paid", "$5,400"),
          )
          invoices.forEach { invoice ->
            row {
              cell {
                KInput(type = InputType.Checkbox, modifier = cssClass("form-check-input"))
              }
              cell(invoice.number, isRowHeader = true)
              cell(invoice.subject)
              cell(invoice.client, muted = true)
              cell(invoice.vatNumber, muted = true)
              cell(invoice.created, muted = true)
              cell {
                Badge(
                  text = invoice.status,
                  color = when (invoice.status) {
                    "Paid" -> BackgroundColor.SEMANTIC.SUCCESS
                    "Pending" -> BackgroundColor.SEMANTIC.WARNING
                    else -> BackgroundColor.SEMANTIC.DANGER
                  },
                )()
              }
              cell(invoice.price)
              cell {
                KAnchor(href = "#") { KText("Edit") }
              }
            }
          }
        }
      }
    }

    // 7. Paginated Table Card
    col(width = GridWidth.FULL) {
      TablerTableCard(
        title = "Employees",
        subtitle = "Paginated table card with responsive layout and status tags",
        columns = listOf(
          TablerTableColumn("Name"),
          TablerTableColumn("Department"),
          TablerTableColumn("Status"),
          TablerTableColumn("Email"),
        ),
        rows = sampleRows,
      )
    }
  }
}
