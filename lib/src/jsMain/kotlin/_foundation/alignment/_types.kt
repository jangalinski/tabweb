package com.github.jangalinski.tabweb._foundation.alignment

/**
 * Orientation is a concept - left/right, top/bottom, horizontal/vertical - that is used to describe the direction of a component or layout
 */
sealed interface Orientation

/**
 * Horizontal orientation is a concept - left/right, start/end - that is used to describe the direction of a component or layout
 */
sealed interface Horizontal : Orientation

/**
 * Vertical orientation is a concept - top/bottom - that is used to describe the direction of a component or layout
 */
sealed interface Vertical : Orientation

/**
 * Direction is a concept - 8 directions - that is used to describe the direction of a component or layout
 */
sealed interface Direction : Orientation

/**
 * CardinalDirection is a concept - 4 directions - that is used to describe the direction of a component or layout
 */
sealed interface CardinalDirection : Direction
