package com.github.jangalinski.tabweb.link

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.compose.KAnchor
import com.github.jangalinski.tabweb._foundation.compose.KText

@Composable
fun TabwebLink(href:String) {

  KAnchor(href = href) {
    KText(href)
  }

}
