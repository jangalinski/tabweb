package com.github.jangalinski.tabweb.gradle.buildlogic.site

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.exists
import assertk.assertions.isEqualTo
import com.github.jangalinski.tabweb.gradle.buildlogic.site.navigation.GenerateSiteNavigationTask
import java.nio.file.Files
import kotlin.test.Test
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

class TabwebSitePluginTest {

  @Test
  fun `registers generateNavigation task with defaults`() {
    val project = ProjectBuilder.builder().build()

    project.pluginManager.apply(TabwebSitePlugin::class.java)

    val task = project.tasks.findByName(GenerateSiteNavigationTask.NAME)
    assertThat(task?.group).isEqualTo(GenerateSiteNavigationTask.GROUP)
    assertThat(task?.description).isEqualTo(GenerateSiteNavigationTask.DESCRIPTION)
    assertThat(project.tasks.names).contains(GenerateSiteNavigationTask.NAME)
  }

  @Test
  fun `generates typed navigation object and resolves included sections`() {
    val projectDirectory = Files.createTempDirectory("tabweb-site-navigation-test")
    val project: Project = ProjectBuilder.builder()
      .withProjectDir(projectDirectory.toFile())
      .build()
    project.pluginManager.apply(TabwebSitePlugin::class.java)

    val inputFile = projectDirectory.resolve("navigation.json")
    Files.writeString(
      inputFile,
      $$"""
      {
        "version": 1,
        "root": [
          {
            "name": "Interface",
            "description": "UI components",
            "route": "/interface",
            "icon": "TI_BOX",
            "children": [
              {
                "name": "Buttons",
                "route": "/interfaces/buttons"
              }
            ],
            "sections": [
              { "$include": "dokka.json" }
            ]
          }
        ]
      }
      """.trimIndent(),
    )
    Files.writeString(
      projectDirectory.resolve("dokka.json"),
      """
      {
        "sections": [
          {
            "name": "com.example",
            "elements": [
              {
                "name": "Widget",
                "route": "/kdoc/com.example/Widget"
              }
            ]
          }
        ]
      }
      """.trimIndent(),
    )

    val outputDirectory = projectDirectory.resolve("generated")
    val task = project.tasks.getByName(GenerateSiteNavigationTask.NAME)
      as GenerateSiteNavigationTask
    task.inputFile.set(inputFile.toFile())
    task.outputDirectory.set(outputDirectory.toFile())

    task()

    val generatedFile = outputDirectory.resolve(
      "com/github/jangalinski/tabweb/site/GeneratedSiteNavigation.kt",
    ).toFile()
    assertThat(generatedFile).exists()
    assertThat(generatedFile.readText()).contains("public data object GeneratedSiteNavigation")
    assertThat(generatedFile.readText()).contains("icon = TablerIcon.TI_BOX")
    assertThat(generatedFile.readText()).contains("route = Url(\"/interfaces/buttons\")")
    assertThat(generatedFile.readText()).contains("title = \"com.example\"")
    assertThat(generatedFile.readText()).contains("route = Url(\"/kdoc/com.example/Widget\")")
  }
}
