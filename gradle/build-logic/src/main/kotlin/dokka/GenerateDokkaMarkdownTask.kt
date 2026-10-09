package com.github.jangalinski.tabweb.gradle.buildlogic.dokka

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import java.nio.file.Files
import java.nio.file.StandardCopyOption

/** Copies Dokka Markdown output into one inspectable directory and writes metadata. */
abstract class GenerateDokkaMarkdownTask : DefaultTask() {
  @get:Input
  abstract val projectPath: Property<String>

  @get:Input
  abstract val projectVersion: Property<String>

  @get:InputDirectory
  @get:PathSensitive(PathSensitivity.RELATIVE)
  abstract val sourceDirectory: DirectoryProperty

  @get:OutputDirectory
  abstract val outputDirectory: DirectoryProperty

  @TaskAction
  fun generate() {
    val source = sourceDirectory.get().asFile.toPath()
    val output = outputDirectory.get().asFile.toPath()

    require(Files.isDirectory(source)) {
      "Dokka Markdown output does not exist: $source"
    }

    output.toFile().deleteRecursively()
    Files.createDirectories(output)

    val markdownFiles = Files.walk(source).use { paths ->
      paths.filter(Files::isRegularFile).toList().map { file ->
        val relative = source.relativize(file)
        val destination = output.resolve(relative)
        Files.createDirectories(destination.parent)
        Files.copy(file, destination, StandardCopyOption.REPLACE_EXISTING)
        relative.toString().replace('\\', '/')
      }.filter { it.endsWith(".md") }.sorted()
    }

    output.resolve("meta.json").toFile().writeText(
      buildString {
        appendLine("{")
        appendLine("  \"project\": \"${projectPath.get()}\",")
        appendLine("  \"version\": \"${projectVersion.get()}\",")
        appendLine("  \"format\": \"gfm\",")
        appendLine("  \"markdownFiles\": [")
        markdownFiles.forEachIndexed { index, file ->
          append("    \"")
          append(file.replace("\\", "\\\\").replace("\"", "\\\""))
          append('"')
          if (index != markdownFiles.lastIndex) append(',')
          appendLine()
        }
        appendLine("  ]")
        appendLine("}")
      },
    )

    logger.lifecycle("Copied ${markdownFiles.size} Markdown files to $output")
  }
}
