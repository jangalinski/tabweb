package com.github.jangalinski.tabweb.gradle.buildlogic.lib.generator

import com.github.jangalinski.tabweb.gradle.buildlogic.lib.*
import com.github.jangalinski.tabweb.gradle.buildlogic.lib.generator.processor.EnumLazyModifierProzessor
import com.github.jangalinski.tabweb.gradle.buildlogic.lib.model.TabwebColorModel
import com.github.jangalinski.tabweb.gradle.buildlogic.lib.model.TabwebColorModel.Social
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.KModifier
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.enumClassBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.interfaceBuilder
import io.toolisticon.kotlin.generation.builder.KotlinEnumClassSpecBuilder
import io.toolisticon.kotlin.generation.spec.KotlinFileSpecList
import io.toolisticon.kotlin.generation.spec.toFileSpec
import io.toolisticon.kotlin.generation.spi.processor.executeAll
import io.toolisticon.kotlin.generation.spi.strategy.KotlinFileSpecListStrategy

@ExperimentalKotlinPoetApi
class TabwebColorCssStrategy : KotlinFileSpecListStrategy<TabwebContext, Unit>(contextType = TabwebContext::class, inputType = Unit::class) {

  override fun invoke(context: TabwebContext, input: Unit): KotlinFileSpecList {
    val packageName = context.basePackage + "._foundation.modifier"
    val className = ClassName(packageName, "BackgroundColor")

    val backgroundColor = interfaceBuilder(className)
      .addSuperinterface(TABWEB_COLOR)
      .addModifiers(KModifier.SEALED)
      .addProperty("textFg", TYPE_CSS_CLASS)

    fun colorEnum(name: String, constants: List<TabwebColorModel>): KotlinEnumClassSpecBuilder {
      val enumBuilder = enumClassBuilder(name)
        .addSuperinterface(className)
        .addConstructorProperty("value", String::class) {
          addModifiers(KModifier.OVERRIDE)
        }
        .addConstructorProperty("initials", TYPE_INITIALS) {
          addModifiers(KModifier.OVERRIDE)
        }
        .addConstructorProperty("displayName", String::class) {
          addModifiers(KModifier.OVERRIDE)
        }

      if (name == "SOCIAL") {
        enumBuilder.addConstructorProperty("icon", TYPE_ICON)
      }

      enumBuilder.addConstructorProperty("textFg", TYPE_CSS_CLASS) {
        addModifiers(KModifier.OVERRIDE)
      }

      constants.forEach {
        val light = "LIGHT" == name
        when (it) {
          is Social -> {
            enumBuilder.addEnumConstant(
              it.enumName, "%S, %T(%S), %S, %T.%L, %M(%S)",
              "bg-${it.value}${if (light) "-lt" else ""}",
              TYPE_INITIALS, if (light) "${it.short}l" else it.short,
              if (light) "${it.name} Light" else it.name,
              TYPE_TABWEB_ICON, "TI_${it.icon.uppercase().replace("-", "_")}",
              MEMBER_CSS_CLASS, "text-${it.value}-fg"
            )
          }

          else -> {
            enumBuilder.addEnumConstant(
              it.enumName, "%S, %T(%S), %S, %M(%S)",
              "bg-${it.value}${if (light) "-lt" else ""}",
              TYPE_INITIALS, if (light) "${it.short}l" else it.short,
              if (light) "${it.name} Light" else it.name,
              MEMBER_CSS_CLASS, "text-${it.value}-fg"
            )
          }
        }
      }

      context.processors(EnumLazyModifierProzessor::class).executeAll(context, "value", enumBuilder)

      return enumBuilder
    }

    backgroundColor.addType(colorEnum("BASE", context.colors.base))
    backgroundColor.addType(colorEnum("LIGHT", context.colors.base.filter { it.hasLightMode }))
    backgroundColor.addType(colorEnum("GRAY", context.colors.gray))
    backgroundColor.addType(colorEnum("SOCIAL", context.colors.social))
    val semantic = colorEnum("SEMANTIC", context.colors.semantic)
    backgroundColor.addType(semantic)

    backgroundColor.addType(KotlinCodeGeneration.buildCompanionObject {
      addProperty("DEFAULT", className) {
        initializer("%T.%L", KotlinCodeGeneration.name.simpleClassName("SEMANTIC"), "TRANSPARENT")
      }
    })

    return KotlinFileSpecList.of(backgroundColor.build().toFileSpec())
  }
}
