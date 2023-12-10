package com.bangkit.catatmak.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.catatmak.data.api.CatatmakRepository
import com.bangkit.catatmak.data.pref.UserModel

class MainViewModel(private val repository: CatatmakRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

}