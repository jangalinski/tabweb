package com.github.jangalinski.tabweb._foundation.size




/**
 * The base interface for all sizes.
 *
 * Implementations:
 *
 * * SMALL_X
 * * SMALL
 * * MEDIUM
 * * LARGE
 * * LARGE_X
 * * LARGE_XX
 *
 */
sealed interface Size

/**
 * A size that is at least medium.
 *
 * Implementations:
 *
 * * MEDIUM
 * * LARGE
 */
sealed interface MediumToLarge : Size

/**
 * A size that is at least small.
 *
 * Implementations:
 *
 * * SMALL
 * * MEDIUM
 * * LARGE
 */
sealed interface SmallToLarge : MediumToLarge

/**
 * A size that is at least small.
 *
 * Implementations:
 *
 * * SMALL
 * * MEDIUM
 * * LARGE
 * * LARGE_X
 */
sealed interface SmallToLargeX : SmallToLarge

val smallToLargeX: Array<SmallToLargeX> = arrayOf(SMALL, MEDIUM, LARGE, LARGE_X)
