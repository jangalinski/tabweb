package com.github.jangalinski.tabweb._foundation.css

data object TabwebStyles {
  /** Badge class with an optional contextual background variant. */
  fun badge(variantClass: String? = null): String = listOfNotNull(ClassNames.badge, variantClass).joinToString(" ")

  /** Progress bar element with an optional Tabweb background class. */
  fun progressBar(variantClass: String? = null): String =
    listOfNotNull(ClassNames.progressBar, variantClass).joinToString(" ")
}
