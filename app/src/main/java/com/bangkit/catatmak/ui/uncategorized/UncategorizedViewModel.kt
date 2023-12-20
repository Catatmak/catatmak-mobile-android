package com.bangkit.catatmak.ui.uncategorized

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.catatmak.data.CatatmakRepository
import com.bangkit.catatmak.data.response.UncategorizeDataItem
import com.bangkit.catatmak.data.response.UpdateCategoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UncategorizedViewModel(private val repository: CatatmakRepository) : ViewModel() {


    val _listTransaction = MutableLiveData<List<UncategorizeDataItem>>()
    val listTransaction: LiveData<List<UncategorizeDataItem>> = _listTransaction

    private val _updatedTransaction = MutableLiveData<List<UpdateCategoryItem>>()
    val updatedTransaction: LiveData<List<UpdateCategoryItem>> = _updatedTransaction

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        getFinancialsUncategorized()
    }

    fun getFinancialsUncategorized() = repository.getFinancialsUncategorized()

    fun setListTransaction(data: List<UncategorizeDataItem>) {
        viewModelScope.launch {
            _listTransaction.value = data
        }
    }

    fun updateSelectedTransaction(transaction: UncategorizeDataItem, category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentList = listTransaction.value.orEmpty().toMutableList()

            val updatedTransaction = transaction.copy(category = category)

            val index = currentList.indexOfFirst { it.id == transaction.id }

            if (index != -1) {
                currentList[index] = updatedTransaction
                viewModelScope.launch {
                    _listTransaction.value = currentList
                }
                Log.d(TAG, _listTransaction.value.toString())
            }
        }
    }

    private fun updatedCategory(): List<UpdateCategoryItem> {
        val currentList = listTransaction.value.orEmpty()
        val transactionResponseList = mutableListOf<UpdateCategoryItem>()

        currentList.forEach { transaction ->
            val transactionResponseItem = UpdateCategoryItem(
                id = transaction.id,
                title = transaction.title,
                type = transaction.type,
                category = transaction.category,
                price = transaction.price,
                createdAt = transaction.createdAt,
            )
            transactionResponseList.add(transactionResponseItem)
        }

        Log.d(TAG, transactionResponseList.toString())

        return transactionResponseList
    }

    fun updateCategory() = repository.updateCategory(updatedCategory())

    companion object {
        const val TAG = "UncategorizedViewModel"
    }

}