package com.bangkit.catatmak.ui.transaction.summary.outcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OutcomeSectionsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment)
{
    override fun createFragment(position: Int): Fragment {
        val fragment = SummaryOutcomeFragment()
        fragment.arguments = Bundle().apply {
            putInt(SummaryOutcomeFragment.EXTRA_POSITION, position + 1)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 3
    }

}