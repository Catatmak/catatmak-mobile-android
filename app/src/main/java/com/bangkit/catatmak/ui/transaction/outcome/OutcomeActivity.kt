package com.bangkit.catatmak.ui.transaction.outcome

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.OutcomeAdapter
import com.bangkit.catatmak.data.ResultState
import com.bangkit.catatmak.databinding.ActivityFinancialsBinding
import com.bangkit.catatmak.ui.ViewModelFactory

class OutcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinancialsBinding

    private var position: Int = 0

    private val viewModel by viewModels<OutcomeViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinancialsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)

        position = intent.getIntExtra(EXTRA_POSITION, 0)

        val upArrow = ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.dark_blue), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        binding.topAppBar.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        setupRecyclerView()
        getFinancialsToday()
    }

    private fun setupRecyclerView() {
        binding.rvOutcome.apply {
            layoutManager = LinearLayoutManager(this@OutcomeActivity)
            setHasFixedSize(true)
        }
    }

    private fun getFinancialsToday() {
        when (position) {
            1 -> {
                supportActionBar?.title = getString(R.string.daily)
                viewModel.getOutcomeDaily().observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Loading -> {
                                showLoading(true)
                            }

                            is ResultState.Success -> {
                                showLoading(false)
                                val financialsDaily = result.data.financialsData
                                if (financialsDaily.isNotEmpty()) {
                                    binding.tvNoData.visibility = View.GONE
                                    val adapter = OutcomeAdapter()
                                    adapter.submitList(financialsDaily)
                                    adapter.setPosition(position)
                                    binding.rvOutcome.adapter = adapter
                                } else {
                                    binding.tvNoData.visibility = View.VISIBLE
                                }

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
                supportActionBar?.title = getString(R.string.weekly)
                viewModel.getOutcomeWeekly().observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Loading -> {
                                showLoading(true)
                            }

                            is ResultState.Success -> {
                                showLoading(false)
                                val financialsWeekly = result.data.financialsData
                                if (financialsWeekly.isNotEmpty()) {
                                    binding.tvNoData.visibility = View.GONE
                                    val adapter = OutcomeAdapter()
                                    adapter.submitList(financialsWeekly)
                                    adapter.setPosition(position)
                                    binding.rvOutcome.adapter = adapter
                                } else {
                                    binding.tvNoData.visibility = View.VISIBLE
                                }

                            }

                            is ResultState.Error -> {
                                showLoading(false)
                                showToast(result.error.toString())
                            }
                        }
                    }
                }
            }
            3 -> {
                supportActionBar?.title = getString(R.string.monthly)
                viewModel.getOutcomeMonthly().observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Loading -> {
                                showLoading(true)
                            }

                            is ResultState.Success -> {
                                showLoading(false)
                                val financialsMonthly = result.data.financialsData
                                if (financialsMonthly.isNotEmpty()) {
                                    binding.tvNoData.visibility = View.GONE
                                    val adapter = OutcomeAdapter()
                                    adapter.submitList(financialsMonthly)
                                    adapter.setPosition(position)
                                    binding.rvOutcome.adapter = adapter
                                } else {
                                    binding.tvNoData.visibility = View.VISIBLE
                                }

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
        binding.pbOutcome.visibility =
            if (isLoading) View.VISIBLE else View.GONE

    }

    private fun showToast(message: String) {
        Toast.makeText(
            this, message, Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        const val EXTRA_POSITION = "arg_position"
    }
}