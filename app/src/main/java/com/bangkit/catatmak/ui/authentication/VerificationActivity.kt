package com.bangkit.catatmak.ui.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bangkit.catatmak.R
import com.bangkit.catatmak.data.ResultState
import com.bangkit.catatmak.data.api.CatatmakRepository
import com.bangkit.catatmak.data.pref.UserModel
import com.bangkit.catatmak.databinding.ActivityVerificationBinding
import com.bangkit.catatmak.ui.ViewModelFactory

class VerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationBinding
    private lateinit var phone: String

    private val viewModel by viewModels<VerificationViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        phone = intent.getStringExtra(PHONE_EXTRA).toString()

        binding.tvVerifSubtitle.text = getString(R.string.verif_subtitle, phone)

        setUpAction()
        sendOtp()
    }

    override fun onResume() {
        super.onResume()
        showLoading(false)
        binding.edtOTP.setText("")
    }

    private fun sendOtp() {
        viewModel.sendOTP(phone).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                    }

                    is ResultState.Success -> {
                    }

                    is ResultState.Error -> {
                        showToast(result.error.toString())
                    }
                }
            }
        }
    }

    private fun setUpAction() {
        binding.btnVerify.setOnClickListener {
            verify()
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.tvSendAgain.setOnClickListener {
            sendOtp()
        }
    }

    private fun verify() {
        val otp = binding.edtOTP.text.toString().trim()

        val isInputEmpty = otp.isEmpty()

        binding.tfOTP.error =
            if (otp.isEmpty()) getString(R.string.empty_phone_input) else null

        if (!isInputEmpty) {
            viewModel.verifyOTP(phone, otp).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }

                        is ResultState.Success -> {
                            val token = result.data.token
                            if (token.isNotEmpty()) {
                                viewModel.saveSession(UserModel(token))
                            }
                            CatatmakRepository.resetInstance()
                            ViewModelFactory.resetInstance()
                            val intent = Intent(this, VerifSuccessActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }

                        is ResultState.Error -> {
                            val intent = Intent(this, VerifFailActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val PHONE_EXTRA = "phone_extra"
    }
}