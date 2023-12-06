package com.bangkit.catatmak.ui.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.catatmak.R
import com.bangkit.catatmak.databinding.ActivityLoginBinding
import com.bangkit.catatmak.databinding.ActivityVerificationBinding

class VerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, VerifSuccessActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}