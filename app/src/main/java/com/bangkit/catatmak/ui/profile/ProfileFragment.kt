package com.bangkit.catatmak.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.bangkit.catatmak.R
import com.bangkit.catatmak.databinding.FragmentProfileBinding
import com.bangkit.catatmak.ui.ViewModelFactory
import com.bangkit.catatmak.ui.profile.about.AboutActivity
import com.bangkit.catatmak.ui.profile.privacy_policy.PrivacyPolicyActivity
import com.bangkit.catatmak.ui.profile.update_profile.UpdateProfileActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAction()
    }

    private fun setUpAction() {
        binding?.btnLogout?.setOnClickListener {
            logout()
        }
        binding?.rowChangeProfile?.setOnClickListener {
            startActivity(Intent(requireActivity(), UpdateProfileActivity::class.java))
        }
        binding?.rowPolicy?.setOnClickListener {
            startActivity(Intent(requireActivity(), PrivacyPolicyActivity::class.java))
        }
        binding?.rowAbout?.setOnClickListener {
            startActivity(Intent(requireActivity(), AboutActivity::class.java))
        }
    }

    private fun logout() {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle(getString(R.string.warning_title))
            setMessage(getString(R.string.logout_warning))
            setPositiveButton(getString(R.string.dialog_positive_action)) { _, _ ->
                viewModel.logout()
            }
            setNegativeButton(getString(R.string.dialog_negative_action)) { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}