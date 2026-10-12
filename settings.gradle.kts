dependencyResolutionManagement {
  repositories {
    mavenLocal {
      content {
        includeGroupByRegex("io\\.toolisticon.*")
      }
    }
    mavenCentral()
    maven(url = "https://jitpack.io")
    google()
  }
}

pluginManagement {
  repositories {
    mavenLocal {
      content {
        includeGroupByRegex("io\\.toolisticon.*")
      }
    }
    gradlePluginPortal()
    mavenCentral()
    google()
  }

  includeBuild("gradle/build-logic")
  includeBuild("plugin")
}

includeBuild("gradle/detekt-rules")

rootProject.name = "tabweb"

include(":lib")
include(":site")
