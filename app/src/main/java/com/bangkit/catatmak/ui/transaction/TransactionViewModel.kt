package com.bangkit.catatmak.ui.transaction

import androidx.lifecycle.ViewModel
import com.bangkit.catatmak.data.CatatmakRepository

class TransactionViewModel(private val repository: CatatmakRepository) : ViewModel() {

    fun getFinancialsTotal() = repository.getFinancialsTotal()

}