package com.bangkit.catatmak.data.response

import com.google.gson.annotations.SerializedName

data class FinancialsTodayResponse(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("data")
    val financialsData: List<FinancialsTodayDataItem>,

    @field:SerializedName("meta")
    val meta: Meta,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)

data class FinancialsTodayDataItem(

    @field:SerializedName("price")
    val price: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("category")
    val category: String
)

data class Meta(

    @field:SerializedName("totalOutcomeToday")
    val totalOutcomeToday: String
)
