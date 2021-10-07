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
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Light)
        val defaultNightMode = getSharedPreferences("sharedPref", MODE_PRIVATE).getInt("Theme", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        AppCompatDelegate.setDefaultNightMode(defaultNightMode)
        when(defaultNightMode){
            AppCompatDelegate.MODE_NIGHT_NO -> setTheme(R.style.Theme_Light)
            AppCompatDelegate.MODE_NIGHT_YES -> setTheme(R.style.Theme_Dark)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }







        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }

}