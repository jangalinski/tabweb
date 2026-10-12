package com.github.jangalinski.tabweb.gradle.buildlogic.site.navigation

import ca.esmcelroy.schema2class.gradle.SchemaSpec
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject
import kotlin.jvm.java

/**
 * Generates Kotlin data classes from the public navigation JSON Schema.
 *
 * This task is intentionally separate from the site's handwritten navigation
 * parser. It provides an internal schema-codegen experiment without changing
 * the existing navigation generation path.
 */
abstract class GenerateNavigationSchemaCodeTask : DefaultTask(), () -> Unit {

  companion object {
    const val GROUP = "site"
    const val NAME = "generateNavigationSchemaCode"
    const val DESCRIPTION = "Generates Kotlin navigation schema data classes."
    const val PACKAGE_NAME = "com.github.jangalinski.tabweb.gradle.buildlogic.site.navigation.schema"
  }

  @get:InputFile
  @get:PathSensitive(PathSensitivity.RELATIVE)
  abstract val inputFile: RegularFileProperty

  @get:OutputDirectory
  abstract val outputDirectory: DirectoryProperty

  @get:Inject
  abstract val objectFactory: ObjectFactory

  @TaskAction
  override fun invoke() {
    val schema = objectFactory.newInstance(SchemaSpec::class.java, "navigation")
    schema.source.set(inputFile)
    schema.packageName.set(PACKAGE_NAME)
    schema.annotationMode.set("KOTLINX_SERIALIZATION")

    val generatorClass = Class.forName(
      "ca.esmcelroy.schema2class.gradle.Schema2ClassGenerator",
    )
    val generator = generatorClass.getField("INSTANCE").get(null)
    generatorClass
      .getMethod("generate", Iterable::class.java, java.io.File::class.java)
      .invoke(generator, listOf(schema), outputDirectory.get().asFile)

    logger.lifecycle(
      "Navigation schema classes generated from ${inputFile.get().asFile} into " +
        outputDirectory.get().asFile,
    )
  }
}
