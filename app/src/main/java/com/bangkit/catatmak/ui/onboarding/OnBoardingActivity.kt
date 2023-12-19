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
        binding.btnTryNow.setOnClickListener { goToMainActivity() }

        var isMenu2Displayed = false

        binding.btnNext?.setOnClickListener {
            isMenu2Displayed = if (!isMenu2Displayed) {
                binding.motionLayoutId.setTransition(R.id.end, R.id.menu2)
                binding.motionLayoutId.transitionToEnd()
                true
            } else {
                binding.motionLayoutId.setTransition(R.id.menu2, R.id.menu3)
                binding.motionLayoutId.transitionToEnd()
                false
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