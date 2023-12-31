package com.bangkit.catatmak.ui.transaction.detail_income

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.ListTransactionAdapter
import com.bangkit.catatmak.data.ResultState
import com.bangkit.catatmak.databinding.ActivityFinancialsDetailBinding
import com.bangkit.catatmak.ui.ViewModelFactory
import com.bangkit.catatmak.ui.transaction.detail_outcome.DetailIncomeViewModel

class DetailIncomeActivity : AppCompatActivity(),
    BottomSheetDismissListener {

    private lateinit var binding: ActivityFinancialsDetailBinding

    var isSheetShown = false

    private val viewModel by viewModels<DetailIncomeViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var startDate: String
    private lateinit var endDate: String
    private lateinit var title: String
    private var position: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinancialsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startDate = intent.getStringExtra(EXTRA_START_DATE).toString()
        endDate = intent.getStringExtra(EXTRA_END_DATE).toString()
        title = intent.getStringExtra(EXTRA_TITLE).toString()
        position = intent.getIntExtra(EXTRA_POSITION, 0)


        setSupportActionBar(binding.topAppBar)

        val upArrow = ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.dark_blue), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        supportActionBar?.title = getString(R.string.detail_income)

        binding.topAppBar.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        binding.tvTitle.text = title

        setupRecyclerView()
        getFinancialsCustomDate()
    }

    override fun onBottomSheetDismissed() {
        getFinancialsCustomDate()
    }

    private fun setupRecyclerView() {
        binding.rvDetailOutcome.apply {
            layoutManager = LinearLayoutManager(this@DetailIncomeActivity)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun getFinancialsCustomDate() {
        when (position) {
            1 -> {
                viewModel.getIncomeCustomDate(startDate, endDate).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Loading -> {
                                showLoading(true)
                            }

                            is ResultState.Success -> {
                                showLoading(false)
                                val financialsCustomDate = result.data.financialsData
                                val adapter = ListTransactionAdapter { transaction ->
                                    if (!isSheetShown) {
                                        val showBottomSheet = UpdateIncomeTransactionSheetFragment(transaction, this)
                                        showBottomSheet.show(supportFragmentManager, UpdateIncomeTransactionSheetFragment.TAG)
                                        isSheetShown = true
                                    }
                                }
                                adapter.submitList(financialsCustomDate)
                                binding.rvDetailOutcome.adapter = adapter
                            }

                            is ResultState.Error -> {
                                showLoading(false)
                                showToast(result.error.toString())
                            }
                        }
                    }
                }
            }

            2 -> {
                viewModel.getIncomeCustomDate(startDate, endDate).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Loading -> {
                                showLoading(true)
                            }

                            is ResultState.Success -> {
                                showLoading(false)
                                val financialsCustomDate = result.data.financialsData
                                val adapter = ListTransactionAdapter { transaction ->
                                    if (!isSheetShown) {
                                        val showBottomSheet = UpdateIncomeTransactionSheetFragment(transaction, this)
                                        showBottomSheet.show(supportFragmentManager, UpdateIncomeTransactionSheetFragment.TAG)
                                        isSheetShown = true
                                    }
                                }
                                adapter.submitList(financialsCustomDate)
                                binding.rvDetailOutcome.adapter = adapter
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
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbDetailOutcome.visibility =
            if (isLoading) View.VISIBLE else View.GONE

    }

    private fun showToast(message: String) {
        Toast.makeText(
            this, message, Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        const val EXTRA_START_DATE = "start_date"
        const val EXTRA_END_DATE = "end_date"
        const val EXTRA_POSITION = "position"
        const val EXTRA_TITLE = "title"
    }
}