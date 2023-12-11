package com.bangkit.catatmak.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.catatmak.R
import com.bangkit.catatmak.data.api.response.DataItem
import com.bangkit.catatmak.databinding.ItemRowTransactionBinding
import com.bangkit.catatmak.model.Transaction
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.Locale

class ListTransactionAdapter :
    ListAdapter<DataItem, ListTransactionAdapter.ListViewHolder>(DIFF_CALLBACK) {

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
    }

    class ListViewHolder(private val binding: ItemRowTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var onItemClickCallback: OnItemClickCallback

        fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
            this.onItemClickCallback = onItemClickCallback
        }


        fun bind(transaction: DataItem) {
            with(binding) {
                with(transaction) {
                    tvItemName.text = title

                    val inputDate = createdAt
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    val outputFormat = SimpleDateFormat("dd MMMM, HH.mm", Locale.getDefault())
                    val date = inputFormat.parse(inputDate)
                    val formattedDate = date?.let { outputFormat.format(it) }
                    Log.d("Date", "$formattedDate")
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
//            itemView.setOnClickListener {
//                onItemClickCallback.onItemClicked(transaction)
//            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Transaction)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}