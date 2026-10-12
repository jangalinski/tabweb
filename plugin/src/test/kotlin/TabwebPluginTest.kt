package com.github.jangalinski.tabweb.plugin

import com.github.jangalinski.tabweb.plugin.navigation.GenerateNavigationTask
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.gradle.testfixtures.ProjectBuilder

class TabwebPluginTest {

  @Test
  fun `registers navigation generation with useful defaults`() {
    val project = ProjectBuilder.builder().build()

    project.pluginManager.apply(TabwebPlugin::class.java)

    val task = project.tasks.getByName(GenerateNavigationTask.NAME) as GenerateNavigationTask
    assertEquals(GenerateNavigationTask.GROUP, task.group)
    assertEquals(GenerateNavigationTask.DESCRIPTION, task.description)
    assertEquals("GeneratedTabwebNavigation", task.objectName.get())
  }

  @Test
  fun `generates typed navigation from a manifest`() {
    val projectDirectory = Files.createTempDirectory("tabweb-navigation-plugin-test")
    val project = ProjectBuilder.builder()
      .withProjectDir(projectDirectory.toFile())
      .build()
    project.pluginManager.apply(TabwebPlugin::class.java)

    val input = projectDirectory.resolve("navigation.json")
    Files.writeString(
      input,
      """
      {
        "version": 1,
        "root": [
          {
            "name": "Buttons",
            "route": "/interfaces/buttons",
            "icon": "TI_BOX"
          }
        ]
      }
      """.trimIndent(),
    )

    val output = projectDirectory.resolve("generated")
    val task = project.tasks.getByName(GenerateNavigationTask.NAME) as GenerateNavigationTask
    task.inputFile.set(input.toFile())
    task.outputDirectory.set(output.toFile())
    task.packageName.set("com.example.navigation")
    task.objectName.set("GeneratedNavigation")

    task.invoke()

    val generated = output.resolve(
      "com/example/navigation/GeneratedNavigation.kt",
    ).toFile()

    assertTrue(generated.isFile)
    assertTrue(generated.readText().contains("public data object GeneratedNavigation"))
    assertTrue(generated.readText().contains(": TabwebNavigation"))
    assertTrue(generated.readText().contains("override fun iterator()"))
    assertTrue(generated.readText().contains("icon = TabwebIcon.TI_BOX"))
    assertTrue(generated.readText().contains("route = Url(\"/interfaces/buttons\")"))
  }
}
