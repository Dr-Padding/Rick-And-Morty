package com.drawing.rickandmorty.util

import androidx.appcompat.app.AppCompatDelegate

class Constants {
    companion object{
        const val LIGHT_THEME = AppCompatDelegate.MODE_NIGHT_NO
        const val DARK_THEME = AppCompatDelegate.MODE_NIGHT_YES
        const val FOLLOW_SYSTEM_THEME = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        const val BASE_URL = "https://rickandmortyapi.com"
        const val VIEW_TYPE_SMALL = 1
        const val VIEW_TYPE_BIG = 2
    }
}