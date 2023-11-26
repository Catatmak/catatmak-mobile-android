package com.bangkit.catatmak.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Insight(
    val createdAt: String,
    val insightTitle: String,
    val insightDesc: String,
    val isFavorite: String
) : Parcelable