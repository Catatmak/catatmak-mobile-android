package com.bangkit.catatmak.ui.add_transaction.add_with_photo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.catatmak.BuildConfig
import com.bangkit.catatmak.data.CatatmakRepository
import com.bangkit.catatmak.data.api.ApiConfig
import com.bangkit.catatmak.data.response.BulkResponseItem
import com.bangkit.catatmak.data.response.OCRDataItem
import com.bangkit.catatmak.data.response.OCRResponse
import com.bangkit.catatmak.data.response.UncategorizeDataItem
import com.bangkit.catatmak.ui.uncategorized.UncategorizedViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.Random

class AddWithPhotoViewModel(private val repository: CatatmakRepository) : ViewModel() {

    private val _categoryItems = MutableLiveData<List<String>>()
    val categoryItems: LiveData<List<String>>
        get() = _categoryItems

    private val _selectedTransaction = MutableLiveData<OCRDataItem?>()
    val selectedTransaction: LiveData<OCRDataItem?> = _selectedTransaction

    private val _listTransaction = MutableLiveData<List<OCRDataItem>>()
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

    fun sendOCR(imageFile: File) {
        _isLoading.value = true
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )
        val client = ApiConfig.getApiService(BuildConfig.TOKEN, BuildConfig.BASE_URL_FINANCIALS)
            .sendOCR(multipartBody)
        client.enqueue(object : Callback<OCRResponse> {
            override fun onResponse(
                call: Call<OCRResponse>,
                response: Response<OCRResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val ocrResponse = response.body()
                    val newList = addIdToOCRData(ocrResponse?.data.orEmpty())
                    _listTransaction.value = newList
                    Log.d("DATAKU", newList.toString())
                } else {
                    _errorMessage.value = response.message()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<OCRResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = t.message.toString()
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    private fun addIdToOCRData(ocrData: List<OCRDataItem>): List<OCRDataItem> {
        val random = Random()
        return ocrData.map { ocrItem ->
            val uniqueId = random.nextInt(Int.MAX_VALUE)
            ocrItem.copy(id = uniqueId)
        }
    }


    private fun updatedBulk(): List<BulkResponseItem> {
        val currentList = _listTransaction.value.orEmpty()
        val bulkResponseList = mutableListOf<BulkResponseItem>()

        currentList.forEach { transaction ->
            val bulkResponseItem = BulkResponseItem(
                title = transaction.title,
                type = transaction.type,
                category = transaction.category,
                price = transaction.price,
                imageUrl = transaction.imageUrl,
                imageName = transaction.imageName
            )
            bulkResponseList.add(bulkResponseItem)
        }

        Log.d(TAG, bulkResponseList.toString())

        return bulkResponseList
    }

    fun updateSelectedTransaction(updatedTransaction: OCRDataItem) {
        val currentList = _listTransaction.value.orEmpty().toMutableList()

        val updatedTransactionCopy = updatedTransaction.copy()

        val index = currentList.indexOfFirst { it.id == updatedTransaction.id }

        if (index != -1) {
            currentList[index] = updatedTransactionCopy
            _listTransaction.value = currentList
        }
    }

    fun updateCategoryTransaction(transaction: OCRDataItem, category: String) {
        val currentList = _listTransaction.value.orEmpty().toMutableList()

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