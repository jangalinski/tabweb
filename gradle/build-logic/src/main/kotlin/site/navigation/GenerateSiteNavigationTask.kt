package com.github.jangalinski.tabweb.gradle.buildlogic.site.navigation

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.nio.file.Files

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
    val navigation = TablerNavigationJson.parse(Files.readString(manifestFile)) { reference ->
      val includedFile = manifestFile.parent.resolve(reference).normalize()
      require(includedFile.startsWith(manifestFile.parent.normalize())) {
        "Navigation include '$reference' escapes the manifest directory."
      }
      require(Files.isRegularFile(includedFile)) {
        "Navigation include '$reference' does not exist at $includedFile."
      }
      Files.readString(includedFile)
    }

    Files.createDirectories(output)
    val generatedFiles = TabwebNavigationGenerator()(navigation)
    generatedFiles.forEach { it.get().writeTo(output) }
    logger.lifecycle("Generated ${generatedFiles.size} navigation file(s) from $manifestFile")
  }
}
