package com.bangkit.catatmak.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.catatmak.data.api.CatatmakRepository
import com.bangkit.catatmak.data.api.response.FinancialsTotalResponse
import com.bangkit.catatmak.data.di.Injection
import com.bangkit.catatmak.ui.authentication.VerificationViewModel
import com.bangkit.catatmak.ui.home.HomeViewModel
import com.bangkit.catatmak.ui.main.MainViewModel
import com.bangkit.catatmak.ui.profile.ProfileViewModel
import com.bangkit.catatmak.ui.splash_screen.SplashScreenViewModel
import com.bangkit.catatmak.ui.transaction.TransactionViewModel

class ViewModelFactory(private val repository: CatatmakRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(VerificationViewModel::class.java) -> {
                VerificationViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SplashScreenViewModel::class.java) -> {
                SplashScreenViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(TransactionViewModel::class.java) -> {
                TransactionViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }

        fun resetInstance() {
            instance = null
        }
    }
}