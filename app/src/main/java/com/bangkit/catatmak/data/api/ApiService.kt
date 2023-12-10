package com.bangkit.catatmak.data.api

import com.bangkit.catatmak.data.api.response.OTPResponse
import com.bangkit.catatmak.data.api.response.VerifyOTPResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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

}