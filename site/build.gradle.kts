import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication

val useLocalTabweb =
  providers.gradleProperty("site.useLocalTabweb")
    .map { value ->
      value.toBooleanStrictOrNull()
        ?: error("site.useLocalTabweb must be true or false, but was '$value'.")
    }
    .getOrElse(true)

plugins {
  id("com.github.jangalinski.tabweb.buildlogic.site")
  id("com.github.jangalinski.tabweb.buildlogic.site-preview")
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.jetbrains.compose)
  alias(libs.plugins.kobweb.application)
  alias(libs.plugins.kobwebx.markdown)
}

kobweb {
  pagesPackage = "com.github.jangalinski.tabweb.site.pages"

  app {
    index {
      description.set("Tabweb documentation and examples")
    }
  }

  markdown {
    defaultLayout.set("com.github.jangalinski.tabweb.site.MarkdownTablerLayout")
    defaultPackage.set(".com.github.jangalinski.tabweb.site.pages")
  }
}

kotlin {
  configAsKobwebApplication()

  sourceSets {
    commonMain.dependencies {
      implementation(libs.kobweb.core)
    }

    jsMain.dependencies {
      if (useLocalTabweb) {
        implementation(project(":lib"))
      } else {
        implementation(libs.tabweb)
      }
      implementation(libs.compose.runtime)
      implementation(libs.compose.html.core)
      implementation(libs.kobweb.compose.js)
      implementation(libs.kobwebx.markdown)
    }

    jsMain {
      kotlin.srcDir(tasks.named("generateNavigation"))
    }
  }
}

// Ignores duplicate resource files (keeps the first occurrence) when multiple sources provide the same file path (e.g. favicon.ico).
tasks.named<Copy>("jsProcessResources") {
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
