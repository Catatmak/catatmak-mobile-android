package com.bangkit.catatmak.ui.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bangkit.catatmak.R
import com.bangkit.catatmak.databinding.ActivityVerifSuccessBinding
import com.bangkit.catatmak.ui.main.MainActivity

class VerifSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this)[VerifSuccessViewModel::class.java]

        viewModel.setInitialTime(4)

        viewModel.startTimer()

        viewModel.currentTimeString.observe(this) { currentTimeString ->
            binding.tvVerifSuccessTitle.text = getString(R.string.verif_success_title, currentTimeString)
        }

        viewModel.eventCountDownFinish.observe(this) { isCountDownFinished ->
            if (isCountDownFinished) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }

        binding.btnMove.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}