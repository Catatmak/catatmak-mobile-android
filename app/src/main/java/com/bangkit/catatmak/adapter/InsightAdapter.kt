package com.bangkit.catatmak.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.catatmak.R
import com.bangkit.catatmak.model.Insight

class InsightAdapter(private val insight: ArrayList<Insight>) :
    RecyclerView.Adapter<InsightAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_row_insight, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (createdAt, insightTitle, insightDesc, isFavorite) = insight[position]

        holder.createdAt.text = createdAt
        holder.insightTitle.text = insightTitle
        holder.insightDesc.text = insightDesc

        if (isFavorite.toInt() == 1) {
            holder.isFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else  {
            holder.isFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    override fun getItemCount(): Int = insight.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val createdAt: TextView = itemView.findViewById(R.id.tvCreatedAt)
        val insightTitle: TextView = itemView.findViewById(R.id.tvInsightTitle)
        val insightDesc: TextView = itemView.findViewById(R.id.tvInsightDesc)
        val isFavorite: ImageButton = itemView.findViewById(R.id.ibFavorite)
    }
}