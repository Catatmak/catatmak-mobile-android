package com.bangkit.catatmak.ui.profile.update_profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.CustomArrayAdapter
import com.bangkit.catatmak.data.ResultState
import com.bangkit.catatmak.data.response.ProfileDataItem
import com.bangkit.catatmak.databinding.ActivityUpdateProfileBinding
import com.bangkit.catatmak.ui.ViewModelFactory
import com.bangkit.catatmak.ui.main.MainViewModel

class UpdateProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateProfileBinding

    private val viewModel by viewModels<UpdateProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.topAppBar.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }


        getProfileData()
        setUpAction()
    }

    private fun setUpAction() {
        binding.btnUpdate.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val age = binding.edtAge.text.toString().trim()
            val gender = binding.actvGender?.text.toString().trim()

            val profile = ProfileDataItem(
                name = name,
                email = email,
                age = age,
                gender = gender
            )

            viewModel.updateProfile(profile).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }

                        is ResultState.Success -> {
                            showLoading(false)
                            val data = result.data.message
                            showToast(data)
                            finish()
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

    private fun getProfileData() {
        viewModel.getProfile().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                    }

                    is ResultState.Success -> {
                        val data = result.data.data
                        with(binding) {
                            with(data) {
                                binding.edtNoWa.setText(phone)
                                binding.edtName.setText(name)
                                binding.edtEmail.setText(email)
                                binding.actvGender?.setText(gender)
                                binding.edtAge.setText(age)

                                viewModel.genderItems.observe(this@UpdateProfileActivity) { gender ->
                                    gender?.let {
                                        val adapter = CustomArrayAdapter(
                                            this@UpdateProfileActivity,
                                            R.layout.dropdown_item_layout,
                                            gender.toTypedArray()
                                        )
                                        binding.actvGender?.setAdapter(adapter)

                                        binding.actvGender?.setOnItemClickListener { parent, view, position, id ->
                                            adapter.setSelectedPosition(position)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    is ResultState.Error -> {
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbUpdateProfile?.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(
            this, message, Toast.LENGTH_SHORT
        ).show()
    }
}

