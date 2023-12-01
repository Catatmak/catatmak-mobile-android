package com.bangkit.catatmak.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bangkit.catatmak.R

class CustomArrayAdapter(
    context: Context,
    private val resource: Int,
    private val items: Array<String>
) : ArrayAdapter<String>(context, resource, items) {

    private var selectedPosition = -1

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(resource, parent, false)

        val textView: TextView = view.findViewById(R.id.tvDropdownItem)
        textView.text = items[position]

        if (position == selectedPosition) {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_blue))
            view.findViewById<TextView>(R.id.tvDropdownItem).setTextColor(Color.WHITE)
        } else {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            view.findViewById<TextView>(R.id.tvDropdownItem).setTextColor(ContextCompat.getColor(context, R.color.dark_blue))
        }

        return view
    }
}
