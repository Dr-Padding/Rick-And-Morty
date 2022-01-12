package com.drawing.rickandmorty.repository

import com.drawing.rickandmorty.api.RetrofitInstance
import com.drawing.rickandmorty.api.RetrofitInstanceTMDB

class Repository {
    suspend fun getCharacters(pageNumber: Int) =
        RetrofitInstance.api.getCharacters(pageNumber)

    suspend fun getSeason(seasonNumber: Int, apiKey: String) =
        RetrofitInstanceTMDB.api.getSeason(seasonNumber, apiKey)
}