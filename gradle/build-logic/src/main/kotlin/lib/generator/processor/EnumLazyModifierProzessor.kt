package com.github.jangalinski.tabweb.gradle.buildlogic.lib.generator.processor

import com.github.jangalinski.tabweb.gradle.buildlogic.lib.generator.TabwebContext
import com.github.jangalinski.tabweb.gradle.buildlogic.lib.generator.TabwebLibGenerator.Companion.MODIFIER_CLASS_NAMES
import com.github.jangalinski.tabweb.gradle.buildlogic.lib.generator.TabwebLibGenerator.Companion.MODIFIER_TYPE
import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.funBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.propertyBuilder
import io.toolisticon.kotlin.generation.builder.KotlinEnumClassSpecBuilder
import io.toolisticon.kotlin.generation.spi.processor.KotlinEnumClassSpecProcessor

@OptIn(ExperimentalKotlinPoetApi::class)
class EnumLazyModifierProzessor : KotlinEnumClassSpecProcessor<TabwebContext, String>(TabwebContext::class, String::class) {
  val rType = TypeVariableName("R")
  val modifierElement = ClassName("com.varabyte.kobweb.compose.ui", "Modifier", "Element")
  val operationLambda = LambdaTypeName.get(
    parameters = arrayOf(rType, modifierElement),
    returnType = rType
  )

  override fun invoke(
    context: TabwebContext,
    input: String,
    builder: KotlinEnumClassSpecBuilder
  ): KotlinEnumClassSpecBuilder = builder.apply {
    addSuperinterface(MODIFIER_TYPE)

    addProperty(
      propertyBuilder("modifier", MODIFIER_TYPE) {
        addModifiers(KModifier.PRIVATE)
        delegate(
          CodeBlock.builder()
            .beginControlFlow("lazy")
            .addStatement("%T.%M($input)", MODIFIER_TYPE, MODIFIER_CLASS_NAMES)
            .endControlFlow()
            .build()
        )
      }
    )

    addFunction(
      funBuilder("fold") {
        addModifiers(KModifier.OVERRIDE)
        addTypeVariable(rType)
        addParameter("initial", rType)
        addParameter("operation", operationLambda)
        returns(rType)
        addStatement("return modifier.fold(initial, operation)")
      }
    )

    addFunction(
      funBuilder("then") {
        addModifiers(KModifier.OVERRIDE)
        addParameter("other", MODIFIER_TYPE)
        returns(MODIFIER_TYPE)
        addStatement("return modifier.then(other)")
      }
    )
  }
}
