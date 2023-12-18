package com.bangkit.catatmak.data.response

import com.google.gson.annotations.SerializedName

data class InsightResponse(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("data")
    val data: List<InsightDataItem>,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)

data class InsightDataItem(

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("title")
    val title: String
)
