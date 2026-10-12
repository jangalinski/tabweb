package com.github.jangalinski.tabweb.plugin.navigation

import com.github.jangalinski.tabweb.plugin.navigation.schema.TabwebNavigationManifest
import com.squareup.kotlinpoet.ClassName
import java.nio.file.Path

internal data class NavigationSource(
    val manifest: TabwebNavigationManifest,
    val manifestFile: Path,
    val packageName: String,
    val objectName: String,
) {
  val className = ClassName(packageName, objectName)
}
