package com.bangkit.catatmak.data.response

import com.google.gson.annotations.SerializedName

data class GetChartsByDateResponse(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("data")
    val data: List<ChartOutcomeDataItem>,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)

data class ChartOutcomeDataItem(

    @field:SerializedName("x")
    val x: String,

    @field:SerializedName("y")
    val y: Float,

    @field:SerializedName("z")
    val z: Int
)
