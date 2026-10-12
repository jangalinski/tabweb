package com.github.jangalinski.tabweb.image

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb._foundation.Image
import com.github.jangalinski.tabweb._foundation.css.ClassNames
import com.github.jangalinski.tabweb._foundation.compose.KImg
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.attr
import com.varabyte.kobweb.compose.ui.modifiers.classNames

/**
 * Renders a Tabweb image or inline SVG with the icon sizing used by the shared shell.
 *
 * The helper keeps icon rendering consistent across navbar, breadcrumbs, and any other
 * shared layout elements that need small inline icons.
 */
@Composable
internal fun renderImage(image: Image?, defaultAlt: String, className: String) {
  val size = when (className) {
    ClassNames.navbarBrandImage -> 40
    ClassNames.navItemIcon -> 16
    ClassNames.dropdownItemIcon -> 16
    else -> null
  }
  when (image) {
    null -> Unit
    is Image.None -> Unit
    is Image.Resource -> KImg(
      src = image.url.get(),
      alt = image.altText ?: defaultAlt,
      modifier = imageModifier(className, size),
    )
    is Image.InlineSvg -> KImg(
      src = svgDataUri(image.svg),
      alt = image.altText ?: defaultAlt,
      modifier = imageModifier(className, size),
    )
  }
}

private fun imageModifier(className: String, size: Int?): Modifier = Modifier
  .classNames(
    className,
    if (
      className == ClassNames.navbarBrandImage ||
      className == ClassNames.navItemIcon ||
      className == ClassNames.dropdownItemIcon
    ) ClassNames.me2 else "",
  )
  .then(size?.let { Modifier.attr("width", it.toString()).attr("height", it.toString()) } ?: Modifier)

/**
 * Converts inline SVG markup into a data URI so it can be used as an image source.
 */
internal fun svgDataUri(svg: String): String =
  "data:image/svg+xml;charset=UTF-8,${encodeURIComponent(svg)}"

@Suppress("UnsafeCastFromDynamic")
private fun encodeURIComponent(value: String): String = js("encodeURIComponent(value)") as String
