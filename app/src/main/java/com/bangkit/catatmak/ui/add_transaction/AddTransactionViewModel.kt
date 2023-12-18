package com.bangkit.catatmak.ui.add_transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.catatmak.data.CatatmakRepository

class AddTransactionViewModel(private val repository: CatatmakRepository) : ViewModel() {

    private val _categoryItems = MutableLiveData<List<String>>()
    val categoryItems: LiveData<List<String>>
        get() = _categoryItems

    init {
        loadOutcomeCategory()
    }

    fun loadOutcomeCategory() {
        val categories = listOf("Makanan dan Minuman", "Belanja", "Hiburan", "Lainnya")
        _categoryItems.value = categories
    }

    fun loadIncomeCategory() {
        val categories = listOf("Gaji", "Bonus", "Hasil Investasi", "Lainnya")
        _categoryItems.value = categories
    }

    fun createFinancial(
        title: String,
        price: String,
        category: String,
        type: String,
        createdAt: String
    ) = repository.createFinancial(
        title, price, category, type, createdAt

    )

}