package com.github.jangalinski.tabweb.plugin

import com.github.jangalinski.tabweb.plugin.navigation.GenerateNavigationTask
import kotlinx.serialization.json.Json
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Adds Tabweb code-generation tasks to a Kotlin Multiplatform project.
 */
class TabwebPlugin : Plugin<Project> {
  companion object {
    internal val JSON = Json { ignoreUnknownKeys = true }
  }

  override fun apply(project: Project) {
    val extension = project.extensions.create<TabwebExtension>("tabweb")

    val navigation = extension.navigation.apply {
      packageName.convention(project.provider { "${project.group}.navigation" })
      objectName.convention("GeneratedTabwebNavigation")
    }

    val task = project.tasks.register<GenerateNavigationTask>(GenerateNavigationTask.NAME) {
      group = GenerateNavigationTask.GROUP
      description = GenerateNavigationTask.DESCRIPTION

      inputFile.convention(
        project.layout.projectDirectory.file("src/jsMain/resources/navigation.json"),
      )
      outputDirectory.convention(
        project.layout.buildDirectory.dir("generated/sources/tabweb/kotlin"),
      )

      packageName.convention(navigation.packageName)
      objectName.convention(navigation.objectName)
    }

    project.pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
      val kotlin = project.extensions.getByType<KotlinMultiplatformExtension>()
      kotlin.sourceSets.maybeCreate("jsMain").kotlin.srcDir(task)
    }
  }
}

