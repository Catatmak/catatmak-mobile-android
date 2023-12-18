package com.bangkit.catatmak.data.response

import com.google.gson.annotations.SerializedName

data class BulkDataResponse(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: DataItem,

    )
data class DataItem(

    @field:SerializedName("upsertedId")
    val upsertedId: Any,

    @field:SerializedName("upsertedCount")
    val upsertedCount: Int,

    @field:SerializedName("acknowledged")
    val acknowledged: Boolean,

    @field:SerializedName("modifiedCount")
    val modifiedCount: Int,

    @field:SerializedName("matchedCount")
    val matchedCount: Int
)

