# <img src=".idea/icon.svg" alt="" height="32" style="vertical-align: -0.18em;"> tabweb

[![tabweb](https://img.shields.io/badge/pages-tabweb-blue?logo=github&logoColor=white)](https://jangalinski.github.io/tabweb/)
[![Detekt](https://github.com/jangalinski/tabweb/actions/workflows/detekt.yml/badge.svg)](https://github.com/jangalinski/tabweb/actions/workflows/detekt.yml)
[![JitPack](https://jitpack.io/v/jangalinski/tabweb.svg)](https://jitpack.io/#jangalinski/tabweb)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.4.20-blue?logo=kotlin)](https://kotlinlang.org/)
[![Compose](https://img.shields.io/badge/compose-1.12.1-blue)](https://github.com/JetBrains/compose-multiplatform)
[![Kobweb](https://img.shields.io/badge/kobweb-0.25.1-blue)](https://kobweb.varabyte.com/)
[![tabler.io](https://img.shields.io/badge/tabler-1.6.1-blue)](https://tabler.io/)
[![API Docs](https://img.shields.io/badge/API_Reference-grey?logo=readthedocs)](https://jangalinski.github.io/tabweb/docs/)

> Build a Kobweb page that is either dynamically served or statically generated using Tabler layout and charts.

Original tabweb source code is licensed under the Apache License,
Version 2.0. Third-party materials and references retain their respective
licenses; see [NOTICE](NOTICE).


- a dynamic page that runs in the browser, or
- a statically generated page that is exported during build time

while still using the Tabler layout system and charting primitives.

## Use the library

This library is published from Git tags through [JitPack](https://jitpack.io/). Add JitPack and the released
library to a consuming Kotlin project:

```kotlin
dependencyResolutionManagement {
  repositories {
    mavenCentral()
    maven {
      url = uri("https://jitpack.io")
    }
  }
}

dependencies {
  implementation("com.github.jangalinski:tabweb:0.0.2")
}
```

The optional Gradle plugin generates typed navigation from `src/jsMain/resources/navigation.json`:

```kotlin
plugins {
  id("com.github.jangalinski.tabweb.plugin") version "0.0.2"
}

tabweb {
  navigation.packageName.set("com.example.site")
  navigation.objectName.set("GeneratedNavigation")
}
```

It registers `generateNavigation` and adds the generated Kotlin source to the `jsMain` source set of a Kotlin
Multiplatform project. The generated object exposes a `data` property containing the library's typed
`TabwebNavigation` model.

The repository is public: consumers and GitHub Actions do not need JitPack credentials.

### Development snapshots

JitPack can build the latest commit on a branch. To use the current `main` branch instead of a tagged release:

```kotlin
implementation("com.github.jangalinski:tabweb:main-SNAPSHOT")
```

Snapshots are changing dependencies, so refresh Gradle's dependency cache when checking a new commit:

```bash
./gradlew compileKotlinJs --refresh-dependencies
```

For a reproducible pre-release check, depend on a specific commit hash instead. See [JitPack's snapshot
documentation](https://docs.jitpack.io/intro/#snapshots) for both forms.


## Build Locally

### Project layout

```
tabweb/
├── build.gradle.kts    ← shared container build
├── lib/                ← published Kobweb Tabler library
├── plugin/             ← published Tabweb Gradle plugin
└── site/               ← documentation, demos, and examples exported to GitHub Pages
```

The root build contains two subprojects:

- `:lib` is the published library. Its artifact name remains `tabweb` for JitPack consumers.
- `plugin/` is the published Gradle plugin. It is a composite build so the site can use the local plugin while
  JitPack publishes it with the same version as `:lib`.
- `:site` contains the repository documentation, demos, and examples. GitHub Pages publishes it at `/tabweb/`.

### How to run

The repo uses [just](https://just.systems/) for task automation. All recipes are defined in `.justfile`.

```
just --list          # show all available recipes
```

```bash
just run-site             # run docs dev server (static layout, dev env)
just export-site          # export docs site
just preview-site         # export + serve at http://localhost:13131/
```

### Release to JitPack

JitPack publishes this library from Git tags. There is no upload command: pushing the `0.0.2` tag is what makes
JitPack build the release. JitPack provides `VERSION=0.0.2` while running the build, and [`jitpack.yml`](jitpack.yml)
publishes only the `:lib` project with Java 17.

Before creating the tag, verify the build and publish the exact release version to a temporary local Maven repository:

```bash
VERSION=0.0.2 ./gradlew :lib:publishToMavenLocal \
  -Dmaven.repo.local=/tmp/tabweb-m2
```

Then create and push the matching annotated tag:

```bash
git tag -a 0.0.2 -m "Release 0.0.2"
git push origin 0.0.2
```

Optionally create a GitHub Release from the `0.0.2` tag. JitPack will build the tag when the dependency is first
requested. Monitor the build at:

<https://jitpack.io/#jangalinski/tabweb/0.0.2>

or inspect its log directly:

<https://jitpack.io/com/github/jangalinski/tabweb/0.0.2/build.log>

The released dependency coordinate is:

```kotlin
implementation("com.github.jangalinski:tabweb:0.0.2")
```

Before tagging, it is also useful to compile the source-backed example and documentation site:

```bash
./gradlew :lib:compileKotlinJs
./gradlew :site:compileKotlinJs
```

After pushing a release tag, JitPack publishes `:lib` and the standalone `plugin/` build with Java 17 (see
[`jitpack.yml`](jitpack.yml)). Both use the same `VERSION` supplied by JitPack.

---

## References:

- Kobweb: https://kobweb.varabyte.com/docs/concepts/foundation/
  - [Kobweb](https://github.com/varabyte/kobweb) `library` template. This template is useful if you want to create a re-usable library that can be consumed by other Kobweb projects. The
    biggest difference between a Kobweb library and a Kobweb application is that the library applies the
    `com.varabyte.kobweb.library` Gradle plugin instead in its build script.

- Tabler docs: https://tabler.io/docs
  - Tabler Demo: https://tabler.io/admin-template/preview 
  - Tabler icons: https://tabler.io/icons
- ApexCharts docs: https://apexcharts.com/docs/
