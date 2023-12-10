package com.bangkit.catatmak.data.api.response

import com.google.gson.annotations.SerializedName

data class VerifyOTPResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("token")
	val token: String
)
