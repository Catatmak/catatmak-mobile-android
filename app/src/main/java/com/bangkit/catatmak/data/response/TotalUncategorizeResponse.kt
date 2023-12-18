package com.bangkit.catatmak.data.response

import com.google.gson.annotations.SerializedName

data class TotalUncategorizeResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: TotalUncategorizeData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class TotalUncategorizeData(

	@field:SerializedName("count")
	val count: Int
)
