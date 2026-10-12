package com.github.jangalinski.tabweb.plugin

import com.github.jangalinski.tabweb.plugin.navigation.TabwebNavigationExtension
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

/**
 * Configuration for Tabweb code generation.
 */
abstract class TabwebExtension @Inject constructor(objects: ObjectFactory) {
  /**
   * Navigation generation configuration.
   */
  val navigation: TabwebNavigationExtension = objects.newInstance(TabwebNavigationExtension::class.java)
}
