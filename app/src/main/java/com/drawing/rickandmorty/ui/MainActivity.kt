package com.drawing.rickandmorty.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.databinding.ActivityMainBinding
import com.drawing.rickandmorty.repository.Repository
import com.drawing.rickandmorty.util.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //lateinit var viewModelPersonages: ViewModelPersonages
    //lateinit var viewModelEpisodes: ViewModelEpisodes

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        val defaultNightMode = getSharedPreferences("sharedPref", MODE_PRIVATE)
            .getInt("themeMode", Constants.LIGHT_THEME)
        AppCompatDelegate.setDefaultNightMode(defaultNightMode)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }

}