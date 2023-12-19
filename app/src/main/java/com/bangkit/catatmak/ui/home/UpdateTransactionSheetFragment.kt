package com.bangkit.catatmak.ui.home

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.CustomArrayAdapter
import com.bangkit.catatmak.data.ResultState
import com.bangkit.catatmak.data.response.FinancialsTodayDataItem
import com.bangkit.catatmak.databinding.FragmentUpdateTransactionSheetBinding
import com.bangkit.catatmak.ui.ViewModelFactory
import com.bangkit.catatmak.utils.convertCurrencyStringToNumber
import com.bangkit.catatmak.utils.convertISO8601StringToCustom
import com.bangkit.catatmak.utils.formatDateToISO8601
import com.bangkit.catatmak.utils.formatISO8601ToCustom
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar
import java.util.TimeZone

interface BottomSheetDismissListener {
    fun onBottomSheetDismissed()
}
class UpdateTransactionSheetFragment(private val transaction: FinancialsTodayDataItem, private val listener: BottomSheetDismissListener? = null) :
    BottomSheetDialogFragment() {

    private var _binding: FragmentUpdateTransactionSheetBinding? = null
    private val binding get() = _binding

    private var formattedDateToISO8601: String? = ""

    private val viewModel by viewModels<HomeViewModel> {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAction()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        listener?.onBottomSheetDismissed()
        // Reset isSheetShown to allow the sheet to be shown again
        (parentFragment as? HomeFragment)?.isSheetShown = false
    }

    private fun setUpAction() {

        binding?.edtDate?.setOnClickListener {
            showDateTimePickerDialog(binding?.edtDate!!)
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
        binding?.btnSave?.setOnClickListener {
            updateData()
        }
        binding?.btnDelete?.setOnClickListener {
            deleteData()
        }
    }

    private fun deleteData() {
        viewModel.deleteFinancial(transaction.id)
            .observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }

                        is ResultState.Success -> {
                            showLoading(false)
                            showToast(result.data.message)
                            dismiss()
                        }

                        is ResultState.Error -> {
                            showLoading(false)
                            showToast(result.error.toString())
                        }
                    }
                }
            }
    }

    private fun updateData() {

        val id = transaction.id
        val type = transaction.type
        val createdAt = transaction.createdAt
        val title = binding?.edtOutcomeName?.text.toString().trim()
        val price = binding?.edtPrice?.text.toString()
        val category = binding?.actvCategory?.text.toString()

        val isInputEmpty =
            title.isEmpty() || price.isEmpty()  || category.isEmpty()

        binding?.tfOutcomeName?.error =
            if (title.isEmpty()) getString(R.string.empty_input) else null
        binding?.tfPrice?.error =
            if (price.isEmpty()) getString(R.string.empty_input) else null
        binding?.tfCategory?.error =
            if (category.isEmpty()) getString(R.string.empty_input) else null

        if (!isInputEmpty) {
            formattedDateToISO8601?.let { _ ->
                viewModel.updateFinancial(id, title, price, category, type, createdAt)
                    .observe(viewLifecycleOwner) { result ->
                        if (result != null) {
                            when (result) {
                                is ResultState.Loading -> {
                                    showLoading(true)
                                }

                                is ResultState.Success -> {
                                    showLoading(false)
                                    showToast(result.data.message)
                                    dismiss()
                                }

                                is ResultState.Error -> {
                                    showLoading(false)
                                    showToast(result.error.toString())
                                }
                            }
                        }
                    }
            }
        }

    }

    private fun setDefaultData() {
        val data = transaction
        if (transaction.type == "income") {
            viewModel.loadIncomeCategory()
            binding?.btnType?.text = getString(R.string.income)
            binding?.tvExpenseName?.text = getString(R.string.income_name)
            binding?.tvPrice?.text = getString(R.string.tv_total_income)
            binding?.edtOutcomeName?.hint = getString(R.string.income_name_hint)
        }

        with(data) {
            binding?.edtDate?.setText(convertISO8601StringToCustom(createdAt))
            binding?.edtDate?.isEnabled = false
            binding?.edtOutcomeName?.setText(title)
            binding?.edtPrice?.setText(convertCurrencyStringToNumber(price).toString())
            binding?.actvCategory?.setText(category)
        }
    }

    private fun showDateTimePickerDialog(editTextDateTime: EditText) {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"))

        val datePickerDialog =
            DatePickerDialog(
                requireContext(),
                { _, year, monthOfYear, dayOfMonth ->
                    val selectedCalendar = Calendar.getInstance()
                    selectedCalendar.set(year, monthOfYear, dayOfMonth)

                    val currentTime = Calendar.getInstance()
                    selectedCalendar.set(
                        Calendar.HOUR_OF_DAY,
                        currentTime.get(Calendar.HOUR_OF_DAY)
                    )
                    selectedCalendar.set(Calendar.MINUTE, currentTime.get(Calendar.MINUTE))

                    formattedDateToISO8601 = formatDateToISO8601(selectedCalendar)
                    editTextDateTime.setText(formatISO8601ToCustom(selectedCalendar))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

        datePickerDialog.show()
    }




    private fun showLoading(isLoading: Boolean) {
        binding?.pbUpdateTransaction?.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(
            requireActivity(), message, Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}