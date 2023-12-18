package com.bangkit.catatmak.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.catatmak.data.response.InsightDataItem
import com.bangkit.catatmak.databinding.ItemRowInsightBinding


class InsightAdapter() :
    ListAdapter<InsightDataItem, InsightAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowInsightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val insight = getItem(position)
        holder.bind(insight)

    }

    class ListViewHolder(private val binding: ItemRowInsightBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(insight: InsightDataItem) {
            with(binding) {
                with(insight) {
                    tvInsightTitle.text = title
                    tvInsightDesc.text = description
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<InsightDataItem>() {
            override fun areItemsTheSame(
                oldItem: InsightDataItem,
                newItem: InsightDataItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: InsightDataItem,
                newItem: InsightDataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}