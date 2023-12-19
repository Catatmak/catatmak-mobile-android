package com.bangkit.catatmak.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkit.catatmak.R
import com.bangkit.catatmak.data.ResultState
import com.bangkit.catatmak.databinding.ActivityMainBinding
import com.bangkit.catatmak.ui.ViewModelFactory
import com.bangkit.catatmak.ui.authentication.LoginActivity
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        setSupportActionBar(binding.topAppBar)

        val navView: BottomNavigationView = binding.navView

        navView.itemActiveIndicatorColor = resources.getColorStateList(R.color.dark_blue)
        navView.itemRippleColor = resources.getColorStateList(R.color.light_blue)

        navView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_transaction,
                R.id.navigation_add_transaction,
                R.id.navigation_analysis,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> {
                    getProfile()
                }

                R.id.navigation_add_transaction -> {
                    supportActionBar?.title = resources.getString(R.string.add_transaction)
                }

                R.id.navigation_transaction -> {
                    supportActionBar?.title = resources.getString(R.string.transaction)
                }

                R.id.navigation_analysis -> {
                    supportActionBar?.title = resources.getString(R.string.analysis)
                }
            }
        }


    }

    private fun getProfile() {
        viewModel.getProfile().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                    }

                    is ResultState.Success -> {
                        val data = result.data.data
                        if (data.name.isNotEmpty()) {
                            supportActionBar?.title = getString(R.string.welcome, data.name)
                        } else {
                            supportActionBar?.title = getString(R.string.welcome, "Sobat")
                        }
                    }
                    is ResultState.Error -> {
                        showToast(result.error.toString())
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(
            this, message, Toast.LENGTH_SHORT
        ).show()
    }
}