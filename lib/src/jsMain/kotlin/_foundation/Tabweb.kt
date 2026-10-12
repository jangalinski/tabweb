package com.github.jangalinski.tabweb._foundation

import com.varabyte.kobweb.compose.ui.Modifier

data object Tabweb {

  /**
   * Controls runtime behavior, dynamic animations, and interactive states, for example
   * pulsing animations (`badge-blink`, `icon-pulse`), loading states (`btn-loading`),
   * or interactive rows and headers (`table-hover`, `table-sort`).
   */
  interface TabwebBehavior

  /**
   * Applies palette colors, theme tints, gradients, or semantic color accents,
   * for example contextual variants (`alert-{color}`, `btn-{color}`),
   * background tints (`bg-{color}`, `steps-{color}`), or trend indicators
   * (`text-green`, `text-red`).
   */
  @Deprecated("Use TabwebColor instead")
  interface Color : Modifier, TabwebValue<String> {

    /**
     * ClassName value used to apply the color to an element.
     */
    val value: String

    /**
     * Short initials used to represent the color, for example in a legend or key.
     */
    val initials: Initials

    /**
     * Display name of the color, for example "Azure" or "Red".
     */
    val displayName: String

    override fun get() = value
  }

  /**
   * Specifies alignment, placement edges, opening direction, or layout orientation,
   * for example edge placement (ribbon-top, offcanvas-start), text or menu
   * alignment (hr-text-start, dropdown-menu-end),
   * or vertical stacking (steps-vertical, nav-segmented-vertical).
   */
  interface Direction

  /**
   * Modifies layout flow, geometry, positioning, or responsive layout behavior,
   * for example geometric shapes (badge-pill, btn-square),
   * stacking and tilting (avatar-list-stacked, card-stacked),
   * or responsive layouts (table-responsive, modal-fullscreen).
   */
  interface TabwebModifier

  /**
   * Designates internal structural elements, child sub-components, or content slots within a composite component hierarchy,
   * for example sub-elements (card-header, accordion-body, modal-dialog, dropdown-item).
   */
  interface Part {
  }

}
