package com.bangkit.catatmak.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.MenuRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.catatmak.R
import com.bangkit.catatmak.model.Transaction

class CategoriesAdapter(private val list: ArrayList<Transaction>, private val isAddPhotoPage: Boolean) :
    RecyclerView.Adapter<CategoriesAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view = if (isAddPhotoPage) {
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_preview, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_categories, parent, false)
        }

        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (createdAt, itemName, categoryName, price, _) = list[position]
        holder.createdAt.text = createdAt
        holder.itemName.text = itemName
        holder.itemPrice.text =  holder.itemName.context.resources.getString(R.string.total_expenses, price)
        holder.chooseCategory.text = categoryName

        holder.chooseCategory.setOnClickListener { v: View ->
            showMenu(holder.itemView.context, v, R.menu.category_menu, holder.chooseCategory)
        }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val createdAt: TextView = itemView.findViewById(R.id.tvCreatedAt)
        val itemName: TextView = itemView.findViewById(R.id.tvItemName)
        val itemPrice: TextView = itemView.findViewById(R.id.tvItemPrice)
        val chooseCategory: Button = itemView.findViewById(R.id.btnChooseCategory)
    }

    private fun showMenu(context: Context, v: View, @MenuRes menuRes: Int, button: Button) {
        val icArrowUp: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_arrow_up)
        val icArrowDown: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_arrow_down)
        val popup = PopupMenu(context, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.option_category_1 -> {
                    button.text = menuItem.title
                    setDrawableEnd(button, icArrowDown)
                    true
                }

                R.id.option_category_2 -> {
                    button.text = menuItem.title
                    setDrawableEnd(button, icArrowDown)
                    true
                }

                R.id.option_category_3 -> {
                    button.text = menuItem.title
                    setDrawableEnd(button, icArrowDown)
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

}
