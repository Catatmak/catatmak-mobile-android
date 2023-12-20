package com.bangkit.catatmak.ui.add_transaction.add_with_photo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.catatmak.data.CatatmakRepository
import com.bangkit.catatmak.data.response.BulkResponseItem
import com.bangkit.catatmak.data.response.OCRDataItem
import com.bangkit.catatmak.utils.convertCurrencyStringToNumber
import java.io.File

class AddWithPhotoViewModel(private val repository: CatatmakRepository) : ViewModel() {

    private val _categoryItems = MutableLiveData<List<String>>()
    val categoryItems: LiveData<List<String>>
        get() = _categoryItems

    private val _selectedTransaction = MutableLiveData<OCRDataItem?>()
    val selectedTransaction: LiveData<OCRDataItem?> = _selectedTransaction

    val _listTransaction = MutableLiveData<List<OCRDataItem>>()
    val listTransaction: LiveData<List<OCRDataItem>> = _listTransaction

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

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

    fun sendOCR(imageFile: File) = repository.sendOCR(imageFile)



    private fun updatedBulk(): List<BulkResponseItem> {
        val currentList = _listTransaction.value.orEmpty()
        val bulkResponseList = mutableListOf<BulkResponseItem>()

        currentList.forEach { transaction ->
            val bulkResponseItem = BulkResponseItem(
                title = transaction.title,
                type = transaction.type,
                category = transaction.category,
                price = convertCurrencyStringToNumber(transaction.price).toString(),
                imageUrl = transaction.imageUrl,
                imageName = transaction.imageName
            )
            bulkResponseList.add(bulkResponseItem)
        }

        Log.d(TAG, bulkResponseList.toString())

        return bulkResponseList
    }

    fun updateSelectedTransaction(updatedTransaction: OCRDataItem) {
        val currentList = listTransaction.value.orEmpty().toMutableList()

        val updatedTransactionCopy = updatedTransaction.copy()

        val index = currentList.indexOfFirst { it.id == updatedTransaction.id }

        if (index != -1) {
            currentList[index] = updatedTransactionCopy
            _listTransaction.value = currentList
        }
    }

    fun updateCategoryTransaction(transaction: OCRDataItem, category: String) {
        val currentList = listTransaction.value.orEmpty().toMutableList()

        val updatedTransaction = transaction.copy(category = category)

        val index = currentList.indexOfFirst { it.id == transaction.id }

        if (index != -1) {
            currentList[index] = updatedTransaction
            _listTransaction.value = currentList
            Log.d(TAG, _listTransaction.value.toString())
        }
    }


    fun deleteTransaction(updatedTransaction: OCRDataItem) {
        val currentList = _listTransaction.value.orEmpty().toMutableList()
        val itemToRemove = currentList.firstOrNull { it.id == updatedTransaction.id }

        itemToRemove?.let {
            currentList.remove(it)
            _listTransaction.value = currentList
        }
    }


    fun selectTransaction(transaction: OCRDataItem) {
        _selectedTransaction.value = transaction
    }

    fun updateBulk() = repository.updateBulk(updatedBulk())

    companion object {
        private const val TAG = "AddWithPhotoViewModel"
    }
}