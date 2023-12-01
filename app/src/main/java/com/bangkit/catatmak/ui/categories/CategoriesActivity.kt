package com.bangkit.catatmak.ui.categories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.CategoriesAdapter
import com.bangkit.catatmak.databinding.ActivityCategoriesBinding
import com.bangkit.catatmak.model.Transaction

class CategoriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesBinding
    private val list = ArrayList<Transaction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.topAppBar.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        setupRecyclerView()
        list.addAll(getListTransactions())
        setTransactionData()
    }

    private fun setupRecyclerView() {
        binding.rvTransactions.apply {
            layoutManager = LinearLayoutManager(this@CategoriesActivity)
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
        val categoriesAdapter = CategoriesAdapter(list)
        binding.rvTransactions.adapter = categoriesAdapter
    }
}