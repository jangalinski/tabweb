package com.github.jangalinski.tabweb._foundation.css

import com.github.jangalinski.tabweb._foundation.css.ClassNames.modifier
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Widths for columns in Tabler's 12-column responsive grid.
 *
 * `sm` applies from 576px, `md` from 768px, and `lg` from 992px.
 * A width set at one breakpoint continues to apply until a larger breakpoint overrides it.
 *
 * @param classNames Bootstrap column classes applied to the grid column.
 */
enum class GridWidth(val classNames: String) {
  /**
   * Shares the available row width equally with other automatic columns at every screen size.
   *
   * Uses `col` rather than a fixed number of the 12 grid columns.
   */
  AUTO("col"),

  /**
   * Occupies 3 of 12 columns (one quarter of the row) at every screen size.
   *
   * `BASE` means there is no breakpoint prefix.
   */
  BASE_QUARTER("col-3"),

  /**
   * Occupies all 12 columns (the full row) at every screen size.
   */
  FULL("col-12"),

  /**
   * Occupies the full row below `sm`, then 6 of 12 columns (half) from `sm` up.
   */
  HALF("col-12 col-sm-6"),

  /**
   * Occupies the full row below `lg`, then 4 of 12 columns (one third) from `lg` up.
   */
  THIRD("col-12 col-lg-4"),

  /**
   * Occupies the full row below `sm`, half the row from `sm`, and a quarter from `lg`.
   *
   * The `sm` and `lg` prefixes refer to the small and large screen breakpoints.
   */
  QUARTER("col-sm-6 col-lg-3"),

  /**
   * Occupies the full row below `md`, half the row from `md`, and a quarter from `lg`.
   *
   * `md` means medium screens and `lg` means large screens.
   */
  MD_HALF_LG_QUARTER("col-md-6 col-lg-3"),

  /**
   * Occupies the full row below `md`, half the row from `md`, and one third from `lg`.
   *
   * `md` means medium screens and `lg` means large screens.
   */
  MD_HALF_LG_THIRD("col-md-6 col-lg-4"),

  /**
   * Occupies the full row below `lg`, then half the row from `lg` up.
   *
   * `lg` means large screens.
   */
  LG_HALF("col-lg-6"),

  /**
   * Occupies the full row below `lg`, then 8 of 12 columns (two thirds) from `lg` up.
   *
   * `lg` means large screens.
   */
  LG_TWO_THIRDS("col-lg-8"),

  /**
   * Occupies the full row below `md`, then shares space equally with automatic columns from `md` up.
   *
   * `md` means medium screens; this preset does not fix a number of grid columns.
   */
  MD_AUTO("col-md"),

  /**
   * Occupies the full row below `md`, then 4 of 12 columns (one third) from `md` up.
   *
   * `md` means medium screens.
   */
  MD_THIRD("col-md-4"),
  ;

  /**
   * Converts this width preset into a modifier for a grid column.
   *
   * @return a modifier containing the column classes for this preset.
   */
  fun modifier(): Modifier = classNames.modifier()
}
