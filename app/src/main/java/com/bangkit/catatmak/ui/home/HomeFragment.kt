package com.bangkit.catatmak.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.ListTransactionAdapter
import com.bangkit.catatmak.data.ResultState
import com.bangkit.catatmak.databinding.FragmentHomeBinding
import com.bangkit.catatmak.ui.ViewModelFactory

class HomeFragment : Fragment(), BottomSheetDismissListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    var isSheetShown = false

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getTotalOutcomeToday()
        getAllFinancialsToday()
        getInsight()
    }

    private fun getInsight() {
        viewModel.getInsight().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true, "one-insight")
                    }

                    is ResultState.Success -> {
                        showLoading(false, "one-insight")
                        val insight = result.data.data
                        if (insight.isNotEmpty()) {
                            binding?.tvInsightTitle?.text =
                                getString(R.string.insight_title, insight[0].title)
                            binding?.tvInsightMessage?.text = insight[0].description
                        } else {
                           binding?.ivMamihAi?.visibility = View.GONE
                        }
                    }

                    is ResultState.Error -> {
                        showLoading(false, "one-insight")
                        showToast(result.error.toString())
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getTotalOutcomeToday()
        getAllFinancialsToday()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onBottomSheetDismissed() {
        getAllFinancialsToday()
        getTotalOutcomeToday()
    }

    private fun setupRecyclerView() {
        binding?.rvTransactions?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun getTotalOutcomeToday() {
        viewModel.getTotalOutcomeToday().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true, "total-outcome-today")
                    }

                    is ResultState.Success -> {
                        showLoading(false, "total-outcome-today")
                        val totalOutcomeToday = result.data.summaryOutcomeIncomeData[0].total
                        if (totalOutcomeToday.isNotEmpty()) {
                            binding?.tvTotalOutcomeToday?.text = totalOutcomeToday
                        }
                    }

                    is ResultState.Error -> {
                        showLoading(false, "total-outcome-today")
                        showToast(result.error.toString())
                    }
                }
            }
        }
    }

    private fun getAllFinancialsToday() {
        viewModel.getAllFinancialsToday().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true, "financials-today")
                    }

                    is ResultState.Success -> {
                        showLoading(false, "financials-today")
                        val financialsToday = result.data.financialsData
                        if (financialsToday.isNotEmpty()) {
                            binding?.tvNoTransactionData?.visibility = View.GONE
                            val adapter = ListTransactionAdapter { transaction ->
                                if (!isSheetShown) {
                                    val showBottomSheet =
                                        UpdateTransactionSheetFragment(transaction, this)
                                    showBottomSheet.show(
                                        childFragmentManager,
                                        UpdateTransactionSheetFragment.TAG
                                    )
                                    isSheetShown = true
                                }
                            }
                            adapter.submitList(financialsToday)
                            binding?.rvTransactions?.adapter = adapter
                        } else {
                            val adapter = ListTransactionAdapter { _ ->
                            }
                            adapter.submitList(emptyList())
                            binding?.tvNoTransactionData?.visibility = View.VISIBLE
                        }
                    }

                    is ResultState.Error -> {
                        showLoading(false, "financials-today")
                        showToast(result.error.toString())
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean, type: String) {
        when (type) {
            "financials-today" -> {
                binding?.progressIndicatorTodaysFinancials?.visibility =
                    if (isLoading) View.VISIBLE else View.GONE
            }

            "total-outcome-today" -> {
                binding?.progressIndicatorFinancialsTotal?.visibility =
                    if (isLoading) View.VISIBLE else View.GONE
            }

            "one-insight" -> {
                binding?.progressIndicatorInsight?.visibility =
                    if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(
            requireActivity(), message, Toast.LENGTH_SHORT
        ).show()
    }

}