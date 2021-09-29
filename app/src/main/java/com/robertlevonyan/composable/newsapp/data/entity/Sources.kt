package com.robertlevonyan.composable.newsapp.data.entity

import com.google.gson.annotations.SerializedName

data class SourceItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("url")
    val url: String,
    @SerializedName("category")
    val category: String?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("country")
    val country: String?,
)

data class SourceResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("sources")
    val sources: List<SourceItem>,
)
