package com.github.jangalinski.tabweb.gradle.buildlogic.lib.task

import com.github.jangalinski.tabweb.gradle.buildlogic.lib.generator.TabwebLibGenerator
import com.github.jangalinski.tabweb.gradle.buildlogic.lib.model.ColorsModel
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class GenerateLibCodeTask : DefaultTask(), () -> Unit {
  companion object {
    const val GROUP = "lib"
    const val NAME = "generateLibCode"
    const val DESCRIPTION = "Generates Kotlin code for Tabler."
  }

  @get:Input
  abstract val projectVersion: Property<String>

  @get:Input
  abstract val versions: MapProperty<String, String>

  @get:OutputDirectory
  abstract val outputDirectory: DirectoryProperty

  @TaskAction
  override operator fun invoke() {
    val root = outputDirectory.get().asFile.toPath().toAbsolutePath()
    println("Generating Tabweb library code to $root")

    val colors : ColorsModel = ColorsModel.load()

    val generator = TabwebLibGenerator(colors)

    println("Generating Version.kt for ${projectVersion.get()}")
    println("Using versions: ${versions.get()}")

    println("Generating color types: $colors")

    val generatedFiles = generator()
    println("Generated ${generatedFiles.size} files:")
    generatedFiles.forEach { println("- ${it.fileName}") }

    generatedFiles.forEach { it.get().writeTo(root) }
  }
}
