package com.bangkit.catatmak.data.api.response

import com.google.gson.annotations.SerializedName

data class SummaryOutcomeIncomeResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val summaryOutcomeIncomeData: SummaryOutcomeIncomeData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class SummaryOutcomeIncomeData(

	@field:SerializedName("total_weekly")
	val totalWeekly: String,

	@field:SerializedName("total_today")
	val totalToday: String,

	@field:SerializedName("total_monthly")
	val totalMonthly: String
)
