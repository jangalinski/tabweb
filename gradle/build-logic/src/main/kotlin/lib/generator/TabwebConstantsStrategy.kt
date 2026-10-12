package com.github.jangalinski.tabweb.gradle.buildlogic.lib.generator

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.enumClassBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.name.className
import io.toolisticon.kotlin.generation.spec.KotlinFileSpecList
import io.toolisticon.kotlin.generation.spec.toFileSpec
import io.toolisticon.kotlin.generation.spi.strategy.KotlinFileSpecListStrategy


@OptIn(ExperimentalKotlinPoetApi::class)
class TabwebConstantsStrategy : KotlinFileSpecListStrategy<TabwebContext, Unit>(contextType = TabwebContext::class, inputType = Unit::class) {

  override fun invoke(context: TabwebContext, input: Unit): KotlinFileSpecList {
    val colorsClass = className(context.foundationPackage, "TabwebColors")

    val colors = enumClassBuilder(colorsClass) {
      addConstructorProperty("value", String::class)

      context.colors.base.forEach {
        this.addEnumConstant(it.enumName, "%S", it.value)
      }
    }

    return KotlinFileSpecList.EMPTY + colors.build().toFileSpec()
  }


}
