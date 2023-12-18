package com.bangkit.catatmak.data.response

import com.google.gson.annotations.SerializedName

data class FinancialsTotalResponse(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("data")
    val financialsTotalData: FinancialsTotalData,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)

data class FinancialsTotalData(

    @field:SerializedName("total_income")
    val totalIncome: String,

    @field:SerializedName("total_outcome")
    val totalOutcome: String
)
