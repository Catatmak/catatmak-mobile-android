package com.bangkit.catatmak.data.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: ProfileData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class ProfileData(

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("membership")
	val membership: Membership,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("age")
	val age: String
)

data class Membership(

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("status")
	val status: String
)
