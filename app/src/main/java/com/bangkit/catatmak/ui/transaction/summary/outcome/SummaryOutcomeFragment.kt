package com.bangkit.catatmak.ui.transaction.summary.outcome

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
import com.bangkit.catatmak.ui.transaction.outcome.OutcomeActivity

class SummaryOutcomeFragment : Fragment() {

    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding

    private var position: Int = 0

    private val viewModel by viewModels<SummaryOutcomeViewModel> {
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

        getSummaryOutcome()

        binding?.btnDetailSummary?.setOnClickListener {
            val intent = Intent(requireActivity(), OutcomeActivity::class.java)
            intent.putExtra(OutcomeActivity.EXTRA_POSITION, position)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        getSummaryOutcome()
    }

    private fun getSummaryOutcome() {
        viewModel.getSummaryOutcome().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true, "summary-outcome")
                    }

                    is ResultState.Success -> {
                        showLoading(false, "summary-outcome")
                        with(result) {
                            when (position) {
                                1 -> {
                                    val summaryOutcome = data.summaryOutcomeIncomeData[0]
                                    with(summaryOutcome) {
                                        setSummaryOutcomeData(title, total, count)
                                    }
                                }

                                2 -> {
                                    val summaryOutcome = data.summaryOutcomeIncomeData[1]
                                    with(summaryOutcome) {
                                        setSummaryOutcomeData(title, total, count)
                                    }
                                }

                                3 -> {
                                    val summaryOutcome = data.summaryOutcomeIncomeData[2]
                                    with(summaryOutcome) {
                                        setSummaryOutcomeData(title, total, count)
                                    }
                                }
                            }
                        }
                    }

                    is ResultState.Error -> {
                        showLoading(false, "summary-outcome")
                        showToast(result.error.toString())
                    }
                }
            }
        }
    }

    private fun setSummaryOutcomeData(title: String, totalOutcome: String, count: Int) {
        binding?.tvSummaryTitle?.text = title
        binding?.tvSummaryTotal?.text = totalOutcome
        binding?.tvCountItem?.text = getString(R.string.count_outcome, count.toString())
    }

    private fun showLoading(isLoading: Boolean, type: String) {
        if (type == "summary-outcome") {
            binding?.pbSummary?.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        }
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



