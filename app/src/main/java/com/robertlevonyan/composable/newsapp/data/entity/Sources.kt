package com.robertlevonyan.composable.newsapp.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SourceItem(
  @SerialName("id")
  val id: String,
  @SerialName("name")
  val name: String?,
  @SerialName("description")
  val description: String?,
  @SerialName("url")
  val url: String,
  @SerialName("category")
  val category: String?,
  @SerialName("language")
  val language: String?,
  @SerialName("country")
  val country: String?,
)

@Serializable
data class SourceResponse(
  @SerialName("status")
  val status: String,
  @SerialName("sources")
  val sources: List<SourceItem>,
)
