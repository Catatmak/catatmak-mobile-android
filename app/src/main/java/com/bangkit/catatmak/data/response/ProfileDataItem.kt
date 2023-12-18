package com.bangkit.catatmak.data.response

import com.google.gson.annotations.SerializedName

data class ProfileDataItem(

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("age")
	val age: String
)
