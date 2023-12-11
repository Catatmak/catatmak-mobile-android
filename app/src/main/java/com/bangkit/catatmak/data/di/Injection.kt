package com.bangkit.catatmak.data.di

import android.content.Context
import com.bangkit.catatmak.data.api.ApiConfig
import com.bangkit.catatmak.data.api.CatatmakRepository
import com.bangkit.catatmak.data.pref.UserPreference
import com.bangkit.catatmak.data.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): CatatmakRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return CatatmakRepository.getInstance(apiService, pref)
    }
}