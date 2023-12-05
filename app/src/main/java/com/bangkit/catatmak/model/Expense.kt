package com.bangkit.catatmak.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Expense(
    val dateTime: String,
    val total: String,
    val expensesAmount: String
) : Parcelable