package com.bangkit.catatmak.ui.splash_screen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bangkit.catatmak.ui.onboarding.OnBoardingActivity
import com.bangkit.catatmak.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        goToOnBoarding()
    }

    @Suppress("DEPRECATION")
    private fun goToOnBoarding() {
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, OnBoardingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }, 3000)
    }
}