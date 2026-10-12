package com.github.jangalinski.tabweb._foundation.css

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames

/**
 * Keeps static string names of Tabweb classes.
 */
data object ClassNames {

  /**
   * Converts a documented Tabweb class-name constant into a Kobweb [Modifier].
   *
   * Compound constants are split on whitespace so a single documented constant can still
   * represent a bundled Tabweb class set.
   */
  fun String.modifier(): Modifier = Modifier.classNames(*trim().split(Regex("\\s+")).toTypedArray())

  /** Root page container used by Tabweb layouts. */
  const val page = "page"

  /** Wrapper that anchors page header/body sections inside the Tabweb page shell. */
  const val pageWrapper = "page-wrapper"

  /** Main content area inside the page wrapper. */
  const val pageBody = "page-body"

  /** Responsive top navigation bar with print suppression. */
  const val navbar = "navbar navbar-expand-md d-print-none"

  /** Horizontal page navbar used for the dashboard header layout. */
  const val navbarHeader = "navbar navbar-expand-md d-print-none"

  /** Vertical page navbar used for the sidebar layout. */
  const val navbarVertical = "navbar navbar-vertical navbar-expand-lg"

  /** Breakpoint behavior that expands the navbar at medium widths and up. */
  const val navbarExpandMd = "navbar-expand-md"

  /** Breakpoint behavior that expands the navbar at small widths and up. */
  const val navbarExpandSm = "navbar-expand-sm"

  /** Suppresses a section when printing. */
  const val dPrintNone = "d-print-none"

  /** Light surface variant used by the horizontal header navbar. */
  const val navbarLight = "navbar-light"

  /** Positions the sidebar navbar absolutely over the page shell. */
  const val positionAbsolute = "position-absolute"

  /** Positions a menu relative to its containing anchor. */
  const val positionRelative = "position-relative"

  /** Standard wide content container used across the layout. */
  const val containerXl = "container-xl"

  /** Standard fluid content container used by the sidebar navbar shell. */
  const val containerFluid = "container-fluid"

  /** Navbar brand area with Tabweb autolight treatment and responsive padding. */
  const val navbarBrand = "navbar-brand navbar-brand-autodark pe-0 pe-md-3"

  /** Header navbar brand area with horizontal layout hiding. */
  const val navbarBrandHeader = "navbar-brand navbar-brand-autodark d-none-navbar-horizontal pe-0 pe-md-3"

  /** Sidebar navbar brand area without the horizontal-only padding. */
  const val navbarBrandSidebar = "navbar-brand navbar-brand-autodark"

  /** Image element used in a navbar brand logo. */
  const val navbarBrandImage = "navbar-brand-image"

  /** Automatic dark/light treatment for the navbar brand logo. */
  const val navbarBrandAutodark = "navbar-brand-autodark"

  /** Hides the brand area on horizontal layouts. */
  const val dNoneNavbarHorizontal = "d-none-navbar-horizontal"

  /** Removes right padding on compact layouts. */
  const val pe0 = "pe-0"

  /** Restores right padding on medium and larger layouts. */
  const val peMd3 = "pe-md-3"

  /** Horizontal navbar list aligned to the right on desktop. */
  const val navbarNav = "navbar-nav flex-row order-md-last"

  /** Plain navigation list used by the primary top-navbar menu. */
  const val navbarNavPrimary = "navbar-nav"

  /** Navbar list in the header layout. */
  const val navbarNavHeader = "navbar-nav flex-row"

  /** Vertical navbar list used in the sidebar layout. */
  const val navbarNavSidebar = "navbar-nav pt-lg-3"

  /** Forces navbar items into a horizontal row. */
  const val flexRow = "flex-row"

  /** Lets a flex item consume the remaining horizontal space. */
  const val flexGrow1 = "flex-grow-1"

  /** Aligns navbar items at the end on medium and larger screens. */
  const val orderMdLast = "order-md-last"

  /** Small left margin used to separate the brand from the nav list. */
  const val ms3 = "ms-3"

  /** Pushes a flex item to the far right. */
  const val msAuto = "ms-auto"

  /** Keeps a link's text color inherited from its parent. */
  const val textReset = "text-reset"

  /** Removes underline decoration from a link. */
  const val textDecorationNone = "text-decoration-none"

  /** Navbar item used for the generated-at timestamp. */
  const val navbarGeneratedAt = "nav-item d-flex align-items-center text-secondary"

  /** Inner navigation item row used by the header and sidebar shells. */
  const val navItem = "nav-item"

  /** Active navbar item state. */
  const val navItemActive = "active"

  /** Dropdown navigation item wrapper. */
  const val navItemDropdown = "nav-item dropdown"

  /** Link styling used for navigation entries. */
  const val navLink = "nav-link"

  /** Dropdown toggle styling used for navigation entries. */
  const val navLinkDropdownToggle = "nav-link dropdown-toggle"

  /** Split dropdown toggle button styling used next to a clickable dropdown label. */
  const val navLinkDropdownToggleButton = "nav-link dropdown-toggle dropdown-toggle-split px-1"

  /** Utility class that hides text visually while keeping it available to screen readers. */
  const val visuallyHidden = "visually-hidden"

  /** Active link state used by navigation entries. */
  const val navLinkActive = "active"

  /** Title span inside a navigation link. */
  const val navLinkTitle = "nav-link-title"

  /** Enables flex layout for the generated-at timestamp row. */
  const val dFlex = "d-flex"

  /** Stacks flex children vertically. */
  const val dFlexColumn = "d-flex flex-column"

  /** Vertically centers flex children. */
  const val alignItemsCenter = "align-items-center"

  /** Small left margin for inline text next to an icon or label. */
  const val generatedAtText = "ms-1"

  /** Small left margin for inline text, e.g. subtitle next to a title. */
  const val ms2 = "ms-2"

  /** Small right margin for inline icons next to text. */
  const val me2 = "me-2"

  /** Wrapper used for sidebar menus. */
  const val navbarCollapse = "collapse navbar-collapse"

  /** Toggler button used by the sidebar layout. */
  const val navbarToggler = "navbar-toggler"

  /** Icon inside the sidebar toggler button. */
  const val navbarTogglerIcon = "navbar-toggler-icon"

  /** Header block shown above the main content. */
  const val pageHeader = "page-header d-print-none"

  /** Header row with compact spacing and vertical centering. */
  const val pageHeaderRow = "row g-2 align-items-center"

  /** Base row class used for Tabweb header layout. */
  const val row = "row"

  /** Compact spacing between items. */
  const val g2 = "g-2"

  /** Generic header column that expands to available width. */
  const val pageHeaderCol = "col"

  /** Large title style used in the page header. */
  const val pageTitle = "page-title"

  /** Secondary text that keeps bottom margin removed. */
  const val textSecondaryMb0 = "text-secondary mb-0"

  /** Shared secondary text tone. */
  const val textSecondary = "text-secondary"

  /** Subtitle style used under the page title. */
  const val subheader = "subheader"

  /** Card deck row with Tabweb spacing between cards. */
  const val rowCards = "row row-deck row-cards g-3 mb-4"

  /** Tabweb deck layout that keeps cards aligned and equal height. */
  const val rowDeck = "row-deck"

  /** Card grouping style for a row of cards. */
  const val rowCardsOnly = "row-cards"

  /** Horizontal spacing between grid items. */
  const val g3 = "g-3"

  /** Bottom spacing after the card grid. */
  const val mb4 = "mb-4"

  /** Standard Tabweb card container. */
  const val card = "card"

  /** Card container that stretches to full height. */
  const val cardH100 = "card h-100"

  /** Forces a card to stretch to full height. */
  const val h100 = "h-100"

  /** Card header section. */
  const val cardHeader = "card-header"

  /** Card title text style. */
  const val cardTitle = "card-title"

  /** Card body section. */
  const val cardBody = "card-body"

  /** Container for compact activity-monitoring blocks. */
  const val tracking = "tracking"

  /** Individual activity-monitoring block inside a tracking container. */
  const val trackingBlock = "tracking-block"

  /** Generic dropdown menu container. */
  const val dropdownMenu = "dropdown-menu"

  /** Dropdown menu with arrow styling. */
  const val dropdownMenuArrow = "dropdown-menu-arrow"

  /** Dropdown menu placement directly below its anchor. */
  const val dropdownMenuBelow = "position-absolute top-100 start-0 mt-2"

  /** Dropdown item link. */
  const val dropdownItem = "dropdown-item"

  /** Active dropdown item state. */
  const val dropdownItemActive = "active"

  /** Icon shown inside a dropdown item. */
  const val dropdownItemIcon = "dropdown-item-icon"

  /** Icon shown inside a top-level nav item. */
  const val navItemIcon = "nav-item-icon"

  /** Generic inline icon class. */
  const val icon = "icon"

  /** Base Tabweb avatar. */
  const val avatar = "avatar"
  const val avatarList = "avatar-list"
  const val avatarListStacked = "avatar-list-stacked"

  /**
   * Half-width on small screens and one-quarter width on large screens.
   *
   * Equivalent to `col-sm-6 col-lg-3`.
   */
  const val colSm6Lg3 = "col-sm-6 col-lg-3"

  /** Half-width on small screens. */
  const val colSm6 = "col-sm-6"

  /** One-quarter width on large screens. */
  const val colLg3 = "col-lg-3"

  /** One-third width column on large screens. */
  const val colLg4 = "col-lg-4"

  /** Two-thirds width column on large screens. */
  const val colLg8 = "col-lg-8"

  /** Full-width column. */
  const val col12 = "col-12"

  /** Heading-sized value with reduced bottom margin. */
  const val h1Mb2 = "h1 mb-2"

  /** Heading-sized text used for stat card values. */
  const val h1 = "h1"

  /** Removes the default bottom margin from the large stat value. */
  const val mb2 = "mb-2"

  /** Secondary subtitle text used under page headers. */
  const val textSecondarySubheader = "text-secondary subheader"

  /** Secondary text with zero margin. */
  const val textSecondaryM0 = "text-secondary m-0"

  /** Small padding used inside the horizontal header content area. */
  const val ps2 = "ps-2"

  /** Extra-large breakpoint visibility helper used by Tabweb layouts. */
  const val dNoneXlBlock = "d-none d-xl-block"

  /** Small text size. */
  const val small = "small"

  /** Removes all margins from supporting text. */
  const val m0 = "m-0"

  /** Smallest top margin used for inline subtitle spacing. */
  const val mt1 = "mt-1"

  /** Top margin used to separate card sections. */
  const val mt3 = "mt-3"

  /** Small top margin used to separate a tracking component from its summary. */
  const val mt2 = "mt-2"

  /** Larger top margin used around chart/table blocks. */
  const val mt4 = "mt-4"

  /** Vertical margin used for horizontal separators and notes. */
  const val my4 = "my-4"

  /** Compact progress bar container with top spacing. */
  const val progress = "progress progress-sm mt-3"

  /** Base progress bar element without variant color. */
  const val progressBar = "progress-bar"

  /** Flush list group used for stacked summary rows. */
  const val listGroup = "list-group list-group-flush"

  /** List group row styled as a single summary line. */
  const val listGroupItem = "list-group-item d-flex justify-content-between align-items-center px-0"

  /** Flex row for the left side of a list item, usually badge plus label. */
  const val listGroupRow = "d-flex align-items-center gap-2"

  /** Horizontal gap used between inline footer links. */
  const val gap3 = "gap-3"

  /** Semibold text for the list label. */
  const val fwSemibold = "fw-semibold"

  /** Base badge class. Use the overload for a contextual variant. */
  const val badge = "badge"

  /** Footer shell that keeps the page footer transparent and print-free. */
  const val footer = "footer footer-transparent d-print-none"

  /** Transparent footer surface. */
  const val footerTransparent = "footer-transparent"

  /** Danger alert shown when chart rendering fails. */
  const val alertDangerMb0 = "alert alert-danger mb-0"

  /** Small, muted helper text. */
  const val smallTextSecondary = "text-secondary small"

  /** Compact footer row that spreads left and right content across the full width. */
  const val footerRow = "d-flex align-items-center w-100"

  /** Footer left-side text block. */
  const val footerLeft = "text-secondary small"

  /** Footer right-side link group. */
  const val footerRight = "ms-auto d-flex align-items-center gap-3"

  /** Footer link styled as quiet secondary text with a subtler hover state. */
  const val footerLink = "link-secondary text-decoration-none link-opacity-75 link-opacity-100-hover d-inline-flex align-items-center"

  /** Small icon inside a footer link. */
  const val footerLinkIcon = "me-2"

  /** Breadcrumb container used in the page header. */
  const val breadcrumb = "breadcrumb m-0"

  /** Breadcrumb item wrapper. */
  const val breadcrumbItem = "breadcrumb-item"

  /** Active breadcrumb item wrapper. */
  const val breadcrumbItemActive = "active text-secondary"

  /** Breadcrumb link styled like the other quiet shell links. */
  const val breadcrumbLink = "link-secondary text-decoration-none link-opacity-75 link-opacity-100-hover d-inline-flex align-items-center"

  /** Wrapper that enables horizontal scrolling for wide tables. */
  const val tableResponsive = "table-responsive"

  /** Tabweb table with card styling and vertical cell alignment. */
  const val table = "table card-table table-vcenter"

  /** Empty body row used to keep paginated table height stable on short pages. */
  const val tablePlaceholderRow = "table-placeholder-row"

  const val tableNoWrap = "table-nowrap"
  const val stickyTop = "sticky-top"
  const val textNoWrap = "text-nowrap"

  // --- Pagination ---

  /** Card footer bar below a table, used for pagination controls and summary text. */
  const val cardFooter = "card-footer"

  /** Flex row that distributes the summary text left and page links right inside the card footer. */
  const val paginationRow = "row g-2 justify-content-center justify-content-sm-between"

  /** Left column of the pagination footer — flex-aligned for the summary paragraph. */
  const val paginationSummaryCol = "col-auto d-flex align-items-center"

  /** Right column of the pagination footer — holds the `ul.pagination` list. */
  const val paginationLinksCol = "col-auto"

  /** Tabweb/Bootstrap pagination list with auto left margin. */
  const val pagination = "pagination m-0 ms-auto"

  /** A single pagination list item (page number or prev/next button). */
  const val pageItem = "page-item"

  /** A pagination item styled as the currently active page. */
  const val pageItemActive = "page-item active"

  /** A pagination item that is not clickable (e.g. prev on first page). */
  const val pageItemDisabled = "page-item disabled"

  /** The visible anchor link inside a pagination item. */
  const val pageLink = "page-link"
}
