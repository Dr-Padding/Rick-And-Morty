package com.drawing.rickandmorty.api

import com.drawing.rickandmorty.models.episodes_from_rick_and_morty_api.AllEpisodesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyEpisodesAPI {

    @GET("api/episode/{id}")
    suspend fun getEpisodesFromRickAndMortyAPI(
        @Path("id") listOfId : MutableList<Int>
    ): Response<AllEpisodesResponse>

}