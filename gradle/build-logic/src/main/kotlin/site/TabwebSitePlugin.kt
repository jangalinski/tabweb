package com.github.jangalinski.tabweb.gradle.buildlogic.site

import com.github.jangalinski.tabweb.gradle.buildlogic.BuildLogic
import com.github.jangalinski.tabweb.gradle.buildlogic.site.navigation.GenerateNavigationSchemaCodeTask
import com.github.jangalinski.tabweb.gradle.buildlogic.site.navigation.GenerateSiteNavigationTask
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * A Gradle plugin for the Tabweb site.
 */
class TabwebSitePlugin : Plugin<Project> {

  override fun apply(project: Project) {
    with(project.tasks) {
      register(GenerateSiteNavigationTask.NAME, GenerateSiteNavigationTask::class.java) {
        group = GenerateSiteNavigationTask.GROUP
        description = GenerateSiteNavigationTask.DESCRIPTION

        outputDirectory.convention(
          project.layout.buildDirectory.dir(BuildLogic.DEFAULT_OUTPUT_DIRECTORY)
        )
        inputFile.convention(
          project.layout.projectDirectory.file("src/jsMain/resources/navigation.json")
        )
      }

      register(GenerateNavigationSchemaCodeTask.NAME, GenerateNavigationSchemaCodeTask::class.java) {
        group = GenerateNavigationSchemaCodeTask.GROUP
        description = GenerateNavigationSchemaCodeTask.DESCRIPTION
        inputFile.convention(
          project.rootProject.layout.projectDirectory.file(
            "lib/src/jsMain/resources/navigation.schema.json",
          )
        )
        outputDirectory.convention(
          project.layout.buildDirectory.dir("generated/source/tabweb")
        )
      }
    }
  }
}
