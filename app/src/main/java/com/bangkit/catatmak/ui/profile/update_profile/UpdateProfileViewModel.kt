package com.bangkit.catatmak.ui.profile.update_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.catatmak.data.CatatmakRepository
import com.bangkit.catatmak.data.response.ProfileDataItem

class UpdateProfileViewModel(private val repository: CatatmakRepository) : ViewModel() {

    private val _genderItems = MutableLiveData<List<String>>()
    val genderItems: LiveData<List<String>>
        get() = _genderItems

    init {
        loadGenderItems()
    }

     private fun loadGenderItems() {
        val gender = listOf("L", "P")
        _genderItems.value = gender
    }

    fun getProfile() = repository.getProfile()

    fun updateProfile(profile: ProfileDataItem) = repository.updateProfile(profile)



}