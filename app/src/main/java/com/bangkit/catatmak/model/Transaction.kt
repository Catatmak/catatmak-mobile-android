package com.bangkit.catatmak.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val createdAt: String,
    val itemName: String,
    val categoryName: String,
    val price: String,
    val isPlus: String
) : Parcelable