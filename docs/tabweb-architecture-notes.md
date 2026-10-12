# Kobweb + Tabler Architecture Notes

> Context for continuing implementation with Codex.
>
> The project is a `tabweb` library whose goal is to expose the
> Tabler design system idiomatically in Kobweb / Compose for Web. Tabler
> is intentionally part of the public model; there is no requirement to
> abstract the library away from Tabler.

## 1. Goals and constraints

-   Build an idiomatic Kobweb API around Tabler.
-   A custom Kobweb `@App` root already exists.
-   Site-wide configuration currently contains concerns such as brand,
    navigation, and footer.
-   Tabler layout/theme variants should be changeable at runtime.
-   Tabler controls many of these variants through `data-*` attributes
    on the document `<html>` element.
-   Primary deployment target: statically exported Kobweb sites served
    from static hosting.
-   A directly clickable `file://` build would be useful, but is not a
    primary requirement.
-   Client-side JavaScript and runtime interactivity are acceptable on
    an otherwise statically hosted site.

## 2. Important distinction: Kobweb layout vs. Tabler layout settings

Do **not** conflate Kobweb `@Layout` with Tabler's layout/display
variants.

Kobweb `@Layout` defines the Compose structure surrounding a page:

``` text
@App
└── @Layout
    └── @Page
```

Tabler settings instead control how that existing structure is
displayed, usually through attributes on `<html>`, for example:

``` html
<html
  data-bs-theme="dark"
  data-bs-layout="boxed"
  data-bs-navbar-position="vertical"
>
```

Because `Layout` already has a specific meaning in Kobweb, avoid a
public type named simply `TabwebLayout` if it creates ambiguity. Prefer
names that describe the actual Tabler concern, such as
`TablerContainer`, `TablerPageVariant`, `NavbarPosition`, etc.

A change from vertical to horizontal navigation, boxed to fluid
presentation, or light to dark mode should normally **not** require
switching Kobweb `@Layout` implementations. The same scaffold can remain
composed while Tabler's attributes change its presentation.

## 3. Recommended architecture

Separate static/default configuration from mutable runtime state.

``` text
TablerConfig / SiteConfig
        │
        │ initial/default values
        ▼
  TablerAppState
        │
        │ Compose-observable state
        ▼
   TablerSettings
        │
        │ synchronized to DOM
        ▼
<html data-bs-...>
```

At the same time, the page composition remains:

``` text
@App
└── TablerAppState
    └── @Layout TablerPageLayout
        ├── Navbar
        ├── Page()
        └── Footer
```

### Responsibilities

**SiteConfig**

Site-specific, mostly static configuration:

-   Brand
-   Navigation model
-   Footer
-   Other site metadata

**TablerConfig**

Initial/default Tabler-specific UI configuration.

**TablerSettings**

Immutable snapshot of the current Tabler UI settings.

**TablerAppState**

Stable, mutable Compose state holder. Runtime changes are made here.

**DOM synchronization**

Maps `TablerSettings` directly to Tabler's expected HTML attributes.
Since this library explicitly targets Tabler, this does not need a
framework-neutral adapter abstraction.

## 4. Immutable settings model

A representative model:

``` kotlin
@Immutable
data class TablerSettings(
    val theme: TablerTheme = TablerTheme.System,
    val container: TablerContainer = TablerContainer.Default,
    val navbarPosition: NavbarPosition = NavbarPosition.Vertical,
    val navbarTheme: NavbarTheme = NavbarTheme.Default,
    val sidebar: SidebarMode = SidebarMode.Default,
    val stickyNavbar: Boolean = false,
)
```

Exact enum names and available values should follow the Tabler version
targeted by the library.

The important property is that `TablerSettings` is a value object.
Runtime updates produce a new snapshot using `copy(...)`.

Example:

``` kotlin
val next = current.copy(
    theme = TablerTheme.Dark,
    navbarPosition = NavbarPosition.Horizontal,
)
```

## 5. `@Immutable` and `@Stable`

Use Compose stability annotations according to their semantics.

`TablerSettings` is a good candidate for `@Immutable` because instances
do not mutate after construction:

``` kotlin
@Immutable
data class TablerSettings(
    val theme: TablerTheme,
    val navbarPosition: NavbarPosition,
    val container: TablerContainer,
)
```

`TablerAppState` is a good candidate for `@Stable`: the state-holder
object itself remains stable while its observable state can change.

``` kotlin
@Stable
class TablerAppState(
    initialSettings: TablerSettings,
) {
    var settings by mutableStateOf(initialSettings)
        private set

    fun update(transform: (TablerSettings) -> TablerSettings) {
        settings = transform(settings)
    }

    fun setTheme(theme: TablerTheme) {
        settings = settings.copy(theme = theme)
    }
}
```

`@Stable` does **not** make ordinary Kotlin properties observable.

This would be incorrect:

``` kotlin
@Stable
class TablerAppState {
    var theme = TablerTheme.Light // Not Compose-observable merely because of @Stable
}
```

Mutable public state that drives composition should use Compose state
mechanisms such as `mutableStateOf`.

Conceptually:

``` text
@Immutable TablerSettings
    value never mutates
    copy(...) creates a new snapshot

@Stable TablerAppState
    object identity can remain the same
    mutableStateOf(...) reports relevant changes to Compose
```

## 6. Config is initial state, not runtime state

The application's configuration can provide defaults:

``` kotlin
data object AppConfig {
    val tabler = TablerSettings(
        theme = TablerTheme.System,
        container = TablerContainer.Default,
        navbarPosition = NavbarPosition.Vertical,
    )

    val brand = ...
    val navigation = ...
    val footer = ...
}
```

However, avoid turning the config object itself into globally mutable
runtime state.

Prefer:

``` text
AppConfig.tabler
    ↓ initial value
TablerAppState
    ↓ runtime mutations
TablerSettings
```

This preserves a useful distinction between site configuration and the
user's current UI state.

## 7. App-level state

The Kobweb `@App` root is a natural place to initialize and provide the
state.

Representative pseudocode:

``` kotlin
@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
    val tablerState = remember {
        TablerAppState(AppConfig.tabler)
    }

    LaunchedEffect(tablerState.settings) {
        tablerState.settings.applyToDocument()
    }

    CompositionLocalProvider(
        LocalTablerAppState provides tablerState,
    ) {
        KobwebApp {
            content()
        }
    }
}
```

The exact `KobwebApp` wrapping depends on the existing project
structure; preserve whatever the actual application entry point
requires.

## 8. CompositionLocal API

Expose the app-wide Tabler state through Compose.

For example:

``` kotlin
val LocalTablerAppState = staticCompositionLocalOf<TablerAppState> {
    error("TablerAppState is not available")
}
```

Consumers can then access it from arbitrary descendants:

``` kotlin
val tabler = LocalTablerAppState.current
```

A convenience API such as this may also be appropriate:

``` kotlin
@Composable
fun rememberTablerAppState(): TablerAppState =
    LocalTablerAppState.current
```

Despite the name, this function would be retrieving the existing app
state rather than creating a new independent state holder. Consider
whether a name such as `tablerAppState()` or
`LocalTablerAppState.current` is clearer.

## 9. Runtime updates

Runtime changes should feel like normal Compose/Kotlin state updates.

Example:

``` kotlin
val tabler = LocalTablerAppState.current

Button(
    onClick = {
        tabler.update {
            copy(
                container = TablerContainer.Boxed,
                navbarPosition = NavbarPosition.Horizontal,
            )
        }
    }
) {
    Text("Switch layout")
}
```

Or focused methods can be provided:

``` kotlin
tabler.setTheme(TablerTheme.Dark)
```

Possible eventual ergonomic API:

``` kotlin
tabler.theme = TablerTheme.Dark
tabler.navbarPosition = NavbarPosition.Horizontal
tabler.sidebar = SidebarMode.Folded
```

If exposing mutable properties directly, ensure each property remains
backed by Compose-observable state and that the public API has a
coherent source of truth.

## 10. Synchronizing state with Tabler's `<html>` attributes

The library can directly encode Tabler's DOM contract.

Representative pseudocode:

``` kotlin
internal fun TablerSettings.applyToDocument() {
    document.documentElement?.apply {
        setAttribute("data-bs-layout", container.value)
        setAttribute("data-bs-navbar-position", navbarPosition.value)
        setAttribute("data-bs-theme", theme.resolvedValue)

        navbarTheme.attributeValue?.let {
            setAttribute("data-bs-navbar-theme", it)
        } ?: removeAttribute("data-bs-navbar-theme")

        sidebar.attributeValue?.let {
            setAttribute("data-bs-sidebar", it)
        } ?: removeAttribute("data-bs-sidebar")

        if (stickyNavbar) {
            setAttribute("data-bs-navbar", "sticky")
        } else {
            removeAttribute("data-bs-navbar")
        }
    }
}
```

The actual attribute/value mapping must be checked against the exact
Tabler version used by the project.

Because this is `tabweb`, there is no need for a generic
`UiSettings -> FrameworkAdapter -> DOM` abstraction. Tabler terminology
can be part of the public API and its attribute mapping can be an
internal implementation detail.

## 11. Kobweb `@Layout` remains the page scaffold

Build a common Kobweb layout for the Tabler page structure:

``` kotlin
@Layout
@Composable
fun TablerPageLayout(
    content: @Composable () -> Unit,
) {
    TablerPage {
        TablerNavbar()

        TablerPageWrapper {
            content()
            TablerFooter()
        }
    }
}
```

Pages then only provide their actual content:

``` kotlin
@Page
@Composable
fun DashboardPage() {
    PageHeader("Dashboard")

    PageBody {
        // ...
    }
}
```

The same `TablerPageLayout` should be capable of rendering
vertical/horizontal navigation, boxed/fluid presentation, dark/light
themes, sidebar variants, etc. by responding to the Tabler state /
resulting HTML attributes.

Do not create separate Kobweb layouts such as these merely for Tabler
display variants unless their actual composition structure differs
substantially:

``` text
VerticalLayout
HorizontalLayout
BoxedLayout
DarkLayout
```

## 12. Default layout for all pages

Kobweb supports package-level default layouts.

A site can therefore make the Tabler scaffold the default for its page
package:

``` kotlin
@file:Layout(".components.layout.TablerPageLayout")

package app.pages
```

This avoids annotating every individual page with the same layout.

Subpackages can use different defaults where needed.

This gives a useful division:

``` text
@App
    global Tabler config/state

@file:Layout(...)
    default structural scaffold for pages

@Page
    page-specific content

Tabler data-* attributes
    runtime visual/layout variants
```

## 13. Site-wide configuration

The existing site config can continue to hold site-level concerns:

``` kotlin
data class SiteConfig(
    val brand: BrandConfig,
    val navigation: NavigationConfig,
    val footer: FooterConfig,
)
```

It is reasonable for the app root / common page scaffold to consume
these values.

A conceptual combined setup might be:

``` kotlin
data object AppConfig {
    val site = SiteConfig(
        brand = ...,
        navigation = ...,
        footer = ...,
    )

    val tabler = TablerSettings(
        theme = TablerTheme.System,
        container = TablerContainer.Default,
        navbarPosition = NavbarPosition.Vertical,
    )
}
```

The important distinction remains:

-   `AppConfig.site`: site configuration.
-   `AppConfig.tabler`: initial/default Tabler settings.
-   `TablerAppState`: current mutable client-side state.

## 14. Theme persistence and first-paint behavior

Persisted settings such as dark mode may be stored in browser storage.

A naive implementation:

``` kotlin
@App
@Composable
fun AppEntry(...) {
    LaunchedEffect(Unit) {
        // Read localStorage
        // Apply theme/layout attributes
    }
}
```

can cause a visible flash:

``` text
HTML loads
    ↓
default light / vertical state
    ↓
Compose/JS starts
    ↓
localStorage is read
    ↓
dark / horizontal state is applied
```

This is particularly noticeable for dark mode.

The library should therefore consider a two-stage model:

``` text
Very early initialization
    ↓
resolve persisted/default Tabler settings
    ↓
apply critical <html> attributes
    ↓
initialize Compose TablerAppState
    ↓
normal reactive synchronization thereafter
```

The exact mechanism should be designed around Kobweb's generated
document / initialization facilities and the targeted Tabler version.

This optimization is important for visual quality but does not
invalidate the central `TablerAppState` architecture.

## 15. Static export

Static deployment is the primary target and does not conflict with this
architecture.

Conceptually:

``` text
Kobweb build/export
    ↓
static HTML + JS + CSS/assets
    ↓
static web server / Pages hosting
    ↓
browser loads site
    ↓
Compose application starts
    ↓
TablerAppState becomes active
    ↓
user can toggle theme/sidebar/layout variants
```

"Static" here means that no application server is required to render
requests dynamically. It does **not** imply that the resulting site must
contain no JavaScript or runtime state.

Therefore features such as these remain compatible with static hosting:

-   Dark-mode toggle
-   Sidebar collapse/fold
-   Horizontal/vertical navigation changes
-   Boxed/fluid variants
-   Persisting UI preferences in `localStorage`

## 16. `file://` support

Opening the generated output directly using `file://` would be
convenient, but it is explicitly a secondary/best-effort target.

Do not compromise the main architecture merely to guarantee `file://`.

Browsers can impose restrictions on locally opened resources, ES
modules, fetches, routing, and asset loading. The primary supported
deployment model should therefore be ordinary static HTTP hosting.

Desired priority:

``` text
1. Static export served by a static HTTP host   REQUIRED
2. Runtime Tabler interaction                   REQUIRED
3. Direct file:// operation                     NICE TO HAVE
```

## 17. Naming guidance

Avoid ambiguous terminology where Kobweb and Tabler use similar words.

Potential names:

``` kotlin
TablerSettings
TablerConfig
TablerAppState

TablerTheme
TablerContainer
TablerPageVariant
NavbarPosition
NavbarTheme
SidebarMode
```

Use `TabwebLayout` only if the intended meaning is unambiguous in the
actual API. Since Kobweb has `@Layout`, a more specific name is
preferable.

## 18. Current architectural direction

The intended architecture is approximately:

``` text
                        AppConfig
                       /         \
                      /           \
              SiteConfig       Tabler defaults
             /    |    \             |
          Brand  Nav  Footer          |
                                      v
@App --------------------------> TablerAppState
                                      |
                                      | CompositionLocal
                                      v
                         @Layout TablerPageLayout
                         /          |          \
                     Navbar       @Page       Footer
                                      |
                                      v
                                page content


TablerAppState
      |
      | immutable snapshots
      v
TablerSettings
      |
      | synchronize
      v
<html data-bs-theme="..."
      data-bs-layout="..."
      data-bs-navbar-position="..."
      ...>
```

## 19. Guidance for continued implementation

When continuing this implementation:

1.  Keep the API explicitly Tabler-oriented; do not introduce
    framework-neutral abstractions without a concrete need.
2.  Keep site configuration/defaults separate from current mutable UI
    state.
3.  Model current settings as immutable value objects.
4.  Model the app-level state holder as Compose-observable stable state.
5.  Keep one common Kobweb `@Layout` scaffold where Tabler variants only
    affect presentation.
6.  Use a package-level default `@file:Layout` where the site should use
    the Tabler scaffold globally.
7.  Synchronize runtime settings to the Tabler `data-*` attributes
    expected on `<html>`.
8.  Design theme/persisted-state initialization to minimize first-paint
    flashes.
9.  Preserve compatibility with Kobweb static export and ordinary static
    hosting.
10. Treat direct `file://` execution as best effort rather than a design
    constraint.
11. Verify exact Tabler attributes and allowed values against the Tabler
    version actually used before finalizing enums or DOM mappings.
12. Preserve the existing application's actual `@App` / `KobwebApp`
    entry structure when converting this pseudocode into production
    code.

## 20. References

These are the primary upstream documentation areas relevant to the
design:

-   Kobweb application root / `@App`:
    https://kobweb.varabyte.com/docs/concepts/foundation/application-root
-   Kobweb layouts / default layouts:
    https://kobweb.varabyte.com/docs/concepts/foundation/layouts
-   Kobweb project / static export documentation:
    https://kobweb.varabyte.com/docs/getting-started/kobweb-project
-   Tabler documentation: https://tabler.io/docs
-   Tabler changelog (verify current layout attributes/features):
    https://tabler.io/changelog
-   Compose stability documentation:
    https://developer.android.com/develop/ui/compose/performance/stability
