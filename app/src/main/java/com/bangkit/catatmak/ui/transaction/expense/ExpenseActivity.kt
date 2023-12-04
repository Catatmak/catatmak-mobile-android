package com.bangkit.catatmak.ui.transaction.expense

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.ExpenseAdapter
import com.bangkit.catatmak.databinding.ActivityExpenseBinding
import com.bangkit.catatmak.model.Expense

class ExpenseActivity : AppCompatActivity() {

    private val list = ArrayList<Expense>()
    private lateinit var binding: ActivityExpenseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpenseBinding.inflate(layoutInflater)
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
        setTransactionData()
    }

    override fun onResume() {
        list.clear()
        list.addAll(getListExpenses())
        setTransactionData()
        super.onResume()
    }

    private fun setupRecyclerView() {
        binding.rvExpenses.apply {
            layoutManager = LinearLayoutManager(this@ExpenseActivity)
            setHasFixedSize(true)
        }
    }

    private fun getListExpenses(): ArrayList<Expense> {
        val dateTime = resources.getStringArray(R.array.data_date_time)
        val dataTotal = resources.getStringArray(R.array.data_total)
        val dataExpensesAmount = resources.getStringArray(R.array.data_expenses_amount)
        val list = ArrayList<Expense>()
        for (i in dateTime.indices) {
            val expense = Expense(
                dateTime[i],
                dataTotal[i],
                dataExpensesAmount[i],
            )
            list.add(expense)
        }
        return list
    }

    private fun setTransactionData() {
        val listTransactionAdapter = ExpenseAdapter(list)
        binding.rvExpenses.adapter = listTransactionAdapter
    }
}