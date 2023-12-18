package com.bangkit.catatmak.data.response

import com.google.gson.annotations.SerializedName


data class BulkResponseItem(

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("category")
    val category: String,

    @field:SerializedName("price")
    val price: String,

    @field:SerializedName("image_url")
    val imageUrl: String,

    @field:SerializedName("image_name")
    val imageName: String,

    )
