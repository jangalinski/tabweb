import com.varabyte.kobweb.gradle.core.util.importCss
import com.varabyte.kobweb.gradle.library.util.configAsKobwebLibrary
import dev.detekt.gradle.Detekt
import dev.detekt.gradle.extensions.DetektExtension
import dev.detekt.gradle.extensions.FailOnSeverity
import kotlinx.html.script
import kotlinx.html.style

val TABWEB = "tabweb"
val strictDetekt = providers.gradleProperty("tablerDetekt.strict")
  .map { value ->
    value.toBooleanStrictOrNull()
      ?: error("tablerDetekt.strict must be true or false, but was '$value'.")
  }
  .getOrElse(false)

plugins {
  `maven-publish`
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.kotlinx.serialization)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.detekt)
  alias(libs.plugins.dokka)
  id("buildlogic.dokka-markdown")
  alias(libs.plugins.kobweb.library)
  id("com.github.jangalinski.tabweb.buildlogic.kotlin-code-generation")

  id("buildlogic.tabweb-lib")

}

extensions.configure<DetektExtension> {
  config.setFrom(rootProject.file("gradle/detekt-rules/config.yml"))
  disableDefaultRuleSets = true
  failOnSeverity = if (strictDetekt) {
    FailOnSeverity.Warning
  } else {
    FailOnSeverity.Error
  }
}

dependencies {
  add("detektPlugins", "com.github.jangalinski.tabweb:detekt-rules")
}

tasks.named("check") {
  dependsOn(tasks.named<Detekt>("detektJsMainSourceSet"))
}

base {
  archivesName.set(TABWEB)
}

kotlin {
  configAsKobwebLibrary(includeServer = false)

  sourceSets {
    jsMain {
      kotlin.srcDir(tasks.named("generateLibCode"))
      dependencies {
        implementation(libs.compose.runtime)
        implementation(libs.compose.html.core)
        implementation(libs.kobweb.core)
        implementation(libs.kobweb.compose.js)
        implementation(libs.jetbrains.markdown)
        implementation(libs.kaml)
      }
    }

    jsTest.dependencies {
      implementation(kotlin("test-js"))
      implementation(libs.compose.html.test.utils)
      implementation(libs.test.assertk)
    }
  }
}

kobweb {
  library {
    index {
      head.add {
        style {
          importCss(
            url = "https://cdn.jsdelivr.net/npm/@tabler/core@${libs.versions.cdn.tabler.core.get()}/dist/css/tabler.min.css",
            layerName = TABWEB
          )
          importCss(
            url = "https://cdn.jsdelivr.net/npm/@tabler/core@${libs.versions.cdn.tabler.core.get()}/dist/css/tabler-vendors.min.css",
            layerName = TABWEB
          )
          importCss(
            url = "https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@${libs.versions.cdn.tabler.icons.get()}/dist/tabler-icons.min.css",
            layerName = TABWEB
          )
        }
        script {
          src = "https://cdn.jsdelivr.net/npm/@tabler/core@${libs.versions.cdn.tabler.core.get()}/dist/js/tabler.min.js"
        }
        script {
          src = "https://cdn.jsdelivr.net/npm/@tabler/core@${libs.versions.cdn.tabler.core.get()}/dist/libs/apexcharts/dist/apexcharts.min.js"
        }
      }
    }
  }
}

dokka {
  dokkaPublications.html {
    moduleName.set(TABWEB)
    moduleVersion.set(project.version.toString())
  }
  dokkaSourceSets.configureEach {
    includes.from(
      fileTree("src/jsMain/kotlin") {
        include("**/*.md")
      }
    )
    suppressedFiles.from(
      file("src/jsMain/kotlin/icon/TablerIcon.kt")
    )
  }
}

publishing {
  publications.withType<MavenPublication>().configureEach {
    artifactId = when (name) {
      "kotlinMultiplatform" -> TABWEB
      "js" -> "$TABWEB-js"
      else -> artifactId
    }
  }
}
