package com.bangkit.catatmak.ui.transaction.income

import androidx.lifecycle.ViewModel
import com.bangkit.catatmak.data.CatatmakRepository

class IncomeViewModel(private val repository: CatatmakRepository) : ViewModel() {

    fun getIncomeThisMonth() = repository.getIncomeThisMonth()


}