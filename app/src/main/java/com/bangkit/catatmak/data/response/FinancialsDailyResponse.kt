package com.bangkit.catatmak.data.response

import com.google.gson.annotations.SerializedName

data class FinancialsDailyResponse(

    @field:SerializedName("code")
	val code: Int,

    @field:SerializedName("data")
	val financialsData: List<FinancialsDailyDataItem>,

    @field:SerializedName("message")
	val message: String,

    @field:SerializedName("status")
	val status: String
)

data class FinancialsDailyDataItem(

	@field:SerializedName("endDate")
	val endDate: String,

	@field:SerializedName("count")
	val count: Int,

	@field:SerializedName("sum")
	val sum: Float,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("startDate")
	val startDate: String
)
