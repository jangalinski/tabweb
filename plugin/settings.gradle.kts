pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
  }
}

dependencyResolutionManagement {
  repositories {
    mavenCentral()
  }

  versionCatalogs {
    create("libs") {
      from(files("../gradle/libs.versions.toml"))
    }
  }
}

rootProject.name = "tabweb-plugin"

gradle.beforeProject {
  group = providers.environmentVariable("GROUP").orElse("com.github.jangalinski").get()
  version = providers.environmentVariable("VERSION").orElse("0.0.2-SNAPSHOT").get()
}
