package com.github.jangalinski.tabweb.gradle.buildlogic.generation

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.exists
import assertk.assertions.isEqualTo
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import java.io.File
import kotlin.test.Test

class KotlinCodeGenerationPluginTest {

  @Test
  fun `registers generateKotlinCode task with group and description`() {
    val project: Project = ProjectBuilder.builder().build()

    project.pluginManager.apply(KotlinCodeGenerationPlugin::class.java)

    val task = project.tasks.findByName("generateKotlinCode")
    assertThat(task?.group).isEqualTo("generation")
    assertThat(task?.description).isEqualTo("Generates Kotlin code for Tabweb CSS.")
    assertThat(project.tasks.names).contains("generateKotlinCode")
  }

  @Test
  fun `generateKotlinCode task writes Foo data class to target directory`() {
    val project: Project = ProjectBuilder.builder().build()
    project.pluginManager.apply(KotlinCodeGenerationPlugin::class.java)

    val task = project.tasks.getByName("generateKotlinCode") as GenerateKotlinCodeTask
    val testOutputDir = File(project.layout.buildDirectory.asFile.get(), "test-generated")
    task.outputDirectory.set(testOutputDir)

    task.generate()

    val generatedFile = File(testOutputDir, "com/github/jangalinski/tabweb/generated/Foo.kt")
    assertThat(generatedFile).exists()
    val expectedContent = """
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
    assertThat(generatedFile.readText()).isEqualTo(expectedContent)
  }
}
