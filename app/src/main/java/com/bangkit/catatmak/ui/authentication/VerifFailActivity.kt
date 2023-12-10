package com.bangkit.catatmak.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.catatmak.databinding.ActivityVerifFailBinding

class VerifFailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifFailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifFailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVerifAgain.setOnClickListener {
            onBackPressed()
        }
    }
}