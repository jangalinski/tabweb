package com.github.jangalinski.tabweb.gradle.buildlogic

import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.isEqualTo
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test

class TabwebIconsPluginTest {

  @Test
  fun `parses and prints icon names`() {
    val css = """
      .ti ti { }
      .ti-a-b:before { content: "\\ec36" }
      .ti-brand-denodo:before { content: "\\f123" }
      .ti-brand-denodo:before { content: "\\f123" }
    """.trimIndent()

    val names = parseTabwebIconNames(css)
    names.forEach(::println)

    assertThat(names).containsExactlyInAnyOrder("ti-a-b", "ti-brand-denodo")
  }

  @Test
  fun `registers download and print tasks`() {
    val project: Project = ProjectBuilder.builder().build()

    project.pluginManager.apply(TabwebIconsPlugin::class.java)

    assertThat(
      project.tasks.names.filter {
        it in setOf("downloadTabwebIconsCss", "printTabwebIconNames", "generateTabwebIcon")
      }
    ).containsExactlyInAnyOrder(
      "downloadTabwebIconsCss",
      "printTabwebIconNames",
      "generateTabwebIcon"
    )
  }

  @Test
  fun `generates sorted enum values using exact css names`() {
    val source = generateTabwebIconSource(listOf("ti-brand-github", "ti-footsteps", "ti-2fa"), "3.46.0")

    assertThat(source).isEqualTo(
      """
        package com.github.jangalinski.tabweb.icon

        import com.varabyte.kobweb.compose.ui.Modifier
        import com.varabyte.kobweb.compose.ui.modifiers.classNames

        /** Generated from Tabweb Icons CSS 3.46.0. Do not edit manually. */
        enum class TabwebIcon(private val modifier: Modifier) : Modifier by modifier {
          TI_2FA("ti-2fa"),
          TI_BRAND_GITHUB("ti-brand-github"),
          TI_FOOTSTEPS("ti-footsteps"),
          ;

          constructor(style: String) : this(Modifier.classNames("ti", style))
        }
      """.trimIndent() + "\n"
    )
  }
}
