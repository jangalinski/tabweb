package com.github.jangalinski.tabweb.gradle.buildlogic.site

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.exists
import assertk.assertions.isEqualTo
import com.github.jangalinski.tabweb.gradle.buildlogic.site.navigation.GenerateNavigationSchemaCodeTask
import com.github.jangalinski.tabweb.gradle.buildlogic.site.navigation.GenerateSiteNavigationTask
import com.github.jangalinski.tabweb.gradle.buildlogic.site.navigation.schema.SectionEntry
import com.github.jangalinski.tabweb.gradle.buildlogic.site.navigation.schema.TabwebNavigationManifest
import java.nio.file.Files
import kotlin.test.Test
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.put
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import java.nio.file.Path

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
  fun `generates recursive navigation schema classes`() {
    val projectDirectory = Files.createTempDirectory("tabweb-navigation-schema-test")
    val project: Project = ProjectBuilder.builder()
      .withProjectDir(projectDirectory.toFile())
      .build()
    project.pluginManager.apply(TabwebSitePlugin::class.java)

    val schemaFile = projectDirectory.resolve("navigation.schema.json")
    Files.writeString(
      schemaFile,
      $$"""
      {
        "$schema": "https://json-schema.org/draft/2020-12/schema",
        "$defs": {
          "element": {
            "type": "object",
            "required": ["name", "route"],
            "properties": {
              "name": { "type": "string" },
              "route": { "type": "string" },
              "children": {
                "type": "array",
                "items": { "$ref": "#/$defs/element" }
              }
            }
          }
        },
        "type": "object",
        "properties": {
          "root": {
            "type": "array",
            "items": { "$ref": "#/$defs/element" }
          }
        }
      }
      """.trimIndent(),
    )

    val outputDirectory = projectDirectory.resolve("generated")
    val task = project.tasks.getByName(GenerateNavigationSchemaCodeTask.NAME)
      as GenerateNavigationSchemaCodeTask
    task.inputFile.set(schemaFile.toFile())
    task.outputDirectory.set(outputDirectory.toFile())

    task()

    val element = outputDirectory.resolve(
      "com/github/jangalinski/tabweb/gradle/buildlogic/site/navigation/schema/Element.kt",
    ).toFile()

    assertThat(element).exists()
    assertThat(element.readText()).contains("@Serializable")
    assertThat(element.readText()).contains("public val elements: List<Element>? = null")
  }

  @Test
  fun `reads site navigation with generated manifest`() {
    val project = ProjectBuilder.builder().build()
    val navigationFile = Path.of(System.getProperty("tabweb.repoRoot"))
      .resolve("site/src/jsMain/resources/navigation.json")

    val source = Json.parseToJsonElement(Files.readString(navigationFile))
    val manifest = Json.decodeFromJsonElement(
      TabwebNavigationManifest.serializer(),
      normalizeSectionEntryVariants(
        source,
        SectionEntry.SectionVariant.serializer().descriptor.serialName,
        SectionEntry.ObjectVariant.serializer().descriptor.serialName,
      ),
    )

    assertThat(manifest.version).isEqualTo(1)
    assertThat(manifest.elements.map { it.name }).isEqualTo(
      listOf("Home", "Interface", "Infrastructure", "Plugins", "Help"),
    )

    val plugins = manifest.elements.first { it.name == "Plugins" }
    val markdownPages = plugins.elements!!.first { it.name == "Markdown pages" }
    val tabwebSection = (
      markdownPages.sectionEntries!!.single() as SectionEntry.SectionVariant
      ).value

    assertThat(tabwebSection.name).isEqualTo("Tabweb")
    assertThat(tabwebSection.elements.map { it.name }).isEqualTo(
      listOf("Markdown pages", "Components"),
    )
  }

  private fun normalizeSectionEntryVariants(
    element: JsonElement,
    sectionVariantName: String,
    objectVariantName: String,
  ): JsonElement = when (element) {
    is JsonObject -> buildJsonObject {
      element.forEach { (name, value) ->
        if (name == "sections") {
          put(
            name,
            JsonArray(
              value.jsonArray.map { sectionEntry ->
                val variant = if ("\u0024include" in sectionEntry.jsonObject) {
                  objectVariantName
                } else {
                  sectionVariantName
                }
                buildJsonObject {
                  put("type", variant)
                  put("value", sectionEntry)
                }
              },
            ),
          )
        } else {
          put(name, normalizeSectionEntryVariants(value, sectionVariantName, objectVariantName))
        }
      }
    }

    is JsonArray -> JsonArray(
      element.map { normalizeSectionEntryVariants(it, sectionVariantName, objectVariantName) },
    )
    else -> element
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
    assertThat(generatedFile.readText()).contains("icon = TabwebIcon.TI_BOX")
    assertThat(generatedFile.readText()).contains("route = Url(\"/interfaces/buttons\")")
    assertThat(generatedFile.readText()).contains("title = \"com.example\"")
    assertThat(generatedFile.readText()).contains("route = Url(\"/kdoc/com.example/Widget\")")
  }
}
