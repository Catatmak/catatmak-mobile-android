package com.bangkit.catatmak.data.response

import com.google.gson.annotations.SerializedName

data class OTPResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)
