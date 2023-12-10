package com.bangkit.catatmak.data.api

import android.util.Log
import androidx.lifecycle.liveData
import com.bangkit.catatmak.data.ResultState
import com.bangkit.catatmak.data.api.response.OTPResponse
import com.bangkit.catatmak.data.api.response.VerifyOTPResponse
import com.bangkit.catatmak.data.pref.UserModel
import com.bangkit.catatmak.data.pref.UserPreference
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class CatatmakRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun sendOTP(phone: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.sendOTP(phone)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, OTPResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }
    }

    fun verifyOTP(phone: String, otp: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.verifyOTP(phone, otp)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, VerifyOTPResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }
    }

    companion object {
        @Volatile
        private var instance: CatatmakRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference,
        ): CatatmakRepository =
            instance ?: synchronized(this) {
                instance ?: CatatmakRepository(apiService, userPreference)
            }.also { instance = it }

        fun resetInstance() {
            instance = null
        }
    }
}