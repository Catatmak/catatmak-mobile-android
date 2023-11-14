package com.bangkit.catatmak.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.ListTransactionAdapter
import com.bangkit.catatmak.databinding.FragmentHomeBinding
import com.bangkit.catatmak.model.Transaction

class HomeFragment : Fragment() {

    private val list = ArrayList<Transaction>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

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
        setProperHeightOfView()
        list.clear()
        list.addAll(getListTransactions())
        setTransactionData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setProperHeightOfView() {
        val layoutView = binding?.root
        val layoutParams = layoutView?.layoutParams
        if (layoutParams != null) {
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            layoutView.requestLayout()
        }
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
    }
}