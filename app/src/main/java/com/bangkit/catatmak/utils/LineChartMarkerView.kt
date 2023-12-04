package com.bangkit.catatmak.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.bangkit.catatmak.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.IMarker
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

class LineChartMarkerView(context: Context?, private val lineChart: BarChart?, layoutResource: Int, axisX: AxisDateFormatter) : MarkerView(context, layoutResource), IMarker {

    private val title: TextView = findViewById(R.id.tvTitle)
    private val item: TextView = findViewById(R.id.tvItem)
    private val XAxis: AxisDateFormatter

    init {
        XAxis = axisX
    }

    @SuppressLint("SetTextI18n")
    override fun refreshContent(e: Entry, highlight: Highlight) {
        try {
            title.text = XAxis.getFormattedValue(e.x).toString()
            val val1 = lineChart?.data?.getDataSetByIndex(0)?.getEntryForXValue(e.x, Float.NaN, DataSet.Rounding.CLOSEST) as Entry
            item.text = resources.getString(R.string.total_expenses, String.format("%,.0f", val1.y))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        super.refreshContent(e, highlight)
    }

    private var mOffset: MPPointF? = null
    override fun getOffset(): MPPointF {
        if (mOffset == null) {
            mOffset = MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
        }
        return mOffset!!
    }
}