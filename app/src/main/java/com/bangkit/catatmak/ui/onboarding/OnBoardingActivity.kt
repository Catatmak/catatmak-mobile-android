package com.bangkit.catatmak.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.catatmak.R
import com.bangkit.catatmak.databinding.ActivityOnBoardingBinding
import com.bangkit.catatmak.ui.authentication.LoginActivity

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.btnTryNow?.setOnClickListener { goToMainActivity() }

        var isMenu2Displayed = false

        binding.btnNext?.setOnClickListener {
            if (!isMenu2Displayed) {
                // Ganti scene dari end ke menu2
                binding.motionLayoutId?.setTransition(R.id.end, R.id.menu2)
                binding.motionLayoutId?.transitionToEnd()
                isMenu2Displayed = true
            } else {
                // Ganti scene dari menu2 ke menu3
                binding.motionLayoutId?.setTransition(R.id.menu2, R.id.menu3)
                binding.motionLayoutId?.transitionToEnd()
                // Reset variabel untuk transisi selanjutnya
                isMenu2Displayed = false
            }
        }

    }

    private fun goToMainActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}