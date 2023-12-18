package com.bangkit.catatmak.ui.authentication

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
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
                if (phone.isEmpty()) getString(R.string.empty_input) else null

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

        binding.tvSendWhatsapp.setOnClickListener {
            openWhatsApp(this, "08112584422", "Halo")
        }
    }

    private fun openWhatsApp(context: Context, phone: String, message: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.whatsapp")
        intent.data = Uri.parse("https://wa.me/$phone?text=$message")

        context.startActivity(intent)
    }

    private fun setMyButtonEnable() {
        val result = binding.edtWhatsappNumber.text
        binding.btnNext.isEnabled = result != null && result.toString().isNotEmpty()
    }
}