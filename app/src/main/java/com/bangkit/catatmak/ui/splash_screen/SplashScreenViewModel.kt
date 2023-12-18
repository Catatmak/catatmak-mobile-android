package com.bangkit.catatmak.ui.splash_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.catatmak.data.CatatmakRepository
import com.bangkit.catatmak.data.pref.UserModel

class SplashScreenViewModel(private val repository: CatatmakRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

}