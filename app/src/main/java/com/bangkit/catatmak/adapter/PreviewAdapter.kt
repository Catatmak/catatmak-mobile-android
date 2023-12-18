package com.bangkit.catatmak.adapter

import android.content.Context
import android.graphics.drawable.Drawable
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
import com.bangkit.catatmak.data.response.OCRDataItem
import com.bangkit.catatmak.databinding.ItemRowPreviewBinding
import com.bangkit.catatmak.utils.formatToCurrency
import com.bangkit.catatmak.utils.getCurrentDateTimeFormatted

class PreviewAdapter(
    private val onDeleteClick: (OCRDataItem) -> Unit,
    private val onEditClick: (OCRDataItem) -> Unit,
    private val onMenuChanged: (OCRDataItem, String) -> Unit
) : ListAdapter<OCRDataItem, PreviewAdapter.ListViewHolder>(DIFF_CALLBACK) {

    private var selectedCategory: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val transaction = getItem(position)

        holder.binding.btnUpdate.setOnClickListener {
            onEditClick(transaction)
        }
        holder.binding.btnDelate.setOnClickListener {
            onDeleteClick(transaction)
        }

        holder.binding.btnChooseCategory.setOnClickListener { v: View ->
            showMenu(holder.itemView.context, v, R.menu.category_menu, holder.binding.btnChooseCategory, transaction)
        }

        holder.bind(transaction)

    }

    class ListViewHolder(
        val binding: ItemRowPreviewBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: OCRDataItem) {
            with(binding) {
                with(transaction) {
                    val currentDateTime = getCurrentDateTimeFormatted()
                    tvCreatedAt.text = currentDateTime
                    tvItemName.text = title
                    val cleanPrice = price.replace(".", "")
                    tvItemPrice.text = formatToCurrency(cleanPrice.toInt())
                    if (category == "") {
                        btnChooseCategory.text = itemView.context.getString(R.string.no_category)
                    } else {
                        btnChooseCategory.text = category
                    }
                }
            }
        }
    }

    private fun showMenu(context: Context, v: View, @MenuRes menuRes: Int, button: Button, transaction: OCRDataItem) {
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

        // Set drawable baru sebagai drawableEnd
        button.setCompoundDrawablesRelativeWithIntrinsicBounds(
            drawables[0], // drawableStart
            drawables[1], // drawableTop
            drawable,     // drawableEnd
            drawables[3]  // drawableBottom
        )
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<OCRDataItem> =
            object : DiffUtil.ItemCallback<OCRDataItem>() {
                override fun areItemsTheSame(
                    oldItem: OCRDataItem,
                    newItem: OCRDataItem
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: OCRDataItem,
                    newItem: OCRDataItem
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}