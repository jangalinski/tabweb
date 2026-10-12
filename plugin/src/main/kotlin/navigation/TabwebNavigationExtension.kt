package com.github.jangalinski.tabweb.plugin.navigation

import org.gradle.api.provider.Property

/**
 * Configuration for the generated navigation object.
 */
abstract class TabwebNavigationExtension {
  /**
   * Package of the generated navigation object.
   */
  abstract val packageName: Property<String>

  /**
   * Name of the generated navigation object.
   */
  abstract val objectName: Property<String>
}
