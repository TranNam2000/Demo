package com.example.demo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Content(
    val href: String? = null,
    val preview_image: Image? = null,
    val duration: Int? = null
) : Parcelable