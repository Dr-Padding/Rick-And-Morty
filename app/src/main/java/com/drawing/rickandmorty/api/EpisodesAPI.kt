package com.drawing.rickandmorty.api


import com.drawing.rickandmorty.models.episodes.Season
import com.drawing.rickandmorty.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EpisodesAPI {

    @GET("3/tv/60625/season/1")
    suspend fun getSeason(
        @Query("season_number") seasonNumber: Int = 1,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Season>

    @GET("3/tv/60625/season")
    suspend fun searchEpisode(
        @Query("name") episodeName: String,
        //@Query("page") pageNumber: Int = 1,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Season>

}