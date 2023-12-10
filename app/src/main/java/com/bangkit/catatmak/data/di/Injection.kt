package com.bangkit.catatmak.data.di

import android.content.Context
import com.bangkit.catatmak.data.api.ApiConfig
import com.bangkit.catatmak.data.api.CatatmakRepository
import com.bangkit.catatmak.data.pref.UserPreference
import com.bangkit.catatmak.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): CatatmakRepository {
        val apiService = ApiConfig.getApiService()
        val pref = UserPreference.getInstance(context.dataStore)
        return CatatmakRepository.getInstance(apiService, pref)
    }
}