package com.bangkit.catatmak.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.catatmak.R
import com.bangkit.catatmak.model.Expense
import com.bangkit.catatmak.ui.transaction.outcome.DetailOutcomeActivity

class ExpenseAdapter(private val list: ArrayList<Expense>) :  RecyclerView.Adapter<ExpenseAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_expense, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (dateTime, total, expensesAmount) = list[position]

        holder.dateTime.text = dateTime
        holder.total.text = holder.itemView.context.resources.getString(R.string.total_expenses, total)
        holder.expensesAmount.text = holder.itemView.context.resources.getString(R.string.expenses_amount, expensesAmount)

        holder.itemView.setOnClickListener {
            holder.itemView.context.startActivity(Intent(holder.itemView.context, DetailOutcomeActivity::class.java))
        }
    }

    override fun getItemCount(): Int = list.size

    class ListViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView) {
        val dateTime: TextView = itemView.findViewById(R.id.tvDateTime)
        val total: TextView = itemView.findViewById(R.id.tvTotalExpense)
        val expensesAmount: TextView = itemView.findViewById(R.id.tvExpensesAmount)
    }

}