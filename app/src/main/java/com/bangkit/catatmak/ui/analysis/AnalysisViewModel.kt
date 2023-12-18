package com.bangkit.catatmak.ui.analysis

import androidx.lifecycle.ViewModel
import com.bangkit.catatmak.data.CatatmakRepository

class AnalysisViewModel(private val repository: CatatmakRepository) : ViewModel() {

    fun getChartsByDate() = repository.getChartsByDate()

    fun getChartsByType() = repository.getChartsByType()

    fun getTotalUnCategorize() = repository.getTotalUncategorize()

    fun getInsight() = repository.getInsight()

}