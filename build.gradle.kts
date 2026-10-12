import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import com.github.jangalinski.tabweb.gradle.buildlogic.TabwebCssDocumentationExtension

plugins {
  id("com.github.jangalinski.tabweb.buildlogic.tabweb-css-documentation")
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.compose.compiler) apply false
  alias(libs.plugins.detekt) apply false
  alias(libs.plugins.dokka) apply false
  alias(libs.plugins.jetbrains.compose) apply false
  alias(libs.plugins.kobweb.application) apply false
  alias(libs.plugins.kobweb.library) apply false
}

extensions.configure<TabwebCssDocumentationExtension> {
  tablerVersion.set(libs.versions.cdn.tabler.core)
}

allprojects {
  group = providers.environmentVariable("GROUP").orElse("com.github.jangalinski").get()
  version = providers.environmentVariable("VERSION").orElse("0.0.2-SNAPSHOT").get()
}

plugins.withType<YarnPlugin> {
  rootProject.extensions.getByType<YarnRootExtension>().lockFileDirectoryProperty =
    rootProject.file("gradle/kotlin-js-store")
}
