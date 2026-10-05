package com.github.jangalinski.tabweb._foundation

enum class Placement : TabwebValue<String> {
  TOP,
  RIGHT,
  BOTTOM,
  LEFT,
  ;

  override fun get() = name.lowercase()
}
