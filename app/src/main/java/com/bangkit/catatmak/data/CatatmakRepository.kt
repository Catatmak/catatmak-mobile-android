package com.bangkit.catatmak.data

import android.util.Log
import androidx.lifecycle.liveData
import com.bangkit.catatmak.BuildConfig
import com.bangkit.catatmak.data.api.ApiConfig
import com.bangkit.catatmak.data.api.ApiService
import com.bangkit.catatmak.data.response.CreateFinancialResponse
import com.bangkit.catatmak.data.response.FinancialsDailyResponse
import com.bangkit.catatmak.data.response.FinancialsTodayResponse
import com.bangkit.catatmak.data.response.FinancialsTotalResponse
import com.bangkit.catatmak.data.response.OTPResponse
import com.bangkit.catatmak.data.response.SummaryOutcomeIncomeResponse
import com.bangkit.catatmak.data.response.VerifyOTPResponse
import com.bangkit.catatmak.data.pref.UserModel
import com.bangkit.catatmak.data.pref.UserPreference
import com.bangkit.catatmak.data.response.BulkDataResponse
import com.bangkit.catatmak.data.response.BulkResponseItem
import com.bangkit.catatmak.data.response.GetChartsByDateResponse
import com.bangkit.catatmak.data.response.InsightResponse
import com.bangkit.catatmak.data.response.ProfileDataItem
import com.bangkit.catatmak.data.response.ProfileResponse
import com.bangkit.catatmak.data.response.TotalUncategorizeResponse
import com.bangkit.catatmak.data.response.UpdateCategoryItem
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

class CatatmakRepository private constructor(
    private var apiService: ApiService,
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
        Log.d("OTP", phone)
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.sendOTP(phone)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, OTPResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
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
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun getAllFinancialsToday() = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse = apiService.getAllFinancialsToday()
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, FinancialsTodayResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }


    fun getSummaryOutcome() = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse = apiService.getSummaryOutcomeIncome("outcome")
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, SummaryOutcomeIncomeResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun getSummaryIncome() = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse = apiService.getSummaryOutcomeIncome("income")
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, SummaryOutcomeIncomeResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun getFinancialsTotal() = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse = apiService.getFinancialsTotal()
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, FinancialsTotalResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun getOutcomeDaily() = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse = apiService.getOutcomeDaily()
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, FinancialsDailyResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun getOutcomeWeekly() = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse = apiService.getOutcomeWeekly()
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, FinancialsDailyResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun getOutcomeMonthly() = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse = apiService.getOutcomeMonthly()
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, FinancialsDailyResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun getIncomeThisMonth() = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse = apiService.getIncomeThisMonth()
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, FinancialsDailyResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun getOutcomeCustomDate(startDate: String, endDate: String) = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse = apiService.getFinancialsCustomDate(
                startDate = startDate,
                endDate = endDate,
                type = "outcome"
            )
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, FinancialsTodayResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun getIncomeCustomDate(startDate: String, endDate: String) = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse = apiService.getFinancialsCustomDate(
                startDate = startDate,
                endDate = endDate,
                type = "income"
            )
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, FinancialsTodayResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun createFinancial(
        title: String,
        price: String,
        category: String,
        type: String,
        createdAt: String
    ) = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse =
                apiService.createFinancial(title, price, category, type, createdAt)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, CreateFinancialResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun deleteFinancial(id: String) = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse = apiService.deleteFinancial(id)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, CreateFinancialResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun updateFinancial(
        id: String,
        title: String,
        price: String,
        category: String,
        type: String,
        createdAt: String
    ) = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse =
                apiService.updateFinancial(id, title, price, category, type, createdAt)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, CreateFinancialResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun updateBulk(
        bulk: List<BulkResponseItem>
    ) = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse =
                apiService.updateBulk(bulk)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, BulkDataResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun updateCategory(
        transaction: List<UpdateCategoryItem>
    ) = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse =
                apiService.updateCategory(transaction)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, BulkDataResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun getChartsByDate() = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse = apiService.getChartsByDate("weekly")
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, GetChartsByDateResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun getChartsByType() = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse = apiService.getChartsByType("weekly")
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, GetChartsByDateResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun getTotalUncategorize() = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse = apiService.getTotalUncategorize()
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, TotalUncategorizeResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun getInsight() = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse = apiService.getInsight()
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, InsightResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun getProfile() = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse = apiService.getProfile()
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ProfileResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun updateProfile(profile: ProfileDataItem) = liveData {
        emit(ResultState.Loading)
        try {
            val apiService =
                ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            val successResponse = apiService.updateProfile(profile)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ProfileResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
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