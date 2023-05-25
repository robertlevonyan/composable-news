package com.robertlevonyan.composable.newsapp.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class NewsItem(
    @SerialName("author")
    val author: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("url")
    val url: String,
    @SerialName("urlToImage")
    val image: String?,
    @SerialName("content")
    val content: String?,
    @SerialName("source")
    val source: NewsSource,
    @SerialName("published_at")
    val publishedAt: String,
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

@Parcelize
@Serializable
data class NewsSource(
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String,
) : Parcelable

@Serializable
data class NewsResponse(
    @SerialName("status")
    val status: String,
    @SerialName("totalResults")
    val totalResults: Int,
    @SerialName("articles")
    val articles: List<NewsItem>,
)
