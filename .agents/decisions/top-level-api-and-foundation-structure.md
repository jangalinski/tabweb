# Decision: Top-Level API and Foundation Structure

## Status

Accepted.

## Context

The library should be navigable by Tabler design concepts (`navbar`, `card`,
`badge`, and so on), rather than technical implementation categories. At the
same time, it needs a small set of shared Compose/Kobweb adapters, semantic
marker types, and Bootstrap-level primitives.

Without a strict boundary, either the package root accumulates unrelated
configuration and helper APIs, or `_foundation` becomes a catch-all package.

## Decision

The library package root exposes exactly one primary entry point:

```kotlin
KobwebTabler
```

`Tabweb` may provide a small, discoverable set of nested entry-point
functions, constants, and application helpers. No additional standalone
feature, configuration, CSS, or DOM API belongs directly in the package root.

Frequently used consumer entry points may be deliberately duplicated as
curated nested facades on `Tabweb`, for example
`KobwebTabler.App`. Those facades provide
convenience factories, constants, and composition entry points while the
authoritative types remain in their owning concept packages. A facade must
delegate to the concept API; it must not create a second model hierarchy.

All other types belong to either a named Tabler concept package or to
`_foundation`.

```text
com.github.jangalinski.tabweb
├── KobwebTabler.kt            # the sole top-level public entry point
├── _foundation/               # shared basis; not a Tabler UI feature
│   ├── Component.kt
│   ├── Style.kt
│   ├── Behavior.kt
│   ├── Size.kt
│   ├── compose/               # internal KDiv, KNav, attributes and modifiers
│   ├── css/                   # CSS-class and modifier infrastructure
│   └── bootstrap/             # genuine Bootstrap-level primitives only
├── _app/                      # application integration, state, theme, and page shell
├── navbar/
├── card/
├── badge/
├── button/
├── icon/
└── table/
```

`_foundation` is deliberately prefixed with `_`: it is shared infrastructure,
not a normal consumer-facing Tabler design element. It contains only concepts
without a more specific owner. There is no `_common` or `util` package.

`_app` is the public application-integration boundary. Its prefix makes clear
that it is application infrastructure rather than a Tabler design component.
It owns
`TablerSiteConfig`, `TabwebShellConfig`, application state, settings, theme,
page-shell layout code, and associated route data. These types are not peer
Tabler widgets such as `avatar` or `table`, and they do not belong in
`_foundation`, because they are public, Tabler-specific, and intentionally
opinionated about Kobweb application composition.

## Placement Rules

- A type with a specific Tabler owner belongs to that owner package, including
  its model, renderer, builders, and CSS class references.
- A reusable Compose/Kobweb DOM adapter belongs in `_foundation.compose`.
- A reusable semantic contract belongs in `_foundation`.
- A Bootstrap primitive belongs in `_foundation.bootstrap` only when it is not
  owned by a Tabler design element.
- A cross-concept helper must have a precise category under `_foundation`; if
  it cannot be named more precisely than `util` or `common`, it stays with its
  current concept owner.

## Consequences

- New work does not add APIs to the package root unless it extends
  `Tabweb` directly.
- User-facing convenience access is exposed through a small
  `Tabweb` facade rather than restoring root-level declarations.
- New work does not add to the technical `components`, `styles`, `layouts`, or
  `models` packages.
- Package migration is incremental and compatibility-preserving. Existing
  code is moved only while changing the relevant concept, or retained as a
  deprecated forwarding API when consumers may rely on it.
