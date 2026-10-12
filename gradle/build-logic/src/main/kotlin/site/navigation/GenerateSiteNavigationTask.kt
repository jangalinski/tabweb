package com.github.jangalinski.tabweb.gradle.buildlogic.site.navigation

import java.nio.file.Files
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

abstract class GenerateSiteNavigationTask : DefaultTask(), () -> Unit {
  companion object {
    const val GROUP = "site"
    const val NAME = "generateNavigation"
    const val DESCRIPTION = "Generates Navigation structure for tabweb based site."
  }

  @get:InputFile
  abstract val inputFile: RegularFileProperty

  @get:OutputDirectory
  abstract val outputDirectory: DirectoryProperty

  @TaskAction
  override fun invoke() {
    val manifestFile = inputFile.get().asFile.toPath()
    val output = outputDirectory.get().asFile.toPath()

    Files.createDirectories(output)
    val generatedFiles = TabwebNavigationGenerator()(manifestFile)
    generatedFiles.forEach { it.get().writeTo(output) }
    logger.lifecycle("Generated ${generatedFiles.size} navigation file(s) from $manifestFile")
  }
}
