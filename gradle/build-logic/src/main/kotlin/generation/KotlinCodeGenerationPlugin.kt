package com.github.jangalinski.tabweb.gradle.buildlogic.generation

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.spec.toFileSpec
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.nio.file.Files

/**
 * Generates Kotlin code for Tabweb CSS.
 */
@OptIn(ExperimentalKotlinPoetApi::class)
abstract class GenerateKotlinCodeTask : DefaultTask() {

  @get:OutputDirectory
  abstract val outputDirectory: DirectoryProperty

  @TaskAction
  fun generate() {
    val root = outputDirectory.get().asFile.toPath()
    val packageDir = root.resolve("com/github/jangalinski/tabweb/generated")
    Files.createDirectories(packageDir)
    val fooFile = packageDir.resolve("Foo.kt")
    val content = """
      |package com.github.jangalinski.tabweb.generated
      |
      |/**
      | * Example generated data class.
      | */
      |data class Foo(
      |  val name: String,
      |)
      |
    """.trimMargin()
    Files.writeString(fooFile, content)
    logger.lifecycle("Generated Foo.kt at $fooFile")

    KotlinCodeGeneration.buildDataClass(ClassName("com.github.jangalinski.tabweb.generated", "Bar")) {
      addConstructorProperty("name", String::class)
      addConstructorProperty("value", String::class)
    }.toFileSpec().get().writeTo(root)
  }
}

class KotlinCodeGenerationPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    project.tasks.register("generateKotlinCode", GenerateKotlinCodeTask::class.java) {
      group = "generation"
      description = "Generates Kotlin code for Tabweb CSS."
      outputDirectory.convention(project.layout.buildDirectory.dir("generated/sources/lib/src/jsMain/kotlin"))
    }
  }
}
