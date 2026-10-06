package com.github.jangalinski.tabweb._foundation

import com.varabyte.kobweb.compose.ui.Alignment

sealed interface TabwebDirection {

  sealed interface Horizontal : TabwebDirection

  data object LEFT : Horizontal {
    val alignment: Alignment.Horizontal = Alignment.Start
  }

  data object RIGHT : Horizontal {
    val alignment: Alignment.Horizontal = Alignment.End
  }
}

