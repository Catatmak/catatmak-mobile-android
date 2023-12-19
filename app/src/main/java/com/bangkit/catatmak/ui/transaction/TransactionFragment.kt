package com.bangkit.catatmak.ui.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import com.bangkit.catatmak.R
import com.bangkit.catatmak.data.ResultState
import com.bangkit.catatmak.databinding.FragmentTransactionBinding
import com.bangkit.catatmak.ui.ViewModelFactory
import com.bangkit.catatmak.ui.transaction.summary.outcome.OutcomeSectionsPagerAdapter
import com.bangkit.catatmak.ui.transaction.summary.income.IncomeSectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class TransactionFragment : Fragment() {

    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModels<TransactionViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    companion object {
        @StringRes
        private val TAB_TITLES_1 = intArrayOf(
            R.string.daily,
            R.string.weekly,
            R.string.monthly
        )
        private val TAB_TITLES_2 = intArrayOf(
            R.string.this_month,
            R.string.last_month,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFinancialsTotal()
        setUpViewPagerOutcome()
        setUpViewPagerIncome()
    }

    private fun setUpViewPagerOutcome() {
        val sectionsPagerAdapter = OutcomeSectionsPagerAdapter(this)
        binding?.vpSummaryOutcome?.adapter = sectionsPagerAdapter
        binding?.tSummaryOutcome?.let {
            binding?.vpSummaryOutcome?.let { it1 ->
                TabLayoutMediator(it, it1) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES_1[position])
                }.attach()
            }
        }
    }

    private fun setUpViewPagerIncome() {
        val sectionsPagerAdapter = IncomeSectionsPagerAdapter(this)
        binding?.vpSummaryIncome?.adapter = sectionsPagerAdapter
        binding?.tSummaryIncome?.let {
            binding?.vpSummaryIncome?.let { it1 ->
                TabLayoutMediator(it, it1) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES_2[position])
                }.attach()
            }
        }
    }

    private fun getFinancialsTotal() {
        viewModel.getFinancialsTotal().observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        showLoading(false)
                        val financialsTotalIncome = result.data.financialsTotalData.totalIncome
                        val financialsTotalOutcome = result.data.financialsTotalData.totalOutcome
                        if (financialsTotalIncome.isNotEmpty() && financialsTotalOutcome.isNotEmpty()) {
                            binding?.tvTotalIncome?.text = financialsTotalIncome
                            binding?.tvTotalOutcome?.text = financialsTotalOutcome
                        } else {
                            binding?.tvTotalIncome?.text = getString(R.string.total_zero)
                            binding?.tvTotalIncome?.text = getString(R.string.total_zero)
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

    private fun showLoading(isLoading: Boolean) {

        binding?.progressIndicatorFinancialsTotal?.visibility =
            if (isLoading) View.VISIBLE else View.GONE

    }

    private fun showToast(message: String) {
        Toast.makeText(
            requireActivity(), message, Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}