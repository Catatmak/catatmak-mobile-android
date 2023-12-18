package com.bangkit.catatmak.ui.transaction.summary.outcome

import androidx.lifecycle.ViewModel
import com.bangkit.catatmak.data.CatatmakRepository

class SummaryOutcomeViewModel(private val repository: CatatmakRepository) : ViewModel() {

    fun getSummaryOutcome() = repository.getSummaryOutcome()

}