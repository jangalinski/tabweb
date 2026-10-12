import org.gradle.language.jvm.tasks.ProcessResources

plugins {
  `java-gradle-plugin`
  `maven-publish`
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlinx.serialization)
  alias(libs.plugins.schema2class)
}

dependencies {
  implementation(gradleKotlinDsl())
  implementation(platform(libs.bom.kotlin.code.generation))
  implementation(libs.kotlin.code.generation)
  implementation(libs.kotlinx.serialization.json)
  compileOnly(libs.kotlin.gradle.plugin)
  testImplementation(kotlin("test-junit5"))
}

val navigationSchemaInput = layout.projectDirectory.file("../schema/navigation.schema.json")
schema2class {
  schemas {
    create("navigation") {
      source.set(navigationSchemaInput)
      packageName.set("com.github.jangalinski.tabweb.plugin.navigation.schema")
      annotationMode.set("KOTLINX_SERIALIZATION")
    }
  }
}

tasks.named<ProcessResources>("processResources") {
  from(navigationSchemaInput)
}

base {
  archivesName.set("tabweb-plugin")
}

gradlePlugin {
  plugins {
    create("tabweb") {
      id = "com.github.jangalinski.tabweb.plugin"
      implementationClass = "com.github.jangalinski.tabweb.plugin.TabwebPlugin"
      displayName = "Tabweb Gradle plugin"
      description = "Generates typed Tabweb navigation from navigation.json."
    }
  }
}

publishing {
  publications.withType<MavenPublication>().configureEach {
    artifactId = when (name) {
      "pluginMaven" -> "tabweb-plugin"
      else -> artifactId
    }
  }
}

tasks.test {
  useJUnitPlatform()
}
