package com.robertlevonyan.composable.newsapp.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsItem(
    @SerializedName("author")
    val author: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlToImage")
    val image: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("source")
    val source: NewsSource,
    @SerializedName("published_at")
    val publishedAt: String,
) : Parcelable

@Parcelize
data class NewsSource(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
) : Parcelable

data class NewsResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val articles: List<NewsItem>,
)
