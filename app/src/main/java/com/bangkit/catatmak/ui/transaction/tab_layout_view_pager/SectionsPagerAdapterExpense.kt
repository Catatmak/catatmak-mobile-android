package com.bangkit.catatmak.ui.transaction.tab_layout_view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapterExpense(activity: FragmentActivity) : FragmentStateAdapter(activity)
{
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ExpenseSummaryFragment()
            1 -> fragment = ExpenseSummary2Fragment()
            2 -> fragment = ExpenseSummary3Fragment()
            else -> ExpenseSummaryFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 3
    }

}