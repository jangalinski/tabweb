# Agent Instructions

This repository is a Kotlin Multiplatform Kobweb component library with a Tagessieg example app.

## Required Context

- Always read and apply `.agents/**/*.md` before making non-trivial changes.
- Treat `.agents` as required project context, not scratch space. Do not delete, move, or ignore it.
- Local decisions in `.agents/decisions/` are authoritative for their topic unless the user explicitly changes direction.
- Shared conventions in `.agents/shared/` apply together with this file.
- If `.agents` is missing or unreadable, stop and report that required agent context is unavailable.

## Working Rules

- Preserve existing public APIs unless the user explicitly asks for a breaking change.
- Keep changes small and aligned with the existing Kotlin/Compose/Kobweb style.
- Use KDoc for new public declarations.
- Do not introduce Python helper scripts for local automation. Use Kotlin Gradle build logic first, then expose user-facing commands through `just` when useful.
- Use `.agents/bin/gh-agent` for GitHub CLI access to this repository. It loads local GitHub auth and defaults commands to `jangalinski/tabweb`, keeping the command prefix stable for agent approval rules.
- Prefer `rg` for repository search.
- Do not revert existing user or agent work in the worktree unless explicitly requested.

## Table And Pagination Notes

- `TabwebTable(data: TabwebTableData)` is the data/configuration API and must remain backward compatible.
- Rich structured table cells belong in the sealed `TabwebTableCell` model.
- Arbitrary composable cell content belongs in the `TabwebTable { header { } row { cell { } } }` DSL overload.
- Pagination is a table-card sub-feature. Do not add a standalone pagination component unless the user explicitly changes that decision.
- Pagination must work in a generated static site without a backend server. This means client-side state and `onPageChange` handlers are expected; pure HTML with JavaScript disabled is not a supported interactive mode.

## Verification

Use `.agents/bin/gradlew-agent` for Gradle verification. It runs the repository Gradle wrapper with
`GRADLE_USER_HOME=/private/tmp/tabweb-gradle` and `--no-daemon`, keeping the command prefix stable for agent
approval rules.

- Library browser tests:

```bash
.agents/bin/gradlew-agent jsBrowserTest --console=plain
```

- Site compile:

```bash
.agents/bin/gradlew-agent :site:compileKotlinJs --console=plain
```

- Site static export:

```bash
.agents/bin/gradlew-agent :site:kobwebExport --console=plain
```

- Whitespace check:

```bash
git diff --check
```
