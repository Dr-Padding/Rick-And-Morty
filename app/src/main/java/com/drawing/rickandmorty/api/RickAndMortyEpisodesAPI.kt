package com.drawing.rickandmorty.api

import com.drawing.rickandmorty.models.episodes_from_rick_and_morty_api.AllEpisodesResponse
import com.drawing.rickandmorty.models.episodes_from_rick_and_morty_api.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface RickAndMortyEpisodesAPI {

    @GET("api/episode/{id}")
    suspend fun getEpisodesFromRickAndMortyAPI(
        @Path("id") listOfId : MutableList<Int>
    ): Response<List<Result>>

}