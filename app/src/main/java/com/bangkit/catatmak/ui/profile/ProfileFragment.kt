package com.bangkit.catatmak.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.catatmak.databinding.FragmentProfileBinding
import com.bangkit.catatmak.ui.about.AboutActivity
import com.bangkit.catatmak.ui.privacy_policy.PrivacyPolicyActivity
import com.bangkit.catatmak.ui.update_profile.UpdateProfileActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}