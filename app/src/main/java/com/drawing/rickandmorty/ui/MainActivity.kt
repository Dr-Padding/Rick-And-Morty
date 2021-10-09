package com.drawing.rickandmorty.ui

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.databinding.ActivityMainBinding
import com.drawing.rickandmorty.ui.fragments.SettingsFragment
import com.drawing.rickandmorty.util.Constants
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        val defaultNightMode = getSharedPreferences("sharedPref", MODE_PRIVATE)
            .getInt("themeMode", Constants.FOLLOW_SYSTEM_THEME)
        AppCompatDelegate.setDefaultNightMode(defaultNightMode)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }

}