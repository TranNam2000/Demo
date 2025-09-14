package com.example.demo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsItem(
    val avatar: Avatar? = null,
    val content: Content? = null,
    val content_type: String? = null,
    val description: String? = null,
    val document_id: String? = null,
    val images: List<Image>? = null,
    val origin_url: String? = null,
    val published_date: String? = null,
    val publisher: Publisher? = null,
    val title: String? = null
) : Parcelable {
    val id: String get() = document_id ?: ""
}