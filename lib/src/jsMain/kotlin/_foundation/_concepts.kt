package com.github.jangalinski.tabweb._foundation

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier

/**
 * Marker interface for Tabweb concepts. All Tabweb concepts are expected to implement this interface.
 */
interface TabwebConcept

/**
 * Marks Tabweb DSL receivers (all [TabwebComponentScope]) so nested layout blocks stay scoped to Tabweb-specific builders.
 */
@DslMarker
annotation class TabwebDsl


/**
 * Marker interface for interfaces that declare composable functions.
 * These interfaces are used to support feature propagation; they are implemented by
 * a data object in a component package but also (using `by` delegation), by the
 * `KobwebTabler` object in the root package.
 *
 * All public methods in implementing interfaces are expected to be annotated with `@Composable` and to be implemented by the data object in the component package.
 */
interface TabwebComposable : TabwebConcept

/**
 * Marker interface for Tabweb component scopes. All Tabweb component scopes are expected to be annotated with `@TabwebDsl` and to implement this interface.
 */
interface TabwebComponentScope: TabwebConcept

/**
 * Marker interface for Tabweb component DSLs.
 * A typical implementation is a data object in a component package that implements the corresponding composable interface.
 */
interface TabwebComponentDsl: TabwebConcept

/**
 * A functional interface for a composable component that can be invoked with or without a [Modifier].
 * Marks a tabweb-component. Encapsulates all type-safe properties and content
 * required to render a component, and provides a composable entry point for rendering the component into the current composition.
 *
 * Defines the base root container or foundational element of a UI component, for example
 * standalone elements (`accordion`, `card`, `btn`, `modal`, `table`).
 */
interface TabwebComponent : TabwebConcept {

  /**
   * Composable entry point for rendering the component into the current composition.
   *
   * @param modifier an optional [Modifier] to apply to the component
   */
  @Composable
  operator fun invoke(modifier: Modifier = Modifier)

}

/**
 * A functional interface for a composable component that can be invoked with or without a [Modifier].
 * Marks a tabweb-foundation-component. Encapsulates all type-safe properties and content
 * required to render a component, and provides a composable entry point for rendering the component into the current composition.
 *
 * Defines a foundational element that is used as a building block for other components, for example
 * `Link`, `Image`, `Text`, ...
 */
interface TabwebFoundationComponent : TabwebComponent

/**
 * Smaller [TabwebComponent]s that are mostly used as content elements or decoration.
 */
interface TabwebElement : TabwebComponent


interface TabwebTextComponent: TabwebFoundationComponent, TabwebValue<String> {
  val value: String

  override fun get() = value
}

/**
 * A functional interface that represents a supplier of a value.
 *
 * This interface is used to provide a way to generate or supply values on demand.
 *
 * @param T the type of value supplied by this supplier
 */
fun interface TabwebValue<T> : TabwebConcept {
  fun get(): T
}

sealed interface TabwebDesign : TabwebConcept

/**
 * Applies palette colors, theme tints, gradients, or semantic color accents,
 * for example contextual variants (`alert-{color}`, `btn-{color}`),
 * background tints (`bg-{color}`, `steps-{color}`), or trend indicators
 * (`text-green`, `text-red`).
 */
interface TabwebColor : TabwebDesign , TabwebValue<String> {

  /**
   * ClassName value used to apply the color to an element.
   */
  val value: String

  /**
   * Short initials used to represent the color, for example in a legend or key.
   */
  val initials: Initials

  /**
   * Display name of the color, for example "Azure" or "Red".
   */
  val displayName: String

  override fun get() = value
}

/**
 * Configures visual styling variants, border treatments, fills, backgrounds, or decorative presentations,
 * for example outlines and ghost buttons (btn-outline, badge-outline, btn-ghost),
 * alternate fills and borders (table-striped, card-dashed, alert-important),
 * or backdrop effects (modal-blur).
 */
interface TabwebStyle : TabwebDesign

/**
 * Scales component dimensions, padding, thickness, or aspect ratios, for example sizing scale variants (btn-sm, badge-lg, modal-xl),
 * track thickness (progress-lg), or aspect ratios (ratio-{ratio}).
 */
interface TabwebSize : TabwebDesign

/**
 * Marker interface for content adapters that take different [TabwebComponent]s and render them into a common content type.
 * Used to support content composition and propagation across different component types.
 */
interface TabwebContent : TabwebConcept

typealias ComposableContent = @Composable () -> Unit

interface TabwebModifier : TabwebValue<Modifier>

interface AsModifier : TabwebConcept {
  val modifier: Modifier
}
