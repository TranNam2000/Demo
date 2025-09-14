package com.example.demo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Publisher(
    val id: String? = null,
    val name: String? = null,
    val icon: String? = null
) : Parcelable
