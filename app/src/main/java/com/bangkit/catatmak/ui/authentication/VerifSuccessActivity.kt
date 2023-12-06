package com.bangkit.catatmak.ui.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.catatmak.R
import com.bangkit.catatmak.databinding.ActivityVerifSuccessBinding
import com.bangkit.catatmak.databinding.ActivityVerificationBinding
import com.bangkit.catatmak.ui.main.MainActivity

class VerifSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMove.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}