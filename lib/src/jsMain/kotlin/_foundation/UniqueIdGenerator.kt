package com.github.jangalinski.tabweb._foundation

data object UniqueIdGenerator {
  private val componentIds = mutableSetOf<String>()

  fun generateUniqueId(baseName: String): String {
    val name = baseName.lowercase().replace(" ", "-")
    var id = name
    var counter = 1

    while (componentIds.contains(id)) {
      id = "$name${counter++}"
    }

    componentIds.add(id)
    return id
  }

  fun removeUniqueId(id: String) {
    componentIds.remove(id)
  }
}
