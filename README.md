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
├── site/               ← documentation/demo site exported to GitHub Pages
└── _examples/          ← standalone Kobweb apps that consume the library
    └── tagessieg/      ← example Kobweb app
```

`_examples` is a **separate Gradle build** — it has its own `settings.gradle.kts` and its own Gradle wrapper.
It is not a sub-project of the root build; it references the `:lib` project via a
[composite build](https://docs.gradle.org/current/userguide/composite_builds.html).

The root build contains two subprojects:

- `:lib` is the published library. Its artifact name remains `tabweb` for JitPack consumers.
- `:site` is the repository documentation site. GitHub Pages publishes it at `/tabweb/`.

GitHub Pages also publishes selected standalone examples below `/tabweb/examples/<example>/`. The first
published example is `/tabweb/examples/tagessieg/`.

### How the examples use the local library

The examples' shared version catalog declares a local development coordinate:

```kotlin
implementation("com.github.jangalinski:tabweb:0.0.2-SNAPSHOT")
```

Its `settings.gradle.kts` uses `includeBuild("../")` with an explicit dependency substitution to replace that
coordinate with the `:lib` project's source. Therefore, `_examples` never downloads the snapshot and does not need
`publishToMavenLocal`; library changes are compiled directly when you build an example.

This is intentional for development, but it also means `_examples` cannot prove that JitPack serves a release.
Use a separate project without this `includeBuild` substitution for that check.

### How to run

The repo uses [just](https://just.systems/) for task automation. All recipes are defined in `.justfile`.

```
just --list          # show all available recipes
```

#### Working with examples

```bash
just run tagessieg        # run dev server (static layout, dev env)
just export tagessieg     # export static site
just preview tagessieg    # export + mirror + serve at http://localhost:10102/tagessieg/
just stop                 # stop all local preview/dev servers
```

#### Working with the documentation site

```bash
just run-site             # run docs dev server (static layout, dev env)
just export-site          # export docs site
just preview-site         # export + serve at http://localhost:13131/
```

Backwards-compatible single-example aliases also exist:

```bash
just run-tagessieg
just export-tagessieg
just preview-tagessieg
just stop-tagessieg
```

> **Note:** Because `_examples` wires the library directly from source via `includeBuild("../")`,
> a `clean build` is only needed to force-recompile everything (e.g., after switching branches).
> Incremental compilation works normally — Gradle rebuilds only what changed.

### Produce and test a release

Before creating a Git tag, test the exact release version locally. Using a temporary Maven repository keeps the
test publication out of your normal `~/.m2` cache:

```bash
VERSION=0.0.2 ./gradlew :lib:publishToMavenLocal \
  -Dmaven.repo.local=/tmp/tabweb-m2
```

When that succeeds, create and push the matching release tag (replace the version for later releases):

```bash
git tag -a 0.0.2 -m "Release 0.0.2"
git push origin 0.0.2
```

Also compile the source-backed example to verify normal development usage:

```bash
./gradlew :lib:compileKotlinJs
./gradlew :site:compileKotlinJs
./gradlew -p _examples :tagessieg:compileKotlinJs
```

After pushing a release tag, JitPack publishes only `:lib` with Java 17 (see [`jitpack.yml`](jitpack.yml)).
The Gradle subproject is named `lib`, but the published artifact remains `tabweb`.

For a quick local verification using Tagessieg:

1. In `gradle/libs.versions.toml`, change `tabweb` from `0.0.2-SNAPSHOT` to the release version, for example
   `0.0.2`.
2. Temporarily comment out the `includeBuild("../") { ... }` block in `_examples/settings.gradle.kts`.
3. Run:

   ```bash
   ./gradlew -p _examples :tagessieg:compileKotlinJs --refresh-dependencies
   ```

4. Restore the snapshot version and the `includeBuild` block when finished.

`_examples` already declares the JitPack repository, so disabling the composite substitution makes Gradle download the
release. This check verifies JitPack resolution; with `includeBuild` enabled, the same build only verifies the local
source replacement.

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
