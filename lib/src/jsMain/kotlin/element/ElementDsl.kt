package com.github.jangalinski.tabweb.element

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.TabwebComponentDsl
import com.varabyte.kobweb.compose.ui.Modifier

internal data object ElementDsl : TabwebComponentDsl, ElementComposable {

  @Composable
  override fun divider(text: String?, modifier: Modifier) {
    Divider(text).invoke(modifier)
  }

  @Composable
  override fun status(text: String, modifier: Modifier) {
    Status(text).invoke(modifier)
  }

  @Composable
  override fun tracking(
    blocks: List<Tracking.Block>,
    square: Boolean,
    modifier: Modifier
  ) {
    Tracking(blocks, square).invoke(modifier)
  }
}
