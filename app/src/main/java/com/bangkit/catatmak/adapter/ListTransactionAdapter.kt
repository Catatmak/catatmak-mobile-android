package com.bangkit.catatmak.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.catatmak.R
import com.bangkit.catatmak.model.Transaction

class ListTransactionAdapter(private val listTransaction: ArrayList<Transaction>) :
    RecyclerView.Adapter<ListTransactionAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_row_transaction, parent, false)
        return ListViewHolder(view)
    }

    @SuppressLint("InflateParams")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (createdAt, itemName, categoryName, price, isPlus) = listTransaction[position]
        holder.createdAt.text = createdAt
        holder.itemName.text = itemName
        holder.categoryName.text = categoryName
        if (isPlus.toInt() == 1) {
            holder.price.text =
                holder.itemName.context.resources.getString(R.string.total_price, "+", "Rp.", price)
            val green = ContextCompat.getColor(holder.itemView.context, R.color.dark_green)
            holder.price.setTextColor(green)
        } else {
            holder.price.text =
                holder.itemName.context.resources.getString(R.string.total_price, "-", "Rp.", price)
            holder.price.setTextColor(Color.RED)
        }

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listTransaction[holder.adapterPosition]) }

    }

    override fun getItemCount(): Int = listTransaction.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val createdAt: TextView = itemView.findViewById(R.id.tvCreatedAt)
        val itemName: TextView = itemView.findViewById(R.id.tvItemName)
        val categoryName: TextView = itemView.findViewById(R.id.tvCategoryName)
        val price: TextView = itemView.findViewById(R.id.tvPrice)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Transaction)
    }
}