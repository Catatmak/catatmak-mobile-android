package com.bangkit.catatmak.ui.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.bangkit.catatmak.databinding.ActivityLoginBinding
import com.bangkit.catatmak.R

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAction()
        setMyButtonEnable()
    }

    private fun setUpAction() {
        binding.btnNext.setOnClickListener {
            val phone = "62" + binding.edtWhatsappNumber.text.toString().trim()

            val isInputEmpty = phone.isEmpty()

            binding.tfWhatsappNumber.error =
                if (phone.isEmpty()) getString(R.string.empty_phone_input) else null

            if (!isInputEmpty) {
                val intent = Intent(this, VerificationActivity::class.java)
                intent.putExtra(VerificationActivity.PHONE_EXTRA, phone)
                startActivity(intent)
            }
        }

        binding.edtWhatsappNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun setMyButtonEnable() {
        val result = binding.edtWhatsappNumber.text
        binding.btnNext.isEnabled = result != null && result.toString().isNotEmpty()
    }
}