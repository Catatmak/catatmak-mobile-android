package com.bangkit.catatmak.data.response

import com.google.gson.annotations.SerializedName

data class OCRResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: List<OCRDataItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class OCRDataItem(

	@field:SerializedName("id")
	val id: Int,

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
