import java.io.File

buildscript {
  repositories {
    gradlePluginPortal()
    mavenCentral()
  }
  dependencies {
    classpath("ca.esmcelroy.schema2class:schema2class-gradle-plugin:0.1.0")
  }
}

plugins {
  `kotlin-dsl`
  `java-gradle-plugin`
  id("org.jetbrains.kotlin.plugin.serialization") version embeddedKotlinVersion
}

dependencies {
  implementation(platform(libs.bom.kotlin.code.generation))

  implementation(libs.kotlinx.serialization.json)
  implementation(libs.kotlin.code.generation)
  implementation("ca.esmcelroy.schema2class:ca.esmcelroy.schema2class.gradle.plugin:0.1.0")
  implementation(libs.dokka.gradle.plugin)

  testImplementation(kotlin("test-junit5"))
  testImplementation(libs.test.assertk)
}

val navigationSchemaInput = layout.projectDirectory.file(
  "../../lib/src/jsMain/resources/navigation.schema.json",
)
val navigationSchemaOutput = layout.buildDirectory.dir("generated/source/tabweb")
val generateNavigationSchemaCode = tasks.register("generateNavigationSchemaCode") {
  inputs.file(navigationSchemaInput)
  outputs.dir(navigationSchemaOutput)

  doLast {
    val schema = objects.newInstance(
      ca.esmcelroy.schema2class.gradle.SchemaSpec::class.java,
      "navigation",
    )
    schema.source.set(navigationSchemaInput)
    schema.packageName.set(
      "com.github.jangalinski.tabweb.gradle.buildlogic.site.navigation.schema",
    )
    schema.annotationMode.set("KOTLINX_SERIALIZATION")

    val generatorClass = Class.forName(
      "ca.esmcelroy.schema2class.gradle.Schema2ClassGenerator",
    )
    val generator = generatorClass.getField("INSTANCE").get(null)
    generatorClass
      .getMethod("generate", Iterable::class.java, File::class.java)
      .invoke(generator, listOf(schema), navigationSchemaOutput.get().asFile)
  }
}

kotlin.sourceSets.named("main") {
  kotlin.srcDir(navigationSchemaOutput)
}

tasks.named("compileKotlin") {
  dependsOn(generateNavigationSchemaCode)
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
    create("tabwebSite") {
      id = "com.github.jangalinski.tabweb.buildlogic.site"
      implementationClass = "com.github.jangalinski.tabweb.gradle.buildlogic.site.TabwebSitePlugin"
    }
    create("tabwebIcons") {
      id = "com.github.jangalinski.tabweb.buildlogic.tabweb-icons"
      implementationClass = "com.github.jangalinski.tabweb.gradle.buildlogic.TabwebIconsPlugin"
    }
    create("tabwebCssDocumentation") {
      id = "com.github.jangalinski.tabweb.buildlogic.tabweb-css-documentation"
      implementationClass = "com.github.jangalinski.tabweb.gradle.buildlogic.TabwebCssDocumentationPlugin"
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
  systemProperty("tabweb.repoRoot", rootProject.projectDir.parentFile.parentFile.absolutePath)
  testLogging {
    showStandardStreams = true
  }
}
