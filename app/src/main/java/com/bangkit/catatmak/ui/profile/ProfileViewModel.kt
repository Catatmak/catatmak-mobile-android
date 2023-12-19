package com.bangkit.catatmak.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.catatmak.data.CatatmakRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: CatatmakRepository) : ViewModel() {


    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getProfile() = repository.getProfile()

}