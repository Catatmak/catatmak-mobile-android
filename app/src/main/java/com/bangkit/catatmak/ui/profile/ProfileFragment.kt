package com.bangkit.catatmak.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.bangkit.catatmak.R
import com.bangkit.catatmak.data.ResultState
import com.bangkit.catatmak.databinding.FragmentProfileBinding
import com.bangkit.catatmak.ui.ViewModelFactory
import com.bangkit.catatmak.ui.authentication.LoginActivity
import com.bangkit.catatmak.ui.profile.about.AboutActivity
import com.bangkit.catatmak.ui.profile.privacy_policy.PrivacyPolicyActivity
import com.bangkit.catatmak.ui.profile.update_profile.UpdateProfileActivity
import com.bumptech.glide.Glide


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
        getProfile()
    }

    override fun onResume() {
        super.onResume()
        getProfile()
    }

    private fun getProfile() {
        viewModel.getProfile().observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        showLoading(false)
                        val data = result.data.data
                        if (data.name.isNotEmpty()) {
                            binding?.completed?.visibility = View.VISIBLE
                            binding?.uncompleted?.visibility = View.GONE
                            binding?.tvUsername?.text = data.name
                            binding?.tvMemberStatus?.text = data.membership.status
                            binding?.ivPremiumMedal?.let {
                                Glide.with(this)
                                    .load(data.membership.imageUrl)
                                    .into(it)
                            }
                        } else {
                            binding?.completed?.visibility = View.GONE
                            binding?.uncompleted?.visibility = View.VISIBLE
                        }
                    }
                    is ResultState.Error -> {
                        showLoading(false)
                        showToast(result.error.toString())
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.pbProfile?.visibility =
            if (isLoading) View.VISIBLE else View.GONE

    }

    private fun showToast(message: String) {
        Toast.makeText(
            requireActivity(), message, Toast.LENGTH_SHORT
        ).show()
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
        binding?.btnCompleteProfile?.setOnClickListener {
            startActivity(Intent(requireActivity(), UpdateProfileActivity::class.java))
        }
    }

    private fun logout() {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle(getString(R.string.warning_title))
            setMessage(getString(R.string.logout_warning))
            setPositiveButton(getString(R.string.dialog_positive_action)) { _, _ ->
                viewModel.logout()
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
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