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
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.CustomArrayAdapter
import com.bangkit.catatmak.databinding.FragmentAddTransactionSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddTransactionSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddTransactionSheetBinding? = null
    private val binding get() = _binding

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

        binding?.btnSave?.setOnClickListener {
            Toast.makeText(
                requireActivity(),
                "Transaksi berhasil ditambahkan",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding?.edtDate?.setOnClickListener {
            showDatePickerDialog(binding?.edtDate!!)
        }

        binding?.ivClose?.setOnClickListener {
            dismiss()
        }

        val items = arrayOf("Makanan", "Shopping", "Hiburan")

        val adapter = CustomArrayAdapter(requireContext(), R.layout.dropdown_item_layout, items)

        binding?.actvCategory?.setAdapter(adapter)

        binding?.actvCategory?.setOnItemClickListener { parent, view, position, id ->
            adapter.setSelectedPosition(position)
        }

        binding?.tbNoteTakingOption?.isSingleSelection = true
        binding?.btnExpenses?.let { binding?.tbNoteTakingOption?.id }
        binding?.btnExpenses?.apply {
            setBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.dark_blue
                )
            )
            setTextColor(Color.WHITE)
        }
        binding?.tbNoteTakingOption?.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            val button: Button = toggleButton.findViewById(checkedId)
            if (isChecked) {
                if (button.id == binding?.btnExpenses?.id) {
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

                    binding?.edtExpenseName?.setText("")
                    binding?.edtDate?.setText("")
                    binding?.edtPrice?.setText("")
                    binding?.actvCategory?.setText("")
                    binding?.tvPrice?.text = resources.getString(R.string.tv_price)
                    binding?.tvExpenseName?.text = resources.getString(R.string.expense_name)
                } else if (button.id == binding?.btnIncome?.id) {
                    binding?.btnExpenses?.apply {
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
                    binding?.edtExpenseName?.setText("")
                    binding?.edtDate?.setText("")
                    binding?.edtPrice?.setText("")
                    binding?.actvCategory?.setText("")
                    binding?.tvPrice?.text = resources.getString(R.string.tv_total_income)
                    binding?.tvExpenseName?.text = resources.getString(R.string.income_name)
                }
            }
        }
    }

    private fun showDatePickerDialog(edtDate: TextInputEditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
                val formattedDate = formatDate(dayOfMonth, monthOfYear + 1, year)
                edtDate.setText(formattedDate) // Set tanggal yang dipilih ke EditText
            }, year, month, day)

        datePickerDialog.show()
    }

    private fun formatDate(day: Int, month: Int, year: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day)
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}