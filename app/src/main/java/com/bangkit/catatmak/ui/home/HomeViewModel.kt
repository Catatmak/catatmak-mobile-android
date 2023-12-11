package com.bangkit.catatmak.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.catatmak.data.api.CatatmakRepository
import com.bangkit.catatmak.data.pref.UserModel

class HomeViewModel(private val repository: CatatmakRepository) : ViewModel() {

    fun getAllFinancialsToday() = repository.getAllFinancialsToday()

    fun getTotalOutcomeToday() = repository.getTotalOutcomeToday()
}