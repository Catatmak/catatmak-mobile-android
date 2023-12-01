package com.bangkit.catatmak.ui.analysis

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
        val insight = ArrayList<Insight>()
        for (i in dataInsightTitle.indices) {
            val insight = Insight(
                dataCreatedAt[i],
                dataInsightTitle[i],
                dataInsightDesc[i],
                dataIsFavorite[i],
            )
            insights.add(insight)
        }
        return insight
    }

    private fun setInsightData() {
        val insightAdapter = InsightAdapter(insights) { position, isFavorite ->
            // Handle the favorite click event here
            // You can update your data or UI based on the position and isFavorite status
            insights[position].isFavorite = if (isFavorite) "1" else "0"
            // Perform any necessary UI updates here
        }
        binding?.rvInsights?.adapter = insightAdapter

    }

    private fun setupRecyclerView() {
        binding?.rvInsights?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun setUpAction() {
        binding?.btnFilter1?.setOnClickListener { v: View ->
            showMenu(v, R.menu.filter_menu_2, binding?.btnFilter1!!)
        }

        binding?.btnFilter2?.setOnClickListener { v: View ->
            showMenu(v, R.menu.filter_menu1, binding?.btnFilter2!!)
        }
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int, button: Button) {
        val icArrowUp: Drawable? = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_arrow_up)
        val icArrowDown: Drawable? = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_arrow_down)
        val popup = PopupMenu(requireActivity(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.option_filter_1 -> {
                    binding?.btnFilter1?.text = menuItem.title
                    setDrawableEnd(button, icArrowDown)
                    true
                }

                R.id.option_filter_2 -> {
                    binding?.btnFilter1?.text = menuItem.title
                    setDrawableEnd(button, icArrowDown)
                    true
                }

                R.id.option_filter_3 -> {
                    binding?.btnFilter1?.text = menuItem.title
                    setDrawableEnd(button, icArrowDown)
                    true
                }

                R.id.option_filter_4 -> {
                    binding?.btnFilter2?.text = menuItem.title
                    setDrawableEnd(button, icArrowDown)
                    true
                }

                R.id.option_filter_5 -> {
                    binding?.btnFilter2?.text = menuItem.title
                    setDrawableEnd(button, icArrowDown)
                    true
                }

                R.id.option_filter_6 -> {
                    binding?.btnFilter2?.text = menuItem.title
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

    companion object {

    }
}