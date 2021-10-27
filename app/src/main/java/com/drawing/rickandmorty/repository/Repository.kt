package com.drawing.rickandmorty.repository

import com.drawing.rickandmorty.api.RetrofitInstance

class Repository {
    suspend fun getCharacters(pageNumber: Int) =
        RetrofitInstance.api.getCharacters(pageNumber)
}