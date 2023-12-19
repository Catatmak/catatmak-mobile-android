package com.bangkit.catatmak.data.api

import com.bangkit.catatmak.data.response.BulkDataResponse
import com.bangkit.catatmak.data.response.BulkResponseItem
import com.bangkit.catatmak.data.response.CreateFinancialResponse
import com.bangkit.catatmak.data.response.FinancialsDailyResponse
import com.bangkit.catatmak.data.response.FinancialsTodayResponse
import com.bangkit.catatmak.data.response.FinancialsTotalResponse
import com.bangkit.catatmak.data.response.GetChartsByDateResponse
import com.bangkit.catatmak.data.response.InsightResponse
import com.bangkit.catatmak.data.response.OCRResponse
import com.bangkit.catatmak.data.response.OTPResponse
import com.bangkit.catatmak.data.response.ProfileDataItem
import com.bangkit.catatmak.data.response.ProfileResponse
import com.bangkit.catatmak.data.response.SummaryOutcomeIncomeResponse
import com.bangkit.catatmak.data.response.TotalUncategorizeResponse
import com.bangkit.catatmak.data.response.UncategorizeResponse
import com.bangkit.catatmak.data.response.UpdateCategoryItem
import com.bangkit.catatmak.data.response.VerifyOTPResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
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

    @GET("financials/my?date=daily&type=outcome")
    suspend fun getOutcomeDaily(
    ): FinancialsDailyResponse

    @GET("financials/my?date=weekly&type=outcome")
    suspend fun getOutcomeWeekly(
    ): FinancialsDailyResponse

    @GET("financials/my?date=monthly&type=outcome")
    suspend fun getOutcomeMonthly(
    ): FinancialsDailyResponse

    @GET("financials/my?date=monthly&type=income")
    suspend fun getIncomeThisMonth(
    ): FinancialsDailyResponse

    @GET("financials/my")
    suspend fun getFinancialsCustomDate(
        @Query("date") date: String = "custom",
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("type") type: String,
    ): FinancialsTodayResponse

    @FormUrlEncoded
    @POST("financials")
    suspend fun createFinancial(
        @Field("title") title: String,
        @Field("price") price: String,
        @Field("category") category: String,
        @Field("type") type: String,
        @Field("created_at") createdAt: String,
    ): CreateFinancialResponse

    @GET("financials/{id}")
    suspend fun getOneFinancial(
        @Path("id") id: String,
    ): CreateFinancialResponse

    @DELETE("financials/{id}")
    suspend fun deleteFinancial(
        @Path("id") id: String,
    ): CreateFinancialResponse

    @FormUrlEncoded
    @PUT("financials/{id}")
    suspend fun updateFinancial(
        @Path("id") id: String,
        @Field("title") title: String,
        @Field("price") price: String,
        @Field("category") category: String,
        @Field("type") type: String,
        @Field("created_at") createdAt: String,
    ): CreateFinancialResponse

    @GET("financials/charts")
    suspend fun getChartsByDate(
        @Query("date") date: String
    ): GetChartsByDateResponse

    @GET("financials/charts/type")
    suspend fun getChartsByType(
        @Query("date") date: String
    ): GetChartsByDateResponse


    @Multipart
    @POST("ocr")
    suspend fun sendOCR(
        @Part file: MultipartBody.Part,
    ): OCRResponse

    @PUT("financials/bulk")
    suspend fun updateBulk(
        @Body bulk: List<BulkResponseItem>
    ): BulkDataResponse

    @PUT("financials/bulk")
    suspend fun updateCategory(
        @Body bulk: List<UpdateCategoryItem>
    ): BulkDataResponse

    @GET("uncategorize/total")
    suspend fun getTotalUncategorize(
    ): TotalUncategorizeResponse


    @GET("uncategorize")
    suspend fun getFinancialsUncategorize(
    ): UncategorizeResponse

    @GET("insight")
    suspend fun getInsight(
    ): InsightResponse

    @GET("profile/my")
    suspend fun getProfile(
    ): ProfileResponse

    @PUT("profile")
    suspend fun updateProfile(
        @Body profile: ProfileDataItem
    ): ProfileResponse
}