package com.bangkit.catatmak.data.response

import com.google.gson.annotations.SerializedName

data class SummaryOutcomeIncomeResponse(

    @field:SerializedName("code")
	val code: Int,

    @field:SerializedName("data")
	val summaryOutcomeIncomeData: List<SummaryOutcomeIncomeDataItem>,

    @field:SerializedName("message")
	val message: String,

    @field:SerializedName("status")
	val status: String
)

data class SummaryOutcomeIncomeDataItem(

	@field:SerializedName("total")
	val total: String,

	@field:SerializedName("count")
	val count: Int,

	@field:SerializedName("title")
	val title: String
)
