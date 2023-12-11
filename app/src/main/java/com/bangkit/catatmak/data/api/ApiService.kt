package com.bangkit.catatmak.data.api

import com.bangkit.catatmak.data.api.response.FinancialsTodayResponse
import com.bangkit.catatmak.data.api.response.FinancialsTotalResponse
import com.bangkit.catatmak.data.api.response.OTPResponse
import com.bangkit.catatmak.data.api.response.SummaryOutcomeIncomeResponse
import com.bangkit.catatmak.data.api.response.VerifyOTPResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("send-otp")
    suspend fun sendOTP(
        @Field("phone") phone: String,
    ): OTPResponse

    @FormUrlEncoded
    @POST("verify-otp")
    suspend fun verifyOTP(
        @Field("phone") phone: String,
        @Field("otp") otp: String,
    ): VerifyOTPResponse

    @GET("financials/my?date=today")
    suspend fun getAllFinancialsToday(
    ): FinancialsTodayResponse

    @GET("financials/my/summary")
    suspend fun getSummaryOutcomeIncome(
        @Query("type") type: String,
    ): SummaryOutcomeIncomeResponse

    @GET("financials/my/total")
    suspend fun getFinancialsTotal(
    ): FinancialsTotalResponse
}