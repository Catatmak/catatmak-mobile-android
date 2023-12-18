package com.bangkit.catatmak.ui.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.catatmak.data.CatatmakRepository
import com.bangkit.catatmak.data.pref.UserModel
import kotlinx.coroutines.launch

class VerificationViewModel(private val repository: CatatmakRepository) : ViewModel() {
    fun sendOTP(phone: String) = repository.sendOTP(phone)
    fun verifyOTP(phone: String, otp: String) = repository.verifyOTP(phone, otp)

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}