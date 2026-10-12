package com.github.jangalinski.tabweb.gradle.buildlogic.lib.model

import com.github.jangalinski.tabweb.gradle.buildlogic.BuildLogic.loadResource
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer


sealed interface TabwebColorModel : TabwebModel {
  val name: String
  val value: String
  val short: String
  val enumName: String get() = name.uppercase().replace("-", "_").replace(" ", "_")


  @Serializable
  data class Base(
    override val name: String,
    override val short: String,
    override val value: String = name.lowercase(),
    val hasLightMode: Boolean = true
  ) : TabwebColorModel

  @Serializable
  data class Semantic(
    override val name: String,
    override val short: String,
    override val value: String = name.lowercase(),
    val hasLightMode: Boolean = true
  ) : TabwebColorModel


  @Serializable
  data class Gray(
    val occupance: Int
  ) : TabwebColorModel {
    override val name: String = "Gray $occupance"
    override val short: String = "$occupance"
    override val value: String = "gray-$occupance"
  }

  @Serializable
  data class Social(
    override val name: String,
    override val short: String,
    override val value: String = name.lowercase(),
    val icon : String = "brand-$value"
  ) : TabwebColorModel

}

@Serializable
data class ColorsModel(
  val base: List<TabwebColorModel.Base>,
  val gray: List<TabwebColorModel.Gray>,
  val semantic: List<TabwebColorModel.Semantic>,
  val social: List<TabwebColorModel.Social>,
) : TabwebModel {

  companion object {
    fun load(resource: String = "tabler/colors.json"): ColorsModel {
      return loadModelFromJson(resource)
    }
  }

}

@OptIn(InternalSerializationApi::class)
inline fun <reified T : TabwebModel> loadModelsFromJson(jsonResource: String): List<T> = Json.decodeFromString(
  ListSerializer(T::class.serializer()),
  loadResource(jsonResource)
)

@OptIn(InternalSerializationApi::class)
inline fun <reified T : TabwebModel> loadModelFromJson(jsonResource: String): T = Json.decodeFromString(
  T::class.serializer(),
  loadResource(jsonResource)
)
