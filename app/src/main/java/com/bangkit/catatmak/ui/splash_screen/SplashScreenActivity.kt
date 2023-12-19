package com.bangkit.catatmak.ui.splash_screen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import com.bangkit.catatmak.R
import com.bangkit.catatmak.ui.onboarding.OnBoardingActivity
import com.bangkit.catatmak.databinding.ActivitySplashScreenBinding
import com.bangkit.catatmak.ui.ViewModelFactory
import com.bangkit.catatmak.ui.main.MainActivity
import com.bumptech.glide.Glide

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    private val viewModel by viewModels<SplashScreenViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this)
            .asGif()
            .load(R.raw.mamih_cute)
            .into(binding.ivLogo)

        viewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                @Suppress("DEPRECATION") val handler = Handler()
                handler.postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, 5000)
            } else {
                @Suppress("DEPRECATION") val handler = Handler()
                handler.postDelayed({
                    goToOnboardingPage()
                }, 5000)
            }
        }
    }

    private fun goToOnboardingPage() {
        val intent = Intent(this, OnBoardingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}