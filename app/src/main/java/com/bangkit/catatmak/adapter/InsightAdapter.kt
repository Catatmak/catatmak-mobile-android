package com.bangkit.catatmak.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.catatmak.R
import com.bangkit.catatmak.model.Insight

class InsightAdapter(
    private val insight: ArrayList<Insight>,
    private val onFavoriteClick: (Int, Boolean) -> Unit
) :
    RecyclerView.Adapter<InsightAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_row_insight, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val currentInsight = insight[position]

        holder.createdAt.text = currentInsight.createdAt
        holder.insightTitle.text = currentInsight.insightTitle
        holder.insightDesc.text = currentInsight.insightDesc

        if (currentInsight.isFavorite.toInt() == 1) {
            holder.isFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            holder.isFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }

        holder.isFavorite.setOnClickListener {
            // Toggle the favorite status
            currentInsight.isFavorite = (1 - currentInsight.isFavorite.toInt()).toString()
            notifyItemChanged(position)

            // Pass the updated favorite status to the fragment
            onFavoriteClick.invoke(position, currentInsight.isFavorite.toInt() == 1)
        }
    }

    override fun getItemCount(): Int = insight.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val createdAt: TextView = itemView.findViewById(R.id.tvCreatedAt)
        val insightTitle: TextView = itemView.findViewById(R.id.tvInsightTitle)
        val insightDesc: TextView = itemView.findViewById(R.id.tvInsightDesc)
        val isFavorite: ImageView = itemView.findViewById(R.id.ibFavorite)
    }
}