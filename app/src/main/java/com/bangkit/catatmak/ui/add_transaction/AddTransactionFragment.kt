package com.bangkit.catatmak.ui.add_transaction

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.bangkit.catatmak.R
import com.bangkit.catatmak.databinding.FragmentAddTransactionBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Calendar

class AddTransactionFragment : Fragment() {

    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding

    private lateinit var btnSave: Button
    private lateinit var edtDate: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTransactionBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAction()
    }

    private fun setUpAction() {
        binding?.btnRecordManually?.setOnClickListener {
           addTransaction()
        }
    }

    private fun addTransaction() {
        val bsDialog = BottomSheetDialog(requireActivity())
        val view = layoutInflater.inflate(R.layout.bs_add_transaction, null)


        edtDate = view.findViewById(R.id.edtDate)
        btnSave = view.findViewById(R.id.btnSave)

        edtDate.setOnClickListener {
            showDatePickerDialog()
        }

        btnSave.setOnClickListener {
            Toast.makeText(context, "Transaksi berhasil ditambahkan", Toast.LENGTH_SHORT).show()
        }
        bsDialog.setCancelable(true)
        bsDialog.setContentView(view)
        bsDialog.show()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
            // Aksi yang diambil ketika tanggal dipilih
            val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
            edtDate.setText(selectedDate) // Set tanggal yang dipilih ke EditText
        }, year, month, day)

        datePickerDialog.show()
    }

    companion object {

    }
}