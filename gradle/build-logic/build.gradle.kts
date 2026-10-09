plugins {
  `kotlin-dsl`
  `java-gradle-plugin`
  id("org.jetbrains.kotlin.plugin.serialization") version embeddedKotlinVersion
}

dependencies {
  implementation(platform(libs.bom.kotlin.code.generation))

  implementation(libs.kotlinx.serialization.json)
  implementation(libs.kotlin.code.generation)
  implementation(libs.dokka.gradle.plugin)

  testImplementation(kotlin("test-junit5"))
  testImplementation(libs.test.assertk)
}

configurations.all {
  // TODO - only for SNAPSHOTS
  resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

gradlePlugin {
  plugins {
    create("tagessiegPreview") {
      id = "com.github.jangalinski.tabweb.buildlogic.tagessieg-preview"
      implementationClass = "com.github.jangalinski.tabweb.gradle.buildlogic.ExamplePreviewPlugin"
    }
    create("sitePreview") {
      id = "com.github.jangalinski.tabweb.buildlogic.site-preview"
      implementationClass = "com.github.jangalinski.tabweb.gradle.buildlogic.SitePreviewPlugin"
    }
    create("tablerIcons") {
      id = "com.github.jangalinski.tabweb.buildlogic.tabler-icons"
      implementationClass = "com.github.jangalinski.tabweb.gradle.buildlogic.TablerIconsPlugin"
    }
    create("tablerCssDocumentation") {
      id = "com.github.jangalinski.tabweb.buildlogic.tabler-css-documentation"
      implementationClass = "com.github.jangalinski.tabweb.gradle.buildlogic.TablerCssDocumentationPlugin"
    }
    create("kotlinCodeGeneration") {
      id = "com.github.jangalinski.tabweb.buildlogic.kotlin-code-generation"
      implementationClass = "com.github.jangalinski.tabweb.gradle.buildlogic.generation.KotlinCodeGenerationPlugin"
    }

    create("tabwebLib") {
      id = "buildlogic.tabweb-lib"
      implementationClass = "com.github.jangalinski.tabweb.gradle.buildlogic.lib.TabwebLibPlugin"
    }
    create("dokkaMarkdown") {
      id = "buildlogic.dokka-markdown"
      implementationClass = "com.github.jangalinski.tabweb.gradle.buildlogic.dokka.DokkaMarkdownPlugin"
    }
  }
}

tasks.test {
  useJUnitPlatform()
  testLogging {
    showStandardStreams = true
  }
}
