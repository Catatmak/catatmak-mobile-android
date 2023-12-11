package com.bangkit.catatmak.ui.transaction.outcome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.catatmak.R
import com.bangkit.catatmak.databinding.ActivityExpenseDetailBinding
import com.bangkit.catatmak.model.Transaction

class DetailOutcomeActivity : AppCompatActivity() {

    private val list = ArrayList<Transaction>()
    private lateinit var binding: ActivityExpenseDetailBinding

    var isSheetShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpenseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.topAppBar.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        setupRecyclerView()

        list.addAll(getListExpenses())
        // setExpenseData()
    }

    override fun onResume() {
        list.clear()
        list.addAll(getListExpenses())
        //setExpenseData()
        super.onResume()
    }

    private fun setupRecyclerView() {
        binding.rvExpenses.apply {
            layoutManager = LinearLayoutManager(this@DetailOutcomeActivity)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun getListExpenses(): ArrayList<Transaction> {
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

//    private fun setExpenseData() {
//        val listTransactionAdapter = ListTransactionAdapter(list)
//        binding.rvExpenses.adapter = listTransactionAdapter
//
//        listTransactionAdapter.setOnItemClickCallback(object :
//            ListTransactionAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: Transaction) {
//                if (!isSheetShown) {
//                    val modalBottomSheet = UpdateTransactionSheetFragment()
//                    modalBottomSheet.show(supportFragmentManager, UpdateTransactionSheetFragment.TAG)
//                    isSheetShown = true
//                }
//            }
//        })
//    }
}