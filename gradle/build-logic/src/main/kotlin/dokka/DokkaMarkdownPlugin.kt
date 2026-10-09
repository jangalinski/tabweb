package com.github.jangalinski.tabweb.gradle.buildlogic.dokka

import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.dokka.gradle.formats.DokkaFormatPlugin
import org.jetbrains.dokka.gradle.internal.InternalDokkaGradlePluginApi

/**
 * Registers GitHub Flavored Markdown as an internal Dokka output format.
 *
 * The implementation follows Dokka's GFM format-plugin contract. Applying
 * this plugin to a project that already applies the Dokka Gradle plugin adds
 * the `dokkaGenerateMarkdown` task.
 */
@OptIn(InternalDokkaGradlePluginApi::class)
abstract class DokkaMarkdownPlugin : DokkaFormatPlugin(formatName = "markdown") {

  override fun DokkaFormatPlugin.DokkaFormatPluginContext.configure() {
    project.dependencies {
      dokkaPlugin(dokka("gfm-plugin"))
      formatDependencies.dokkaPublicationPluginClasspathApiOnly.dependencies.addLater(
        dokka("gfm-template-processing-plugin"),
      )
    }

    project.tasks.register("generateDokkaMarkdown", GenerateDokkaMarkdownTask::class.java) {
      dependsOn("dokkaGenerateMarkdown")
      projectPath.set(project.path)
      projectVersion.set(project.provider { project.version.toString() })
      sourceDirectory.set(project.layout.buildDirectory.dir("dokka/markdown"))
      outputDirectory.set(project.layout.buildDirectory.dir("dokka-markdown"))
    }
  }
}
