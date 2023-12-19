package com.bangkit.catatmak.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.catatmak.R
import com.bangkit.catatmak.data.response.UncategorizeDataItem
import com.bangkit.catatmak.databinding.ItemRowCategorizeBinding
import com.bangkit.catatmak.utils.formatDateTime
import com.bangkit.catatmak.utils.formatToCurrency

class UncategorizeAdapter(private val onMenuChanged: (UncategorizeDataItem, String) -> Unit) :
    ListAdapter<UncategorizeDataItem, UncategorizeAdapter.ListViewHolder>(DIFF_CALLBACK) {

    private var selectedCategory: String = ""

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding =
            ItemRowCategorizeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val transaction = getItem(position)

        holder.binding.btnChooseCategory.setOnClickListener { v: View ->
            showMenu(holder.itemView.context, v, R.menu.category_menu, holder.binding.btnChooseCategory, transaction)
        }

        holder.bind(transaction)
    }

    class ListViewHolder(
        val binding: ItemRowCategorizeBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: UncategorizeDataItem) {
            with(binding) {
                with(transaction) {
                    Log.d("CATEGORY", category)
                    val cleanPrice = price.replace(".", "")
                    tvCreatedAt.text = formatDateTime(updatedAt)
                    tvItemName.text = title
                    tvItemPrice.text = formatToCurrency(cleanPrice.toInt())
                    btnChooseCategory.text = category
                }
            }
        }
    }

    private fun showMenu(context: Context, v: View, @MenuRes menuRes: Int, button: Button, transaction: UncategorizeDataItem) {
        val icArrowUp: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_arrow_up)
        val icArrowDown: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_arrow_down)
        val popup = PopupMenu(context, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            selectedCategory = menuItem.title.toString()
            when (menuItem.itemId) {
                R.id.option_category_1 -> {
                    button.text = menuItem.title
                    setDrawableEnd(button, icArrowDown)
                    onMenuChanged(transaction, selectedCategory)
                    true
                }

                R.id.option_category_2 -> {
                    button.text = menuItem.title
                    setDrawableEnd(button, icArrowDown)
                    onMenuChanged(transaction, selectedCategory)
                    true
                }

                R.id.option_category_3 -> {
                    button.text = menuItem.title
                    setDrawableEnd(button, icArrowDown)
                    onMenuChanged(transaction, selectedCategory)
                    true
                }
                R.id.option_category_4 -> {
                    button.text = menuItem.title
                    setDrawableEnd(button, icArrowDown)
                    onMenuChanged(transaction, selectedCategory)
                    true
                }

                else -> false
            }
        }
        popup.setOnDismissListener {
            setDrawableEnd(button, icArrowDown)
        }
        // Show the popup menu.
        popup.show()
        setDrawableEnd(button, icArrowUp)
    }

    private fun setDrawableEnd(button: Button, drawable: Drawable?) {
        val drawables = button.compoundDrawablesRelative

        button.setCompoundDrawablesRelativeWithIntrinsicBounds(
            drawables[0],
            drawables[1],
            drawable,
            drawables[3]
        )
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UncategorizeDataItem> =
            object : DiffUtil.ItemCallback<UncategorizeDataItem>() {
                override fun areItemsTheSame(
                    oldItem: UncategorizeDataItem,
                    newItem: UncategorizeDataItem
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: UncategorizeDataItem,
                    newItem: UncategorizeDataItem
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}