package com.bangkit.catatmak.ui.uncategorized

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.catatmak.BuildConfig
import com.bangkit.catatmak.data.CatatmakRepository
import com.bangkit.catatmak.data.api.ApiConfig
import com.bangkit.catatmak.data.response.BulkResponseItem
import com.bangkit.catatmak.data.response.UncategorizeDataItem
import com.bangkit.catatmak.data.response.UncategorizeResponse
import com.bangkit.catatmak.data.response.UpdateCategoryItem
import com.bangkit.catatmak.ui.add_transaction.add_with_photo.AddWithPhotoViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UncategorizedViewModel(private val repository: CatatmakRepository) : ViewModel() {


    private val _listTransaction = MutableLiveData<List<UncategorizeDataItem>>()
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

    private fun getFinancialsUncategorized() {
        _isLoading.value = true
        val client = ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            .getFinancialsUncategorize()
        client.enqueue(object : Callback<UncategorizeResponse> {
            override fun onResponse(
                call: Call<UncategorizeResponse>,
                response: Response<UncategorizeResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listTransaction.value = response.body()?.data
                    Log.d("DATAKU", listTransaction.toString())
                } else {
                    _errorMessage.value = response.message()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UncategorizeResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = t.message.toString()
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun updateSelectedTransaction(transaction: UncategorizeDataItem, category: String) {
        val currentList = _listTransaction.value.orEmpty().toMutableList()

        val updatedTransaction = transaction.copy(category = category)

        val index = currentList.indexOfFirst { it.id == transaction.id }

        if (index != -1) {
            currentList[index] = updatedTransaction
            _listTransaction.value = currentList
            Log.d(TAG, _listTransaction.value.toString())
        }
    }

    private fun updatedCategory(): List<UpdateCategoryItem> {
        val currentList = _listTransaction.value.orEmpty()
        val transactionResponseList = mutableListOf<UpdateCategoryItem>()

        currentList.forEach { transaction ->
            val transactionResponseItem = UpdateCategoryItem(
                id = transaction.id,
                title = transaction.title,
                type = transaction.type,
                category = transaction.category,
                price = transaction.price
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