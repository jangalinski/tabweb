package com.github.jangalinski.tabweb.plugin.navigation

import java.nio.file.Files
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import java.nio.file.Path

/**
 * Generates a typed navigation object from a Tabweb navigation manifest.
 */
@CacheableTask
abstract class GenerateNavigationTask : DefaultTask(), () -> Unit {
  companion object {
    /**
     * Task group used for Tabweb code-generation tasks.
     */
    const val GROUP = "tabweb"

    /**
     * Name of the navigation generation task.
     */
    const val NAME = "generateNavigation"

    /**
     * Description shown for the navigation generation task.
     */
    const val DESCRIPTION = "Generates typed Tabweb navigation from navigation.json."
  }

  /**
   * Navigation manifest consumed by the task.
   */
  @get:InputFile
  @get:PathSensitive(PathSensitivity.RELATIVE)
  abstract val inputFile: RegularFileProperty

  /**
   * Directory containing the generated Kotlin sources.
   */
  @get:OutputDirectory
  abstract val outputDirectory: DirectoryProperty

  /**
   * Package name for the generated navigation object.
   */
  @get:Input
  abstract val packageName: Property<String>

  /**
   * Name of the generated navigation object.
   */
  @get:Input
  abstract val objectName: Property<String>

  @TaskAction
  override fun invoke() {
    val manifestFile: Path = inputFile.get().asFile.toPath()
    val output : Path = outputDirectory.get().asFile.toPath()

    Files.createDirectories(output)
    val generatedFiles = TabwebNavigationGenerator(
      packageName = packageName.get(),
      objectName = objectName.get(),
    )(manifestFile)
    generatedFiles.forEach { it.get().writeTo(output) }
    logger.lifecycle("Generated ${generatedFiles.size} navigation file(s) from $manifestFile")
  }
}
