package com.bangkit.catatmak.ui.transaction.summary.income

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class IncomeSectionsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment)
{
    override fun createFragment(position: Int): Fragment {
        var fragment = SummaryIncomeFragment()
        fragment.arguments = Bundle().apply {
            putInt(SummaryIncomeFragment.EXTRA_POSITION, position + 1)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }

}