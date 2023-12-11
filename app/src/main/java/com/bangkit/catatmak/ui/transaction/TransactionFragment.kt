package com.bangkit.catatmak.ui.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.ListTransactionAdapter
import com.bangkit.catatmak.data.ResultState
import com.bangkit.catatmak.databinding.FragmentTransactionBinding
import com.bangkit.catatmak.model.Transaction
import com.bangkit.catatmak.ui.ViewModelFactory
import com.bangkit.catatmak.ui.main.MainViewModel
import com.bangkit.catatmak.ui.transaction.tab_layout_view_pager.SectionsPagerAdapterExpense
import com.bangkit.catatmak.ui.transaction.tab_layout_view_pager.SectionsPagerAdapterIncome
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

        // setupAction()

        val sectionsPagerAdapterExpense = SectionsPagerAdapterExpense(requireActivity())
        binding?.viewPager1?.adapter = sectionsPagerAdapterExpense
        binding?.tabs1?.let {
            binding?.viewPager1?.let { it1 ->
                TabLayoutMediator(it, it1) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES_1[position])
                }.attach()
            }
        }

        val sectionsPagerAdapterIncome = SectionsPagerAdapterIncome(requireActivity())
        binding?.viewPager2?.adapter = sectionsPagerAdapterIncome
        binding?.tabs2.let {
            binding?.viewPager2?.let { it1 ->
                if (it != null) {
                    TabLayoutMediator(it, it1) { tab, position ->
                        tab.text = resources.getString(TAB_TITLES_2[position])
                    }.attach()
                }
            }
        }

        getFinancialsTotal()
    }

    override fun onResume() {
        super.onResume()
        getFinancialsTotal()
    }

    private fun getFinancialsTotal() {
        viewModel.getFinancialsTotal().observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true, "financials-total")
                    }

                    is ResultState.Success -> {
                        showLoading(false, "financials-total")
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
                        showLoading(false, "financials-total")
                        showToast(result.error.toString())
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean, type: String) {
        if (type == "financials-total") {
            binding?.progressIndicatorFinancialsTotal?.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(
            requireActivity(), message, Toast.LENGTH_SHORT
        ).show()
    }

// private fun setupAction() {
//        binding?.btnFilter?.setOnClickListener { v: View ->
//            showMenu(v, R.menu.menu_filter_time_past, binding?.btnFilter!!)
//        }
//        binding?.btnCategory?.setOnClickListener { v: View ->
//            showMenu(v, R.menu.category_menu, binding?.btnCategory!!)
//        }
    // }

//    private fun showMenu(v: View, @MenuRes menuRes: Int, button: Button) {
//        val icArrowUp: Drawable? = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_arrow_up)
//        val icArrowDown: Drawable? = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_arrow_down)
//        val popup = PopupMenu(requireActivity(), v)
//        popup.menuInflater.inflate(menuRes, popup.menu)
//
//        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
//            when (menuItem.itemId) {
//                R.id.option_filter_1 -> {
//                    binding?.btnFilter?.text = menuItem.title
//                    setDrawableEnd(button, icArrowDown)
//                    true
//                }
//
//                R.id.option_filter_2 -> {
//                    binding?.btnFilter?.text = menuItem.title
//                    setDrawableEnd(button, icArrowDown)
//                    true
//                }
//
//                R.id.option_filter_3 -> {
//                    binding?.btnFilter?.text = menuItem.title
//                    setDrawableEnd(button, icArrowDown)
//                    true
//                }
//
//                R.id.option_category_1 -> {
//                    binding?.btnCategory?.text = menuItem.title
//                    setDrawableEnd(button, icArrowDown)
//                    true
//                }
//
//                R.id.option_category_2 -> {
//                    binding?.btnCategory?.text = menuItem.title
//                    setDrawableEnd(button, icArrowDown)
//                    true
//                }
//
//                R.id.option_category_3 -> {
//                    binding?.btnCategory?.text = menuItem.title
//                    setDrawableEnd(button, icArrowDown)
//                    true
//                }
//
//                else -> false
//            }
//        }
//        popup.setOnDismissListener {
//            setDrawableEnd(button, icArrowDown)
//        }
//        // Show the popup menu.
//        popup.show()
//        setDrawableEnd(button, icArrowUp)
//    }

//    private fun setDrawableEnd(button: Button, drawable: Drawable?) {
//        val drawables = button.compoundDrawablesRelative
//
//        // Set drawable baru sebagai drawableEnd
//        button.setCompoundDrawablesRelativeWithIntrinsicBounds(
//            drawables[0], // drawableStart
//            drawables[1], // drawableTop
//            drawable,     // drawableEnd
//            drawables[3]  // drawableBottom
//        )
//    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}