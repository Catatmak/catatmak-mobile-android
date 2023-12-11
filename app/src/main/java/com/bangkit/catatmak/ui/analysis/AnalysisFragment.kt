package com.bangkit.catatmak.ui.analysis

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.InsightAdapter
import com.bangkit.catatmak.databinding.FragmentAnalysisBinding
import com.bangkit.catatmak.model.Insight
import com.bangkit.catatmak.ui.categories.CategoriesActivity
import com.bangkit.catatmak.utils.AxisDateFormatter
import com.bangkit.catatmak.utils.LineChartMarkerView
import com.github.mikephil.charting.components.IMarker
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class AnalysisFragment : Fragment() {

    private val insights = ArrayList<Insight>()

    private var _binding: FragmentAnalysisBinding? = null
    private val binding get() = _binding

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

        insights.addAll(getInsights())
        setInsightData()

        setUpExpensesGraph()
        setUpCashFlowGraph()

        setUpAction()
    }

    private fun setUpAction() {
        binding?.btnCategoriesAutomatically?.setOnClickListener {
            startActivity(Intent(requireActivity(), CategoriesActivity::class.java))
        }
        binding?.btnFilterInsight?.setOnClickListener { v: View ->
            showMenu(requireActivity(), v, R.menu.filter_menu_time_current, binding?.btnFilterInsight!!)
        }
    }

    private fun setUpCashFlowGraph() {
        binding?.lineChartCashFlow?.legend?.isEnabled = false


        val value = ArrayList<BarEntry>()
        value.apply {
            clear()
            add(BarEntry(0F, 5000000F))
            add(BarEntry(1F, 3000000F))
            add(BarEntry(2F, 1000000F))
        }

        val type = ArrayList<String>();
        type.apply {
            clear()
            add(resources.getString(R.string.income))
            add(resources.getString(R.string.outcome))
            add(resources.getString(R.string.savings))
        }
        val axisType = AxisDateFormatter(type.toArray(arrayOfNulls<String>(type.size)))


        val marker: IMarker = LineChartMarkerView(
            requireActivity(),
            binding?.lineChartCashFlow,
            R.layout.markerview_item,
            axisType
        );
        binding?.lineChartCashFlow?.marker = marker;

        val barDataSet = BarDataSet(value, getString(R.string.outcome))
        barDataSet.colors = listOf(
            ContextCompat.getColor(requireActivity(), R.color.orange),
            ContextCompat.getColor(requireActivity(), R.color.red),
            ContextCompat.getColor(requireActivity(), R.color.teal_blue)
        )
        barDataSet.setDrawValues(false)

        binding?.lineChartCashFlow?.apply {
            description.isEnabled = false
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            data = BarData(barDataSet)
            xAxis.apply {
                labelCount = value.size
                valueFormatter = axisType
                granularity = 1f // Menetapkan granularitas sumbu X
                isGranularityEnabled = true // Mengaktifkan granularitas
            }

            setPinchZoom(false)
            animateXY(100, 500)
        }
    }

    private fun setUpExpensesGraph() {
        binding?.lineChartExpenses?.legend?.isEnabled = false


        val expenses = ArrayList<BarEntry>()
        expenses.apply {
            clear()
            add(BarEntry(0F, 15000F))
            add(BarEntry(1F, 10000F))
            add(BarEntry(2F, 6000F))
            add(BarEntry(3F, 13000F))
            add(BarEntry(4F, 10000F))
            add(BarEntry(5F, 15000F))
            add(BarEntry(6F, 15000F))
            add(BarEntry(7F, 15000F))
            add(BarEntry(8F, 15000F))
            add(BarEntry(9F, 1000F))
        }

        val date = ArrayList<String>();
        date.apply {
            clear()
            add("01 Jun")
            add("02 Jun")
            add("03 Jun")
            add("04 Jun")
            add("05 Jun")
            add("06 Jun")
            add("07 Jun")
            add("08 Jun")
            add("09 Jun")
            add("10 Jun")
        }

        val axisDate = AxisDateFormatter(date.toArray(arrayOfNulls<String>(date.size)))


        val marker: IMarker = LineChartMarkerView(
            requireActivity(),
            binding?.lineChartExpenses,
            R.layout.markerview_item,
            axisDate
        );
        binding?.lineChartExpenses?.marker = marker;

        val expensesBarDataSet = BarDataSet(expenses, getString(R.string.outcome))
        expensesBarDataSet.color = ContextCompat.getColor(requireActivity(), R.color.teal_blue)
        expensesBarDataSet.setDrawValues(false)

        binding?.lineChartExpenses?.apply {
            description.isEnabled = false
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            data = BarData(expensesBarDataSet)
            xAxis.apply {
                labelCount = date.size
                valueFormatter = axisDate
                granularity = 1f // Atur interval label
                isGranularityEnabled = true
                labelRotationAngle = -45f // Menetapkan rotasi label
                setDrawLabels(true)
            }
            setPinchZoom(false)
            animateXY(100, 500)
        }
    }

    private fun showMenu(context: Context, v: View, @MenuRes menuRes: Int, button: Button) {
        val icArrowUp: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_arrow_up)
        val icArrowDown: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_arrow_down)
        val popup = PopupMenu(context, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.option_filter_1 -> {
                    button.text = menuItem.title
                    setDrawableEnd(button, icArrowDown)
                    true
                }

                R.id.option_filter_2 -> {
                    button.text = menuItem.title
                    setDrawableEnd(button, icArrowDown)
                    true
                }

                R.id.option_filter_3 -> {
                    button.text = menuItem.title
                    setDrawableEnd(button, icArrowDown)
                    true
                }

                else -> false
            }
        }
        popup.setOnDismissListener {
            setDrawableEnd(button, icArrowDown)
        }
        // Show the popup menu.
        popup.show()
        setDrawableEnd(button, icArrowUp)
    }

    private fun setDrawableEnd(button: Button, drawable: Drawable?) {
        val drawables = button.compoundDrawablesRelative

        // Set drawable baru sebagai drawableEnd
        button.setCompoundDrawablesRelativeWithIntrinsicBounds(
            drawables[0], // drawableStart
            drawables[1], // drawableTop
            drawable,     // drawableEnd
            drawables[3]  // drawableBottom
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getInsights(): ArrayList<Insight> {
        val dataCreatedAt = resources.getStringArray(R.array.insight_created_at)
        val dataInsightTitle = resources.getStringArray(R.array.insight_title)
        val dataInsightDesc = resources.getStringArray(R.array.insight_desc)
        val dataIsFavorite = resources.getStringArray(R.array.insight_is_favorite)
        val list = ArrayList<Insight>()
        for (i in dataInsightTitle.indices) {
            val insight = Insight(
                dataCreatedAt[i],
                dataInsightTitle[i],
                dataInsightDesc[i],
                dataIsFavorite[i],
            )
            list.add(insight)
        }
        return list
    }

    private fun setInsightData() {
        val insightAdapter = InsightAdapter(insights) { position, isFavorite ->
            insights[position].isFavorite = if (isFavorite) "1" else "0"
        }
        binding?.rvInsights?.adapter = insightAdapter

    }

    private fun setupRecyclerView() {
        binding?.rvInsights?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }
}