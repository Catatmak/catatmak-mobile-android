package com.bangkit.catatmak.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.catatmak.R
import com.bangkit.catatmak.data.response.FinancialsTodayDataItem
import com.bangkit.catatmak.databinding.ItemRowTransactionBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ListTransactionAdapter(private val onItemClick: (FinancialsTodayDataItem) -> Unit) :
    ListAdapter<FinancialsTodayDataItem, ListTransactionAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding =
            ItemRowTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val transaction = getItem(position)
        holder.bind(transaction)

        holder.itemView.setOnClickListener {
            onItemClick(transaction)
        }

    }

    class ListViewHolder(private val binding: ItemRowTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: FinancialsTodayDataItem) {
            with(binding) {
                with(transaction) {
                    tvItemName.text = title

                    val inputDate = createdAt
                    val inputFormat =
                        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    val outputFormat = SimpleDateFormat("dd MMMM, HH.mm", Locale.getDefault())
                    val date = inputFormat.parse(inputDate)
                    val formattedDate = date?.let { outputFormat.format(it) }

                    tvCreatedAt.text = formattedDate.toString()
                    tvCategoryName.text = category
                    if (tvCategoryName.text.toString() == "Tidak Terkategori") {
                        tvCategoryName.setBackgroundResource(R.drawable.background_red_with_radius)
                        tvCategoryName.setTextColor(
                            ContextCompat.getColor(
                                itemView.context,
                                android.R.color.white
                            )
                        )
                        tvCategoryName.setPadding(6, 6, 6, 6)
                    } else {
                        tvCategoryName.setBackgroundResource(android.R.color.transparent)
                        tvCategoryName.setTextColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.dark_blue
                            )
                        )
                        tvCategoryName.setPadding(0, 0, 0, 0)
                    }

                    if (type == "outcome") {
                        tvPrice.text = itemView.context.getString(R.string.price, "-", price)
                        tvPrice.setTextColor(Color.RED)
                    } else {
                        tvPrice.text = itemView.context.getString(R.string.price, "+", price)
                        tvPrice.setTextColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.dark_green
                            )
                        )
                    }
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FinancialsTodayDataItem>() {
            override fun areItemsTheSame(
                oldItem: FinancialsTodayDataItem,
                newItem: FinancialsTodayDataItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FinancialsTodayDataItem,
                newItem: FinancialsTodayDataItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}