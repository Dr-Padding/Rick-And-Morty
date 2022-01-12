package com.drawing.rickandmorty.util

import androidx.appcompat.app.AppCompatDelegate

class Constants {
    companion object{
        const val LIGHT_THEME = AppCompatDelegate.MODE_NIGHT_NO
        const val DARK_THEME = AppCompatDelegate.MODE_NIGHT_YES
        const val FOLLOW_SYSTEM_THEME = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        const val BASE_URL = "https://rickandmortyapi.com"
        const val QUERY_PAGE_SIZE = 20
        const val API_KEY = "0190a4b51699a244b640d31adee94266"
        const val TMDB_BASE_URL = "https://api.themoviedb.org/"
    }
}