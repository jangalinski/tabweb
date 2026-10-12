package com.github.jangalinski.tabweb.gradle.buildlogic

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.net.URI
import javax.inject.Inject
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

/** Configuration for the generated, version-pinned Tabweb CSS reference. */
abstract class TabwebCssDocumentationExtension @Inject constructor() {
  /** Version of the Tabweb Core stylesheet to document. */
  abstract val tablerVersion: Property<String>
}

/** Generates a navigable Markdown inventory from Tabweb Core's unminified stylesheet. */
abstract class GenerateTabwebCssDocumentationTask : DefaultTask() {
  /** Version embedded in the source CDN URL and generated reference. */
  @get:Input
  abstract val tablerVersion: Property<String>

  /** Directory containing the generated Markdown reference. */
  @get:OutputDirectory
  abstract val outputDirectory: DirectoryProperty

  /** Optional TSV file containing reviewed category, owner, status, and description overrides. */
  @get:InputFile
  @get:Optional
  abstract val manifestFile: RegularFileProperty

  @TaskAction
  fun generate() {
    val version = tablerVersion.get()
    val stylesheetUrl = "https://cdn.jsdelivr.net/npm/@tabler/core@$version/dist/css/tabler.css"
    val stylesheet = URI(stylesheetUrl).toURL().readText()
    val classes = CLASS_PATTERN.findAll(stylesheet)
      .map { it.groupValues[1] }
      .filterNot { it.startsWith("css-") }
      .toSortedSet()
    val overrides = readOverrides()

    val root = outputDirectory.get().asFile.toPath()
    root.createDirectories()

    val entries = classes.map { cssClass ->
      val override = overrides[cssClass]
      val category = override?.category ?: classify(cssClass)
      TabwebCssClass(
        name = cssClass,
        category = category,
        owner = override?.owner ?: ownerFor(category),
        status = override?.status ?: "unreviewed",
        description = override?.description,
      )
    }
    entries.groupBy { it.category }.toSortedMap().forEach { (category, categoryEntries) ->
      writeCategory(root, category, categoryEntries, version, stylesheetUrl)
    }
    writeReadme(root, entries, version, stylesheetUrl)
    writeIndex(root, entries)

    logger.lifecycle("Generated ${entries.size} Tabweb CSS reference entries in $root")
  }

  private fun writeReadme(
    root: java.nio.file.Path,
    entries: List<TabwebCssClass>,
    version: String,
    stylesheetUrl: String,
  ) {
    root.resolve("README.md").writeTextIfChanged(
      """
      # Tabweb CSS reference

      Generated from [Tabweb Core $version]($stylesheetUrl). Do not edit generated class pages directly; run
      `just generate-tabweb-css-docs` after changing `cdn-tabler-core`.

      This is a complete inventory of static CSS class selectors shipped by the pinned stylesheet. It is a planning
      checklist, not a claim that every class is in the supported Kobweb Tabweb API.

      ## Status vocabulary

      - **unreviewed** — no library decision yet.
      - **foundation candidate** — generic Bootstrap/Tabweb utility suitable for `_foundation`.
      - **concept-owned candidate** — belongs to its named Tabweb concept package.

      The generated category and suggested owner are starting points. Review them before exposing a public API or
      enforcing ownership through Detekt. Store reviewed decisions in
      [`../tabler-css-manifest.tsv`](../tabler-css-manifest.tsv), not in generated class pages.

      ## Scope

      - Bootstrap-derived utilities are grouped below `foundation/`.
      - Tabweb component classes are grouped below their likely concept package.
      - The entry for a responsive variant documents its exact emitted class; responsive families can subsequently be
        represented by one typed Kotlin API rather than one API per breakpoint.

      ${entries.size} class selectors are currently indexed. See [_index.md](_index.md).
      """.trimIndent() + "\n",
    )
  }

  private fun writeIndex(root: java.nio.file.Path, entries: List<TabwebCssClass>) {
    val categories = entries.groupBy { it.category }.toSortedMap()
    root.resolve("_index.md").writeTextIfChanged(
      buildString {
        appendLine("# Tabweb CSS reference index")
        appendLine()
        appendLine("Generated inventory grouped by suggested API owner.")
        appendLine()
        categories.forEach { (category, categoryEntries) ->
          appendLine("- [$category]($category/_index.md) — ${categoryEntries.size} classes")
        }
      },
    )
  }

  private fun writeCategory(
    root: java.nio.file.Path,
    category: String,
    entries: List<TabwebCssClass>,
    version: String,
    stylesheetUrl: String,
  ) {
    val directory = root.resolve(category)
    directory.createDirectories()
    directory.resolve("_index.md").writeTextIfChanged(
      buildString {
        appendLine("# $category")
        appendLine()
        appendLine("${entries.size} generated entries grouped by category.")
        appendLine()
        entries.sortedBy { it.name }.forEach { entry ->
          appendLine("- [`${entry.name}`](${entry.name}.md)")
        }
      },
    )
    entries.forEach { entry ->
      directory.resolve("${entry.name}.md").writeTextIfChanged(
        """
        # `${entry.name}`

        - **Category:** `$category`
        - **Owner:** `${entry.owner}`
        - **Library status:** ${entry.status}
        - **Source:** [Tabweb Core $version]($stylesheetUrl)

        ## Meaning

        ${entry.description ?: descriptionFor(entry)}

        ## Upstream documentation

        ${upstreamDocumentationFor(entry.category).replace("\n", "\n        ")}

        ## Kotlin API

        Not exposed yet. Add a typed API only after deciding whether this is a cross-concept foundation utility or
        an implementation detail of the suggested owner.
        """.trimIndent() + "\n",
      )
    }
  }

  private fun classify(cssClass: String): String = when {
    cssClass.matches(Regex("(m|p)[trblxyse]?(-[a-z]{2,3})?-[0-9a-z]+")) -> "foundation/spacing"
    cssClass.matches(Regex("(w|h|mw|mh|vw|vh)-[0-9a-z-]+")) -> "foundation/sizing"
    cssClass.startsWith("bg-") || cssClass.startsWith("text-") || cssClass.startsWith("border-") ||
      cssClass.startsWith("link-") -> "foundation/colors"
    cssClass.startsWith("d-") || cssClass.startsWith("flex") || cssClass.startsWith("justify-") ||
      cssClass.startsWith("align-") || cssClass.startsWith("order-") || cssClass.startsWith("gap-") ||
      cssClass.startsWith("float-") || cssClass.startsWith("position-") || cssClass.startsWith("top-") ||
      cssClass.startsWith("bottom-") || cssClass.startsWith("start-") || cssClass.startsWith("end-") ||
      cssClass in LAYOUT_CLASSES -> "foundation/layout"
    cssClass == "row" || cssClass == "col" || cssClass == "g" || cssClass == "gx" || cssClass == "gy" ||
      cssClass.startsWith("container") || cssClass.startsWith("row-") || cssClass.startsWith("col-") ||
      cssClass.startsWith("offset-") || cssClass.startsWith("g-") || cssClass.startsWith("gx-") ||
      cssClass.startsWith("gy-") -> "foundation/grid"
    cssClass.startsWith("font-") || cssClass.startsWith("fs-") || cssClass.startsWith("fw-") ||
      cssClass.startsWith("fst-") || cssClass.startsWith("lh-") || cssClass.startsWith("text-wrap") ||
      cssClass.startsWith("text-break") || cssClass.startsWith("text-truncate") || cssClass in TYPOGRAPHY_CLASSES -> "foundation/typography"
    cssClass.startsWith("border") || cssClass.startsWith("divide") || cssClass == "hr" -> "foundation/border"
    cssClass.startsWith("cursor") || cssClass.startsWith("user-select") || cssClass.startsWith("pe-") -> "foundation/interaction"
    cssClass in STATE_CLASSES -> "foundation/state"
    cssClass.startsWith("rounded") || cssClass.startsWith("shadow") || cssClass.startsWith("opacity-") ||
      cssClass.startsWith("overflow-") || cssClass.startsWith("object-") || cssClass.startsWith("ratio") ||
      cssClass.startsWith("z-") || cssClass.startsWith("visible") || cssClass.startsWith("invisible") ||
      cssClass.startsWith("visually-hidden") -> "foundation/utility"
    else -> componentCategory(cssClass)
  }

  private fun componentCategory(cssClass: String): String {
    val prefix = cssClass.substringBefore('-')
    return when (prefix) {
      "card" -> "card"
      "navbar", "nav" -> "navbar"
      "badge" -> "badge"
      "avatar" -> "avatar"
      "table" -> "table"
      "breadcrumb" -> "breadcrumb"
      "icon", "ti" -> "icon"
      "btn", "button" -> "button"
      "dropdown" -> "dropdown"
      "dropend", "dropstart", "dropup" -> "dropdown"
      "alert" -> "alert"
      "modal" -> "modal"
      "form", "input", "select", "textarea", "valid", "invalid" -> "form"
      "list" -> "list"
      "pagination", "page" -> "pagination"
      "offcanvas" -> "offcanvas"
      "accordion" -> "accordion"
      "progress" -> "progress"
      "toast" -> "toast"
      "tooltip" -> "tooltip"
      "popover" -> "popover"
      "tab" -> "tabs"
      "carousel" -> "carousel"
      "calendar" -> "calendar"
      "chart" -> "chart"
      "chat" -> "chat"
      "datagrid" -> "datagrid"
      "dimmer" -> "dimmer"
      "empty" -> "empty_state"
      "footer" -> "footer"
      "mention" -> "mention"
      "prose" -> "prose"
      "ribbon" -> "ribbon"
      "scroll", "scrollable" -> "scroll"
      "signature" -> "signature"
      "spinner" -> "spinner"
      "star", "stars" -> "star_rating"
      "status" -> "status"
      "step", "steps" -> "steps"
      "switch" -> "switch"
      "tag", "tags" -> "tag"
      "timeline" -> "timeline"
      "tracking" -> "tracking"
      "trend" -> "trending"
      else -> "tabler/${prefix.ifBlank { "misc" }}"
    }
  }

  private fun ownerFor(category: String): String = when {
    category.startsWith("foundation/") -> "_foundation.${category.substringAfter('/')}"
    category.startsWith("tabler/") -> "unassigned (${category.substringAfter('/')})"
    else -> category
  }

  private fun descriptionFor(entry: TabwebCssClass): String = when (entry.category) {
    "foundation/colors" -> "A color-related Tabweb or Bootstrap utility. Its exact color token and aspect are encoded in `${entry.name}`."
    "foundation/layout" -> "A generic layout or responsive utility. It affects display, flex placement, alignment, ordering, or positioning."
    "foundation/grid" -> "A generic Bootstrap grid utility for containers, rows, columns, gutters, or offsets."
    "foundation/spacing" -> "A generic spacing utility. Its name encodes margin or padding, direction, optional breakpoint, and scale."
    "foundation/sizing" -> "A generic width, height, viewport, or maximum-size utility."
    "foundation/typography" -> "A generic typography utility for font family, size, weight, style, line height, or text flow."
    "foundation/border" -> "A generic border or divider utility."
    "foundation/interaction" -> "A generic interaction utility for cursor, pointer events, or text selection."
    "foundation/state" -> "A generic state class used by Bootstrap or Tabweb JavaScript behavior."
    "foundation/utility" -> "A generic visual or accessibility utility rather than a specific Tabweb component element."
    else -> "A Tabweb `${entry.category}` CSS selector. Its DOM role and permitted combinations should be confirmed against Tabweb documentation before exposing it."
  }

  private fun upstreamDocumentationFor(category: String): String = when (category) {
    "foundation/colors" -> "- [Tabweb colors](https://docs.tabler.io/ui/base/colors)\n- [Bootstrap colors](https://getbootstrap.com/docs/5.3/utilities/colors/)"
    "foundation/grid" -> "- [Bootstrap grid](https://getbootstrap.com/docs/5.3/layout/grid/)"
    "foundation/layout" -> "- [Bootstrap display](https://getbootstrap.com/docs/5.3/utilities/display/)\n- [Bootstrap flex](https://getbootstrap.com/docs/5.3/utilities/flex/)"
    "foundation/spacing" -> "- [Tabweb margins](https://docs.tabler.io/ui/utilities/margins)\n- [Bootstrap spacing](https://getbootstrap.com/docs/5.3/utilities/spacing/)"
    "foundation/sizing" -> "- [Bootstrap sizing](https://getbootstrap.com/docs/5.3/utilities/sizing/)"
    "foundation/typography" -> "- [Tabweb typography](https://docs.tabler.io/ui/base/typography)\n- [Bootstrap text](https://getbootstrap.com/docs/5.3/utilities/text/)"
    "foundation/border" -> "- [Tabweb borders](https://docs.tabler.io/ui/utilities/borders)\n- [Bootstrap borders](https://getbootstrap.com/docs/5.3/utilities/borders/)"
    "foundation/interaction" -> "- [Tabweb interactions](https://docs.tabler.io/ui/utilities/interactions)"
    "foundation/utility" -> "- [Bootstrap utilities](https://getbootstrap.com/docs/5.3/utilities/)"
    "card" -> "- [Tabweb card](https://docs.tabler.io/ui/components/card)"
    "navbar" -> "- [Tabweb navbars](https://docs.tabler.io/ui/layout/navbars)"
    "tabs" -> "- [Tabweb navs and tabs](https://docs.tabler.io/ui/layout/navs-tabs)"
    else -> "- [Tabweb UI documentation](https://docs.tabler.io/ui)"
  }

  private fun readOverrides(): Map<String, TabwebCssClassOverride> {
    val file = manifestFile.orNull?.asFile ?: return emptyMap()
    if (!file.isFile) return emptyMap()
    return file.readLines()
      .asSequence()
      .filter { it.isNotBlank() && !it.startsWith("#") && !it.startsWith("class\t") }
      .map { line ->
        val fields = line.split('\t', limit = 5)
        require(fields.size == 5) {
          "Invalid Tabweb CSS manifest entry. Expected five tab-separated fields: $line"
        }
        fields[0] to TabwebCssClassOverride(
          category = fields[1].ifBlank { null },
          owner = fields[2].ifBlank { null },
          status = fields[3].ifBlank { null },
          description = fields[4].ifBlank { null },
        )
      }
      .toMap()
  }

  private data class TabwebCssClass(
    val name: String,
    val category: String,
    val owner: String,
    val status: String,
    val description: String?,
  )

  private data class TabwebCssClassOverride(
    val category: String?,
    val owner: String?,
    val status: String?,
    val description: String?,
  )

  private fun java.nio.file.Path.writeTextIfChanged(content: String) {
    if (!exists() || readText() != content) writeText(content)
  }

  private companion object {
    val CLASS_PATTERN = Regex("(?<![A-Za-z0-9_-])\\.([A-Za-z_-][A-Za-z0-9_-]*)")
    val LAYOUT_CLASSES = setOf("clearfix", "fixed-top", "fixed-bottom", "sticky-top", "sticky-bottom", "vstack", "hstack")
    val TYPOGRAPHY_CLASSES = setOf(
      "blockquote", "blockquote-footer", "caption-top", "display-1", "display-2", "display-3", "display-4", "display-5", "display-6",
      "h1", "h2", "h3", "h4", "h5", "h6", "initialism", "lead", "mark", "small", "strong",
    )
    val STATE_CLASSES = setOf("active", "disabled", "fade", "show", "collapse", "collapsed", "collapsing", "hiding")
  }
}

/** Registers the root task that creates the Tabweb CSS planning reference. */
class TabwebCssDocumentationPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    val extension = project.extensions.create("tabwebCssDocumentation", TabwebCssDocumentationExtension::class.java)
    project.tasks.register("generateTabwebCssDocumentation", GenerateTabwebCssDocumentationTask::class.java) {
      group = "documentation"
      description = "Generates the complete, version-pinned Tabweb CSS Markdown reference."
      tablerVersion.convention(extension.tablerVersion)
      outputDirectory.convention(project.layout.projectDirectory.dir("docs/tabler-css"))
      manifestFile.convention(project.layout.projectDirectory.file("docs/tabler-css-manifest.tsv"))
    }
  }
}
