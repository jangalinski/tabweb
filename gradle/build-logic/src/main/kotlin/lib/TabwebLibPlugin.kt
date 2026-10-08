package com.github.jangalinski.tabweb.gradle.buildlogic.lib

import com.github.jangalinski.tabweb.gradle.buildlogic.BuildLogic
import com.github.jangalinski.tabweb.gradle.buildlogic.lib.task.GenerateLibCodeTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/**
 * A Gradle plugin for the Tabweb library.
 */
class TabwebLibPlugin : Plugin<Project> {

  override fun apply(project: Project) {
    val libs = project.extensions
      .getByType<VersionCatalogsExtension>()
      .named("libs")

    with(project.tasks) {
      register(GenerateLibCodeTask.NAME, GenerateLibCodeTask::class.java) {
        group = GenerateLibCodeTask.GROUP
        description = GenerateLibCodeTask.DESCRIPTION
        outputDirectory.convention(
          project.layout.buildDirectory.dir(BuildLogic.DEFAULT_OUTPUT_DIRECTORY)
        )
        projectVersion.convention(project.provider { project.version.toString() })

        versions.putAll(libs.versionAliases.associateWith { libs.findVersion(it).get().requiredVersion })
      }
    }
  }
}
