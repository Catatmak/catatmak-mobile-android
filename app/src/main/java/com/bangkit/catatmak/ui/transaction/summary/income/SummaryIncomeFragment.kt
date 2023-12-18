package com.bangkit.catatmak.ui.transaction.summary.income

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bangkit.catatmak.R
import com.bangkit.catatmak.data.ResultState
import com.bangkit.catatmak.databinding.FragmentSummaryBinding
import com.bangkit.catatmak.ui.ViewModelFactory
import com.bangkit.catatmak.ui.transaction.income.IncomeActivity
import com.bangkit.catatmak.ui.transaction.outcome.OutcomeActivity

class SummaryIncomeFragment : Fragment() {

    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding

    private var position: Int = 0

    private val viewModel by viewModels<SummaryIncomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSummaryBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(EXTRA_POSITION, 0)
        }

        getSummaryIncome()

        binding?.btnDetailSummary?.setOnClickListener {
            val intent = Intent(requireActivity(), IncomeActivity::class.java)
            intent.putExtra(IncomeActivity.EXTRA_POSITION, position)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        getSummaryIncome()
    }

    private fun getSummaryIncome() {
        viewModel.getSummaryIncome().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        showLoading(false)
                        with(result) {
                            when (position) {
                                1 -> {
                                    val summaryOutcome = data.summaryOutcomeIncomeData[0]
                                    with(summaryOutcome) {
                                        setSummaryIncomeData(title, total, count)
                                    }
                                }

                                2 -> {
                                    val summaryOutcome = data.summaryOutcomeIncomeData[1]
                                    with(summaryOutcome) {
                                        setSummaryIncomeData(title, total, count)
                                    }
                                }
                            }
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

    private fun setSummaryIncomeData(title: String, totalOutcome: String, count: Int) {
        binding?.tvSummaryTitle?.text = title
        binding?.tvSummaryTotal?.text = totalOutcome
        binding?.tvCountItem?.text = getString(R.string.count_income, count.toString())
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.pbSummary?.visibility =
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

    companion object {
        const val EXTRA_POSITION = "position"
    }
}