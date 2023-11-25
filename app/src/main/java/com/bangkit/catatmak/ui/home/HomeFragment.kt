package com.bangkit.catatmak.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.ListTransactionAdapter
import com.bangkit.catatmak.databinding.FragmentHomeBinding
import com.bangkit.catatmak.model.Transaction
import com.google.android.material.bottomsheet.BottomSheetDialog


class HomeFragment : Fragment() {

    private val list = ArrayList<Transaction>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private lateinit var btnUpdate: Button
    private lateinit var btnDelate: Button
    private lateinit var edtExpenseName: EditText
    private lateinit var edtPrice: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        list.addAll(getListTransactions())
        setTransactionData()
    }


    override fun onResume() {
        super.onResume()
        list.clear()
        list.addAll(getListTransactions())
        setTransactionData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

//    private fun setProperHeightOfView() {
//        val layoutView = binding?.root
//        val layoutParams = layoutView?.layoutParams
//        if (layoutParams != null) {
//            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
//            layoutView.requestLayout()
//        }
//    }

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

        btnUpdate = view.findViewById(R.id.btnUpdate)
        btnDelate = view.findViewById(R.id.btnDelete)
        edtExpenseName = view.findViewById(R.id.edtExpenseName)
        edtPrice = view.findViewById(R.id.edtPrice)

        edtExpenseName.setText(data.itemName)
        edtPrice.setText(data.price)

        btnUpdate.setOnClickListener {
            Toast.makeText(context, "Berhasil mengupdate transaksi", Toast.LENGTH_SHORT).show()
        }

        btnDelate.setOnClickListener {
            Toast.makeText(context, "Berhasil delate transaksi", Toast.LENGTH_SHORT).show()
        }

        bsDialog.setCancelable(true)
        bsDialog.setContentView(view)
        bsDialog.show()
    }
}