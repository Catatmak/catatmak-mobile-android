package com.bangkit.catatmak.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.catatmak.data.CatatmakRepository

class HomeViewModel(private val repository: CatatmakRepository) : ViewModel() {

    private val _categoryItems = MutableLiveData<List<String>>()
    val categoryItems: LiveData<List<String>>
        get() = _categoryItems


    init {
        loadOutcomeCategory()
    }

    private fun loadOutcomeCategory() {
        val categories = listOf("Makanan dan Minuman", "Belanja", "Hiburan", "Lainnya")
        _categoryItems.value = categories
    }

    fun loadIncomeCategory() {
        val categories = listOf("Gaji", "Bonus", "Hasil Investasi", "Lainnya")
        _categoryItems.value = categories
    }

    fun getAllFinancialsToday() = repository.getAllFinancialsToday()

    fun getTotalOutcomeToday() = repository.getSummaryOutcome()

    fun updateFinancial(
        id: String,
        title: String,
        price: String,
        category: String,
        type: String,
        createdAt: String
    ) = repository.updateFinancial(
        id,
        title,
        price,
        category,
        type,
        createdAt
    )

    fun deleteFinancial(id: String) = repository.deleteFinancial(id)

    fun getInsight() = repository.getInsight()

}