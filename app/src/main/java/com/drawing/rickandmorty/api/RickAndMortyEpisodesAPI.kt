package com.drawing.rickandmorty.api

import com.drawing.rickandmorty.models.episodes_from_rick_and_morty_api.AllEpisodesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyEpisodesAPI {

    @GET("api/episode")
    suspend fun getEpisodesFromRickAndMortyAPI(
        @Query("id") listOfId : MutableList<Int>
    ): Response<AllEpisodesResponse>

}