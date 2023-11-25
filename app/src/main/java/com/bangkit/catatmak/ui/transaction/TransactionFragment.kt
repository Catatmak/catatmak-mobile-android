package com.bangkit.catatmak.ui.transaction

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.ListTransactionAdapter
import com.bangkit.catatmak.databinding.FragmentTransactionBinding
import com.bangkit.catatmak.model.Transaction
import com.google.android.material.bottomsheet.BottomSheetDialog

class TransactionFragment : Fragment() {

    private val list = ArrayList<Transaction>()
    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding

    private lateinit var edtExpenseName: EditText
    private lateinit var edtPrice: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        setupAction()

        list.addAll(getListTransactions())
        setTransactionData()
    }

    override fun onResume() {
        super.onResume()
        list.clear()
        list.addAll(getListTransactions())
        setTransactionData()
    }

    private fun setupAction() {
        binding?.btnFilter?.setOnClickListener { v: View ->
            showMenu(v, R.menu.filter_menu, binding?.btnFilter!!)
        }
        binding?.btnCategory?.setOnClickListener { v: View ->
            showMenu(v, R.menu.category_menu, binding?.btnCategory!!)
        }
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int, button: Button) {
        val icArrowUp: Drawable? = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_arrow_up)
        val icArrowDown: Drawable? = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_arrow_down)
        val popup = PopupMenu(requireActivity(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.option_filter_1 -> {
                    binding?.btnFilter?.text = menuItem.title
                    setDrawableEnd(button, icArrowDown)
                    true
                }

                R.id.option_filter_2 -> {
                    binding?.btnFilter?.text = menuItem.title
                    setDrawableEnd(button, icArrowDown)
                    true
                }

                R.id.option_filter_3 -> {
                    binding?.btnFilter?.text = menuItem.title
                    setDrawableEnd(button, icArrowDown)
                    true
                }

                R.id.option_category_1 -> {
                    binding?.btnCategory?.text = menuItem.title
                    setDrawableEnd(button, icArrowDown)
                    true
                }

                R.id.option_category_2 -> {
                    binding?.btnCategory?.text = menuItem.title
                    setDrawableEnd(button, icArrowDown)
                    true
                }

                R.id.option_category_3 -> {
                    binding?.btnCategory?.text = menuItem.title
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

    private fun setupRecyclerView() {
        binding?.rvTransactions?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun getListTransactions(): ArrayList<Transaction> {
        val dataCreatedAt = resources.getStringArray(R.array.data_created_at)
        val dataItemName = resources.getStringArray(R.array.data_item_name)
        val dataCategoryName = resources.getStringArray(R.array.data_category_name)
        val dataPrice = resources.getStringArray(R.array.data_price)
        val datIsPlus = resources.getStringArray(R.array.data_is_plus)
        val listTransaction = ArrayList<Transaction>()
        for (i in dataItemName.indices) {
            val transaction = Transaction(
                dataCreatedAt[i],
                dataItemName[i],
                dataCategoryName[i],
                dataPrice[i],
                datIsPlus[i]
            )
            listTransaction.add(transaction)
        }
        return listTransaction
    }

    private fun setTransactionData() {
        val listTransactionAdapter = ListTransactionAdapter(list)
        binding?.rvTransactions?.adapter = listTransactionAdapter

        listTransactionAdapter.setOnItemClickCallback(object :
            ListTransactionAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Transaction) {
              updateTransaction(data)
            }
        })
    }

    private fun updateTransaction(data: Transaction) {
        val bsDialog = BottomSheetDialog(requireActivity())
        val view = layoutInflater.inflate(R.layout.bs_update_transaction, null)

        val btnUpdate = view.findViewById<Button>(R.id.btnUpdate)
        edtExpenseName = view.findViewById(R.id.edtExpenseName)
        edtPrice = view.findViewById(R.id.edtPrice)

        edtExpenseName.setText(data.itemName)
        edtPrice.setText(data.price)

        btnUpdate.setOnClickListener {
            Toast.makeText(context, "Berhasil mengupdate data", Toast.LENGTH_SHORT).show()
        }
        bsDialog.setCancelable(true)
        bsDialog.setContentView(view)
        bsDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}