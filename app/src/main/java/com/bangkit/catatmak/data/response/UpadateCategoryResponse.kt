package com.bangkit.catatmak.data.response

import com.google.gson.annotations.SerializedName


data class UpdateCategoryItem(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("category")
    val category: String,

    @field:SerializedName("price")
    val price: String,

    )
