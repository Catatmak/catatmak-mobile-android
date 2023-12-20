package com.bangkit.catatmak.data.response

import com.google.gson.annotations.SerializedName

data class UncategorizeResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: List<UncategorizeDataItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class UncategorizeDataItem(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,
)
