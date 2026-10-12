@file:OptIn(ExperimentalKotlinPoetApi::class)

package com.github.jangalinski.tabweb.gradle.buildlogic.lib

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.MemberName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.name.className

const val PKG_ROOT = "com.github.jangalinski.tabweb"
const val PKG_FOUNDATION = "com.github.jangalinski.tabweb._foundation"

val TABWEB_COLOR = className(PKG_FOUNDATION, "Tabweb")
  .nestedClass("Color")

val TYPE_INITIALS = className(PKG_FOUNDATION, "Initials")
val TYPE_ICON = className("$PKG_ROOT.icon", "Icon")
val TYPE_TABWEB_ICON = className("$PKG_ROOT.icon", "TabwebIcon")
val TYPE_CSS_CLASS = className("$PKG_FOUNDATION.css", "CssClass")
val MEMBER_CSS_CLASS = MemberName("$PKG_FOUNDATION.css", "cssClass")
