package com.bangkit.catatmak.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.catatmak.data.CatatmakRepository
import com.bangkit.catatmak.data.di.Injection
import com.bangkit.catatmak.ui.add_transaction.AddTransactionViewModel
import com.bangkit.catatmak.ui.add_transaction.add_with_photo.AddWithPhotoViewModel
import com.bangkit.catatmak.ui.analysis.AnalysisViewModel
import com.bangkit.catatmak.ui.authentication.VerificationViewModel
import com.bangkit.catatmak.ui.home.HomeViewModel
import com.bangkit.catatmak.ui.main.MainViewModel
import com.bangkit.catatmak.ui.profile.ProfileViewModel
import com.bangkit.catatmak.ui.profile.update_profile.UpdateProfileViewModel
import com.bangkit.catatmak.ui.splash_screen.SplashScreenViewModel
import com.bangkit.catatmak.ui.transaction.TransactionViewModel
import com.bangkit.catatmak.ui.transaction.detail_outcome.DetailIncomeViewModel
import com.bangkit.catatmak.ui.transaction.detail_outcome.DetailOutcomeViewModel
import com.bangkit.catatmak.ui.transaction.income.IncomeViewModel
import com.bangkit.catatmak.ui.transaction.outcome.OutcomeViewModel
import com.bangkit.catatmak.ui.transaction.summary.income.SummaryIncomeViewModel
import com.bangkit.catatmak.ui.transaction.summary.outcome.SummaryOutcomeViewModel
import com.bangkit.catatmak.ui.uncategorized.UncategorizedViewModel

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
            modelClass.isAssignableFrom(SummaryOutcomeViewModel::class.java) -> {
                SummaryOutcomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(OutcomeViewModel::class.java) -> {
                OutcomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(IncomeViewModel::class.java) -> {
                IncomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailIncomeViewModel::class.java) -> {
                DetailIncomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailOutcomeViewModel::class.java) -> {
                DetailOutcomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SummaryIncomeViewModel::class.java) -> {
                SummaryIncomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddTransactionViewModel::class.java) -> {
                AddTransactionViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AnalysisViewModel::class.java) -> {
                AnalysisViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddWithPhotoViewModel::class.java) -> {
                AddWithPhotoViewModel(repository) as T
            }
            modelClass.isAssignableFrom(UncategorizedViewModel::class.java) -> {
                UncategorizedViewModel(repository) as T
            }
            modelClass.isAssignableFrom(UpdateProfileViewModel::class.java) -> {
                UpdateProfileViewModel(repository) as T
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