package com.bangkit.catatmak.ui.profile.about

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bangkit.catatmak.R
import com.bangkit.catatmak.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)


        val upArrow = ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.dark_blue), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        binding.topAppBar.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }
    }
}