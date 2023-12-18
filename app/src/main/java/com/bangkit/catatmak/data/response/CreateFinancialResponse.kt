package com.bangkit.catatmak.data.response

import com.google.gson.annotations.SerializedName

data class CreateFinancialResponse(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("data")
    val data: CreateFinancialData,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)

data class CreateFinancialData(

    @field:SerializedName("acknowledged")
    val acknowledged: Boolean,

    @field:SerializedName("insertedId")
    val insertedId: String
)
