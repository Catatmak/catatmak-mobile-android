package com.bangkit.catatmak.ui.add_transaction.add_with_photo

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.CustomArrayAdapter
import com.bangkit.catatmak.data.response.OCRDataItem
import com.bangkit.catatmak.databinding.FragmentUpdateTransactionSheetBinding
import com.bangkit.catatmak.ui.ViewModelFactory
import com.bangkit.catatmak.utils.convertCurrencyStringToNumber
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class UpdateBulkTransactionSheetFragment(
    private val transaction: OCRDataItem,
    private val transactionUpdateListener: UpdateTransactionListener,
    private val transactionDeleteListener: DeleteTransactionListener,
) :
    BottomSheetDialogFragment() {

    private var _binding: FragmentUpdateTransactionSheetBinding? = null
    private val binding get() = _binding


    private val viewModel by viewModels<AddWithPhotoViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateTransactionSheetBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isHideable = true
            behavior.skipCollapsed = true
            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING && behavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }

            })
        }

        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
//        updateListener.onDataUpdated()
        // Reset isSheetShown to allow the sheet to be shown again
        (requireActivity() as? AddWithPhotoActivity)?.isSheetShown = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAction()
    }

    private fun setUpAction() {

        binding?.btnSave?.setOnClickListener {
            viewModel.selectTransaction(transaction)
            val title = binding?.edtOutcomeName?.text.toString().trim()
            val price = binding?.edtPrice?.text.toString().trim()
            val category = binding?.actvCategory?.text.toString().trim()

            val isInputEmpty =
                title.isEmpty() || price.isEmpty() || category.isEmpty()

            binding?.tfOutcomeName?.error =
                if (title.isEmpty()) getString(R.string.empty_input) else null

            binding?.tfPrice?.error =
                if (price.isEmpty()) getString(R.string.empty_input) else null

            binding?.tfCategory?.error =
                if (category.isEmpty()) getString(R.string.empty_input) else null

            if (!isInputEmpty) {
                viewModel.selectTransaction(transaction)
                val updatedTransaction = transaction.copy(
                    title = title,
                    price = price,
                    category = category
                )

                Log.d("UPDATED_BRO", "$updatedTransaction")

                transactionUpdateListener.onUpdateTransaction(updatedTransaction)
                dismiss()
            }
        }


        binding?.btnDelete?.setOnClickListener {
            viewModel.selectTransaction(transaction)

            transactionDeleteListener.onDeleteTransaction(transaction)
            dismiss()
        }

        binding?.ivClose?.setOnClickListener {
            dismiss()
        }

        viewModel.categoryItems.observe(viewLifecycleOwner) { categories ->
            categories?.let {
                val adapter = CustomArrayAdapter(
                    requireContext(),
                    R.layout.dropdown_item_layout,
                    categories.toTypedArray()
                )
                binding?.actvCategory?.setAdapter(adapter)

                binding?.actvCategory?.setOnItemClickListener { _, _, position, _ ->
                    adapter.setSelectedPosition(position)
                }
            }
        }

        setDefaultData()
    }

    private fun setDefaultData() {
        val data = transaction
        if (transaction.type == "income") {
            viewModel.loadIncomeCategory()
            binding?.btnType?.text = getString(R.string.income)
            binding?.tvExpenseName?.text = getString(R.string.income_name)
            binding?.tvPrice?.text = getString(R.string.tv_total_income)
            binding?.actvCategory?.setText(getString(R.string.no_category))
        }

        with(data) {
            binding?.tvDate?.visibility = View.GONE
            binding?.edtDate?.visibility = View.GONE
            binding?.edtOutcomeName?.setText(title)
            binding?.edtPrice?.setText(convertCurrencyStringToNumber(price).toString())
            binding?.actvCategory?.setText(category)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}