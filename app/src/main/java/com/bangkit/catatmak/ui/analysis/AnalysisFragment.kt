package com.bangkit.catatmak.ui.analysis

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.InsightAdapter
import com.bangkit.catatmak.data.ResultState
import com.bangkit.catatmak.data.response.ChartOutcomeDataItem
import com.bangkit.catatmak.databinding.FragmentAnalysisBinding
import com.bangkit.catatmak.ui.ViewModelFactory
import com.bangkit.catatmak.ui.uncategorized.UcategorizedActivity
import com.bangkit.catatmak.utils.AxisDateFormatter
import com.bangkit.catatmak.utils.LineChartMarkerView
import com.github.mikephil.charting.components.IMarker
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class AnalysisFragment : Fragment() {


    private var _binding: FragmentAnalysisBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModels<AnalysisViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private var count: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnalysisBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        setUpAction()

        getChartsByDate()
        getChartsByType()
        getTotalUnCategorize()
        getInsight()
    }

    private fun getInsight() {
        viewModel.getInsight().observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true, "insight")
                    }

                    is ResultState.Success -> {
                        showLoading(false, "insight")
                        val insight = result.data.data
                        if (insight.isNotEmpty()) {
                            binding?.tvNoInsight?.visibility = View.GONE
                            val adapter = InsightAdapter()
                            adapter.submitList(insight)
                            binding?.rvInsights?.adapter = adapter
                        } else {
                            binding?.tvNoInsight?.visibility = View.VISIBLE
                        }
                    }

                    is ResultState.Error -> {
                        showLoading(false, "insight")
                        showToast(result.error.toString())
                    }

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getTotalUnCategorize()
    }

    private fun setUpAction() {
        binding?.btnCategorize?.setOnClickListener {
            val intent = Intent(requireActivity(), UcategorizedActivity::class.java)
            intent.putExtra(UcategorizedActivity.EXTRA_COUNT, count)
            startActivity(intent)
        }
    }

    private fun getTotalUnCategorize() {
        viewModel.getTotalUnCategorize().observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                    }

                    is ResultState.Success -> {
                        showLoading(false, "total_uncategorize")
                        val totalUncategorize = result.data.data.count
                        count = totalUncategorize
                        if (totalUncategorize > 0) {
                            binding?.cvCategorize?.visibility = View.VISIBLE
                            binding?.tvTitleCategoriesAutomatically?.text = getString(
                                R.string.title_categories_automatically,
                                totalUncategorize.toString()
                            )
                        } else {
                            binding?.cvCategorize?.visibility = View.GONE
                        }
                    }

                    is ResultState.Error -> {
                        showToast(result.error.toString())
                    }

                }
            }
        }
    }

    private fun getChartsByType() {
        viewModel.getChartsByType().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true, "cash-flow-chart")
                    }

                    is ResultState.Success -> {
                        showLoading(false, "cash-flow-chart")
                        val chartData = result.data.data
                        if (chartData.isEmpty()) {
                            binding?.lineChartCashFlow?.visibility = View.INVISIBLE
                            binding?.tvNoCashFlowData?.visibility = View.VISIBLE
                        } else {
                            binding?.lineChartCashFlow?.visibility = View.VISIBLE
                            binding?.tvNoCashFlowData?.visibility = View.GONE
                            setUpCashFlowCharts(chartData)
                        }
                    }

                    is ResultState.Error -> {
                        showLoading(false, "cash-flow-chart")
                        showToast(result.error.toString())
                    }

                }
            }
        }
    }

    private fun getChartsByDate() {
        viewModel.getChartsByDate().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true, "outcome-chart")
                    }

                    is ResultState.Success -> {
                        showLoading(false, "outcome-chart")
                        val chartData = result.data.data
                        if (chartData.isEmpty()) {
                            binding?.lineChartOutcome?.visibility = View.INVISIBLE
                            binding?.tvNoChartOutcomeData?.visibility = View.VISIBLE
                        } else {
                            binding?.lineChartOutcome?.visibility = View.VISIBLE
                            binding?.tvNoChartOutcomeData?.visibility = View.GONE
                            setUpOutcomeCharts(chartData)
                        }
                    }

                    is ResultState.Error -> {
                        showLoading(false, "outcome-chart")
                        showToast(result.error.toString())
                    }

                }
            }
        }
    }

    private fun setUpCashFlowCharts(chartData: List<ChartOutcomeDataItem>) {
        binding?.lineChartCashFlow?.legend?.isEnabled = false

        var x = 0F
        val value = ArrayList<BarEntry>()
        value.apply {
            chartData.forEach { result ->
                val y = result.y.toInt()
                add(BarEntry(x, y.toFloat()))
                x++
            }
        }

        val type = ArrayList<String>();
        type.apply {
            chartData.forEach { result ->
                add(result.x)
            }
        }
        val axisType = AxisDateFormatter(type.toArray(arrayOfNulls<String>(type.size)))


        val marker: IMarker = LineChartMarkerView(
            requireActivity(),
            binding?.lineChartCashFlow,
            R.layout.markerview_item,
            axisType
        )
        binding?.lineChartCashFlow?.marker = marker;

        val barDataSet = BarDataSet(value, getString(R.string.outcome))
        barDataSet.colors = listOf(
            ContextCompat.getColor(requireActivity(), R.color.orange),
            ContextCompat.getColor(requireActivity(), R.color.red),
            ContextCompat.getColor(requireActivity(), R.color.teal_blue),
            ContextCompat.getColor(requireActivity(), R.color.orange),
        )
        barDataSet.setDrawValues(false)

        binding?.lineChartCashFlow?.apply {
            description.isEnabled = false
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            data = BarData(barDataSet)
            xAxis.apply {
                labelCount = type.size
                valueFormatter = axisType
                granularity = 1f
                isGranularityEnabled = true
            }
            setPinchZoom(false)
            animateXY(100, 500)
        }
    }

    private fun setUpOutcomeCharts(chartData: List<ChartOutcomeDataItem>) {
        binding?.lineChartOutcome?.legend?.isEnabled = false

        var x = 0F
        val outcome = ArrayList<BarEntry>()
        outcome.apply {
            chartData.forEach { result ->
                val y = result.y.toInt()
                add(BarEntry(x, y.toFloat()))
                x++
            }
        }

        val date = ArrayList<String>();
        date.apply {
            chartData.forEach { result ->
                val parts = result.x.split("-")
                val dayOfMonth = parts.getOrNull(2) ?: ""
                add(dayOfMonth)
            }
        }

        val dateTitle = ArrayList<String>();
        dateTitle.apply {
            chartData.forEach { result ->
                add(result.x)
            }
        }

        val axisDate = AxisDateFormatter(date.toArray(arrayOfNulls<String>(date.size)))
        val axisDateTitle =
            AxisDateFormatter(dateTitle.toArray(arrayOfNulls<String>(dateTitle.size)))

        val marker: IMarker = LineChartMarkerView(
            requireActivity(),
            binding?.lineChartOutcome,
            R.layout.markerview_item,
            axisDateTitle
        );
        binding?.lineChartOutcome?.marker = marker

        val expensesBarDataSet = BarDataSet(outcome, getString(R.string.outcome))
        expensesBarDataSet.color = ContextCompat.getColor(requireActivity(), R.color.teal_blue)
        expensesBarDataSet.setDrawValues(false)

        binding?.lineChartOutcome?.apply {
            description.isEnabled = false
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            data = BarData(expensesBarDataSet)
            xAxis.apply {
                labelCount = date.size
                valueFormatter = axisDate
                granularity = 1f
                isGranularityEnabled = true
            }
            setPinchZoom(false)
            animateXY(100, 500)
        }
    }

    private fun showLoading(isLoading: Boolean, type: String) {
        when (type) {
            "outcome-chart" -> {
                binding?.pbOutcomeCharts?.visibility =
                    if (isLoading) View.VISIBLE else View.GONE
            }

            "cash-flow-chart" -> {
                binding?.pbCashFlowCharts?.visibility =
                    if (isLoading) View.VISIBLE else View.GONE
            }

            "insight" -> {
                binding?.pbInsight?.visibility =
                    if (isLoading) View.VISIBLE else View.GONE
            }
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

    private fun setupRecyclerView() {
        binding?.rvInsights?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }
}