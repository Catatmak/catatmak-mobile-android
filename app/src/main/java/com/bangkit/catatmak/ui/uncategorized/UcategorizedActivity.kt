package com.bangkit.catatmak.ui.uncategorized

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.MenuRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.ListTransactionAdapter
import com.bangkit.catatmak.adapter.UncategorizeAdapter
import com.bangkit.catatmak.data.ResultState
import com.bangkit.catatmak.databinding.ActivityCategorizeBinding
import com.bangkit.catatmak.ui.ViewModelFactory
import com.bangkit.catatmak.ui.analysis.AnalysisFragment
import com.bangkit.catatmak.ui.home.UpdateTransactionSheetFragment

class UcategorizedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategorizeBinding

    private val viewModel by viewModels<UncategorizedViewModel> {
        ViewModelFactory.getInstance(this)
    }


    private var count: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategorizeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.topAppBar.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        count = intent.getIntExtra(EXTRA_COUNT, 0)

        binding.tvTitleCategoriesAutomatically.text =
            getString(R.string.title_categories_automatically, count.toString())

        setUpAction()

        setupRecyclerView()

        viewModel.listTransaction.observe(this) { listTransaction ->
            val adapter = UncategorizeAdapter { transaction, category ->
                viewModel.updateSelectedTransaction(transaction, category)
            }
            adapter.submitList(listTransaction)
            binding.rvTransactions.adapter = adapter
        }

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            showToast(errorMessage)
        }
    }

    private fun setUpAction() {
        binding.btnUpdate.setOnClickListener {
            viewModel.updateCategory()?.observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }

                        is ResultState.Success -> {
                            showLoading(false)
                            showToast(result.data.message)
                            finish()
                        }

                        is ResultState.Error -> {
                            showLoading(false)
                            showToast(result.error.toString())
                        }
                    }
                }
            }
        }
    }


    private fun setupRecyclerView() {
        binding.rvTransactions.apply {
            layoutManager = LinearLayoutManager(this@UcategorizedActivity)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(
            this, message, Toast.LENGTH_SHORT
        ).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbUncategorized.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }


    companion object {
        const val EXTRA_COUNT = "count"
    }
}