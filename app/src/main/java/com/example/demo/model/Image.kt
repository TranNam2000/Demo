package com.example.demo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val href: String? = null,
    val main_color: String? = null,
    val width: Int? = null,
    val height: Int? = null
) : Parcelable