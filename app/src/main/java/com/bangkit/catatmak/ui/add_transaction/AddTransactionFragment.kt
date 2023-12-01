package com.bangkit.catatmak.ui.add_transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.catatmak.databinding.FragmentAddTransactionBinding

class AddTransactionFragment : Fragment() {

    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding

    var isSheetShown = false

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
            if (!isSheetShown) {
                val modalBottomSheet = AddTransactionSheetFragment()
                modalBottomSheet.show(childFragmentManager, AddTransactionSheetFragment.TAG)
                isSheetShown = true
            }
        }

    }
}

