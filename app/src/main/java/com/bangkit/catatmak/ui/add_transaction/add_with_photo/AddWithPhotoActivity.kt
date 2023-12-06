package com.bangkit.catatmak.ui.add_transaction.add_with_photo

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.CategoriesAdapter
import com.bangkit.catatmak.databinding.ActivityAddWithPhotoBinding
import com.bangkit.catatmak.model.Transaction

class AddWithPhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWithPhotoBinding
    private lateinit var imageUri: Uri

    private val list = ArrayList<Transaction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWithPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.topAppBar.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        val imageUriString = intent.getStringExtra(IMAGE_URI)
        imageUri = Uri.parse(imageUriString)

        setUpAction()

        setupRecyclerView()
        list.addAll(getListTransactions())
        setTransactionData()
    }

    private fun setUpAction() {
        imageUri.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivPreviewImage.setImageURI(it)
        }
    }

    private fun setupRecyclerView() {
        binding.rvTransactions.apply {
            layoutManager = LinearLayoutManager(this@AddWithPhotoActivity)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun getListTransactions(): ArrayList<Transaction> {
        val dataCreatedAt = resources.getStringArray(R.array.data_created_at)
        val dataItemName = resources.getStringArray(R.array.data_item_name)
        val dataCategoryName = resources.getStringArray(R.array.data_category_name)
        val dataPrice = resources.getStringArray(R.array.data_price)
        val datIsPlus = resources.getStringArray(R.array.data_is_plus)
        val listTransaction = ArrayList<Transaction>()
        for (i in dataItemName.indices) {
            val transaction = Transaction(
                dataCreatedAt[i],
                dataItemName[i],
                dataCategoryName[i],
                dataPrice[i],
                datIsPlus[i]
            )
            listTransaction.add(transaction)
        }
        return listTransaction
    }

    private fun setTransactionData() {
        val categoriesAdapter = CategoriesAdapter(list, true)
        binding.rvTransactions.adapter = categoriesAdapter
    }

    companion object {
        const val IMAGE_URI = "image_uri"
    }
}