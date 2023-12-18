package com.bangkit.catatmak.ui.add_transaction

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.CustomArrayAdapter
import com.bangkit.catatmak.data.ResultState
import com.bangkit.catatmak.databinding.FragmentAddTransactionSheetBinding
import com.bangkit.catatmak.ui.ViewModelFactory
import com.bangkit.catatmak.utils.formatDateToISO8601
import com.bangkit.catatmak.utils.formatISO8601ToCustom
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar
import java.util.TimeZone

class AddTransactionSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddTransactionSheetBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModels<AddTransactionViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private var formattedDateToISO8601: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTransactionSheetBinding.inflate(layoutInflater, container, false)
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
        (parentFragment as? AddTransactionFragment)?.isSheetShown = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAction()
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

                binding?.actvCategory?.setOnItemClickListener { parent, view, position, id ->
                    adapter.setSelectedPosition(position)
                }
            }
        }

        binding?.tbNoteTakingOption?.isSingleSelection = true
        binding?.btnType?.let { binding?.tbNoteTakingOption?.id }
        binding?.btnType?.apply {
            setBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.dark_blue
                )
            )
            setTextColor(Color.WHITE)
        }
        createFinancial("outcome")
        binding?.tbNoteTakingOption?.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            val button: Button = toggleButton.findViewById(checkedId)
            if (isChecked) {
                if (button.id == binding?.btnType?.id) {
                    binding?.btnIncome?.apply {
                        setBackgroundColor(Color.WHITE)
                        setTextColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.dark_blue
                            )
                        )
                    }
                    button.apply {
                        setBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.dark_blue
                            )
                        )
                        setTextColor(Color.WHITE)
                    }

                    viewModel.loadOutcomeCategory()

                    binding?.edtExpenseName?.setText("")
                    binding?.edtDate?.setText("")
                    binding?.edtPrice?.setText("")
                    binding?.actvCategory?.setText("")
                    binding?.tvPrice?.text = resources.getString(R.string.tv_price)
                    binding?.tvExpenseName?.text = resources.getString(R.string.expense_name)
                    binding?.edtExpenseName?.hint = getString(R.string.expense_name_hint)

                    createFinancial("outcome")
                } else if (button.id == binding?.btnIncome?.id) {
                    binding?.btnType?.apply {
                        setBackgroundColor(Color.WHITE)
                        setTextColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.dark_blue
                            )
                        )
                    }
                    button.apply {
                        setBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.dark_blue
                            )
                        )
                        setTextColor(Color.WHITE)
                    }

                    viewModel.loadIncomeCategory()
                    viewModel.categoryItems.observe(viewLifecycleOwner) { categories ->
                        categories?.let {
                            val adapter = CustomArrayAdapter(
                                requireContext(),
                                R.layout.dropdown_item_layout,
                                categories.toTypedArray()
                            )
                            binding?.actvCategory?.setAdapter(adapter)

                            binding?.actvCategory?.setOnItemClickListener { parent, view, position, id ->
                                adapter.setSelectedPosition(position)
                            }
                        }
                    }

                    binding?.edtExpenseName?.setText("")
                    binding?.edtDate?.setText("")
                    binding?.edtPrice?.setText("")
                    binding?.actvCategory?.setText("")
                    binding?.tvPrice?.text = resources.getString(R.string.tv_total_income)
                    binding?.tvExpenseName?.text = resources.getString(R.string.income_name)
                    binding?.edtExpenseName?.hint = getString(R.string.income_name_hint)

                    createFinancial("income")
                }
            }
        }
    }

    private fun createFinancial(type: String) {
        binding?.btnSave?.setOnClickListener {

            val title = binding?.edtExpenseName?.text.toString().trim()
            val price = binding?.edtPrice?.text.toString()
            val category = binding?.actvCategory?.text.toString()
            val createdAt = binding?.edtDate?.text.toString()

            val isInputEmpty =
                title.isEmpty() || price.isEmpty() || createdAt.isEmpty() || category.isEmpty()

            binding?.tfExpenseName?.error =
                if (title.isEmpty()) getString(R.string.empty_input) else null
            binding?.tfPrice?.error =
                if (price.isEmpty()) getString(R.string.empty_input) else null
            binding?.tfDate?.error =
                if (createdAt.isEmpty()) getString(R.string.empty_input) else null
            binding?.tfCategory?.error =
                if (category.isEmpty()) getString(R.string.empty_input) else null

            if (!isInputEmpty) {
                formattedDateToISO8601?.let { date ->
                    viewModel.createFinancial(title, price, category, type, date)
                        .observe(requireActivity()) { result ->
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
        binding?.pbAddTransaction?.visibility =
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