package com.example.demo.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class NewsResponse(
    val items: List<NewsItem>? = listOf()
) : Parcelable
