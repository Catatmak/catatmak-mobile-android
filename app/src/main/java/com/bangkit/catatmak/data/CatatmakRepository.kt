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
import com.bangkit.catatmak.data.response.OCRResponse
import com.bangkit.catatmak.data.response.ProfileDataItem
import com.bangkit.catatmak.data.response.ProfileResponse
import com.bangkit.catatmak.data.response.TotalUncategorizeResponse
import com.bangkit.catatmak.data.response.UncategorizeResponse
import com.bangkit.catatmak.data.response.UpdateCategoryItem
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException

class CatatmakRepository private constructor(
    private var apiBotService: ApiService,
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
            val successResponse = apiBotService.sendOTP(phone)
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
            val successResponse = apiBotService.verifyOTP(phone, otp)
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
            val successResponse = apiService.getChartsByDate("monthly")
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
            val successResponse = apiService.getChartsByType("monthly")
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

    fun sendOCR(imageFile: File) = liveData {
        emit(ResultState.Loading)
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = apiService.sendOCR(multipartBody)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, OCRResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }

    fun getFinancialsUncategorized() = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.getFinancialsUncategorize()
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, UncategorizeResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: IOException) {
            emit(ResultState.Error("Network error occurred"))
        }
    }


    companion object {
        @Volatile
        private var instance: CatatmakRepository? = null
        fun getInstance(
            apiBotService: ApiService,
            apiService: ApiService,
            userPreference: UserPreference,
        ): CatatmakRepository =
            instance ?: synchronized(this) {
                instance ?: CatatmakRepository(apiBotService, apiService, userPreference)
            }.also { instance = it }

        fun resetInstance() {
            instance = null
        }
    }
}