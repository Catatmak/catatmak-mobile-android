package com.bangkit.catatmak.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.catatmak.R
import com.bangkit.catatmak.data.response.FinancialsDailyDataItem
import com.bangkit.catatmak.databinding.ItemRowOutcomeBinding
import com.bangkit.catatmak.ui.transaction.detail_outcome.DetailIncomeActivity
import com.bangkit.catatmak.utils.formatToCurrency

class IncomeAdapter :
    ListAdapter<FinancialsDailyDataItem, IncomeAdapter.ListViewHolder>(DIFF_CALLBACK) {

    private var position: Int = 0

    fun setPosition(pos: Int) {
        position = pos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowOutcomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding, position)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val outcome = getItem(position)

        holder.bind(outcome)

    }

    class ListViewHolder(private val binding: ItemRowOutcomeBinding, private val position: Int) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(outcome: FinancialsDailyDataItem) {
            with(binding) {
                with(outcome) {
                    tvTitle.text = title
                    tvTotal.text = formatToCurrency(sum.toInt())
                    tvCountItem.text =
                        itemView.context.getString(R.string.count_income, count.toString())

                    itemView.setOnClickListener {
                        val intent = Intent(itemView.context, DetailIncomeActivity::class.java)
                        intent.putExtra(DetailIncomeActivity.EXTRA_START_DATE, startDate)
                        intent.putExtra(DetailIncomeActivity.EXTRA_END_DATE, endDate)
                        intent.putExtra(DetailIncomeActivity.EXTRA_POSITION, position)
                        itemView.context.startActivity(intent)
                    }
                }
            }
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FinancialsDailyDataItem>() {
            override fun areItemsTheSame(
                oldItem: FinancialsDailyDataItem,
                newItem: FinancialsDailyDataItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FinancialsDailyDataItem,
                newItem: FinancialsDailyDataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}