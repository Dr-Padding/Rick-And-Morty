package com.drawing.rickandmorty.api

import com.drawing.rickandmorty.models.AllCharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersAPI {

    @GET("api/character")
    suspend fun getCharacters(
        @Query("page") pageNumber: Int = 1
    ): Response<AllCharactersResponse>

    @GET("api/character")
    suspend fun searchCharacters(
        @Query("name") characterName: String,
        @Query("page") pageNumber: Int = 1,
    ): Response<AllCharactersResponse>

}