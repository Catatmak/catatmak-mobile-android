package com.bangkit.catatmak.ui.transaction.summary.income

import androidx.lifecycle.ViewModel
import com.bangkit.catatmak.data.CatatmakRepository

class SummaryIncomeViewModel(private val repository: CatatmakRepository) : ViewModel() {

    fun getSummaryIncome() = repository.getSummaryIncome()

}