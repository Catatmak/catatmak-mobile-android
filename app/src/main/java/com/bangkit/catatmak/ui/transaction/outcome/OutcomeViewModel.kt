package com.bangkit.catatmak.ui.transaction.outcome

import androidx.lifecycle.ViewModel
import com.bangkit.catatmak.data.CatatmakRepository

class OutcomeViewModel(private val repository: CatatmakRepository) : ViewModel() {

    fun getOutcomeDaily() = repository.getOutcomeDaily()

    fun getOutcomeWeekly() = repository.getOutcomeWeekly()

    fun getOutcomeMonthly() = repository.getOutcomeMonthly()

}