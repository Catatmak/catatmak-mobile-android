package com.bangkit.catatmak.ui.home

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.CustomArrayAdapter
import com.bangkit.catatmak.databinding.FragmentUpdateTransactionSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UpdateTransactionSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentUpdateTransactionSheetBinding? = null
    private val binding get() = _binding


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
        // Reset isSheetShown to allow the sheet to be shown again
        (parentFragment as? HomeFragment)?.isSheetShown = false
    }

    private fun setUpAction() {

        binding?.btnSave?.setOnClickListener {
            Toast.makeText(
                requireActivity(),
                "Transaksi berhasil ditambahkan",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding?.btnDelete?.setOnClickListener {
            Toast.makeText(
                requireActivity(),
                "Transaksi berhasil dihapus",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding?.edtDate?.setOnClickListener {
            showDatePickerDialog(binding?.edtDate!!)
        }

        val items = arrayOf("Makanan & Minuman", "Transportasi", "Uang Sekolah", "Party")

        val adapter = CustomArrayAdapter(requireContext(), R.layout.dropdown_item_layout, items)

        binding?.actvCategory?.setAdapter(adapter)

        binding?.actvCategory?.setOnItemClickListener { parent, view, position, id ->
            adapter.setSelectedPosition(position)
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