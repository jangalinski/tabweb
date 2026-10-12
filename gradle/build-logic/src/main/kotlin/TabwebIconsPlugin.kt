package com.github.jangalinski.tabweb.gradle.buildlogic

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import java.net.URI
import java.nio.file.Files
import java.util.Locale

private const val DEFAULT_CSS_URL =
  "https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@3.46.0/dist/tabler-icons.min.css"

abstract class TabwebIconsExtension {
  /** The Tabweb Icons CSS file to download. */
  abstract val cssUrl: Property<String>

  /** The Tabweb Icons version represented by the CSS file. */
  abstract val version: Property<String>
}

abstract class DownloadTabwebIconsCssTask : DefaultTask() {
  @get:Input
  abstract val cssUrl: Property<String>

  @get:OutputFile
  abstract val outputFile: RegularFileProperty

  @TaskAction
  fun download() {
    val destination = outputFile.get().asFile.toPath()
    Files.createDirectories(destination.parent)
    URI(cssUrl.get()).toURL().openStream().use { input ->
      Files.newOutputStream(destination).use { output ->
        input.copyTo(output)
      }
    }
    logger.lifecycle("Downloaded Tabweb Icons CSS to $destination")
  }
}

abstract class PrintTabwebIconNamesTask : DefaultTask() {
  @get:InputFile
  @get:PathSensitive(PathSensitivity.RELATIVE)
  abstract val cssFile: RegularFileProperty

  @TaskAction
  fun printNames() {
    val names = parseTabwebIconNames(cssFile.get().asFile.readText())
    names.forEach(logger::lifecycle)
    logger.lifecycle("Found ${names.size} Tabweb icon names")
  }
}

abstract class GenerateTabwebIconTask : DefaultTask() {
  @get:InputFile
  @get:PathSensitive(PathSensitivity.RELATIVE)
  abstract val cssFile: RegularFileProperty

  @get:OutputFile
  abstract val outputFile: RegularFileProperty

  @get:Input
  abstract val version: Property<String>

  @TaskAction
  fun generate() {
    val names = parseTabwebIconNames(cssFile.get().asFile.readText())
    val source = generateTabwebIconSource(names, version.get())
    val destination = outputFile.get().asFile.toPath()
    Files.createDirectories(destination.parent)
    destination.toFile().writeText(source)
    logger.lifecycle("Generated TabwebIcon with ${names.size} values at $destination")
  }
}

class TabwebIconsPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    val extension = project.extensions.create("tabwebIcons", TabwebIconsExtension::class.java)
    extension.cssUrl.convention(DEFAULT_CSS_URL)
    extension.version.convention("3.46.0")

    val downloadTask = project.tasks.register(
      "downloadTabwebIconsCss",
      DownloadTabwebIconsCssTask::class.java
    ) {
      group = "tabweb"
      description = "Downloads the configured Tabweb Icons CSS file."
      cssUrl.set(extension.cssUrl)
      outputFile.convention(project.layout.buildDirectory.file("tabweb-icons/tabweb-icons.css"))
    }

    project.tasks.register("printTabwebIconNames", PrintTabwebIconNamesTask::class.java) {
      group = "tabweb"
      description = "Prints all Tabweb icon CSS class names."
      cssFile.set(downloadTask.flatMap { it.outputFile })
      dependsOn(downloadTask)
    }

    val generateTask = project.tasks.register("generateTabwebIcon", GenerateTabwebIconTask::class.java) {
      group = "tabweb"
      description = "Generates the TabwebIcon source from the downloaded CSS file."
      cssFile.set(downloadTask.flatMap { it.outputFile })
      outputFile.convention(
        project.layout.projectDirectory.file("src/jsMain/kotlin/icon/TabwebIcon.kt")
      )
      version.set(extension.version)
      dependsOn(downloadTask)
    }
  }
}

internal fun parseTabwebIconNames(css: String): List<String> =
  Regex("""\.(ti-[a-z0-9-]+):before\s*\{""")
    .findAll(css)
    .map { it.groupValues[1] }
    .distinct()
    .sorted()
    .toList()

internal fun generateTabwebIconSource(names: List<String>, version: String): String = buildString {
  appendLine("package com.github.jangalinski.tabweb.icon")
  appendLine()
  appendLine("import com.varabyte.kobweb.compose.ui.Modifier")
  appendLine("import com.varabyte.kobweb.compose.ui.modifiers.classNames")
  appendLine()
  appendLine("/** Generated from Tabweb Icons CSS $version. Do not edit manually. */")
  appendLine("enum class TabwebIcon(private val modifier: Modifier) : Modifier by modifier {")
  names.sorted().forEach { cssName ->
    val enumName = cssName.uppercase(Locale.ROOT)
      .replace('-', '_')
    appendLine("  $enumName(\"$cssName\"),")
  }
  appendLine("  ;")
  appendLine()
  appendLine("  constructor(style: String) : this(Modifier.classNames(\"ti\", style))")
  appendLine("}")
}
