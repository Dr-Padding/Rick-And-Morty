package com.drawing.rickandmorty.repository

import com.drawing.rickandmorty.api.RetrofitInstance
import com.drawing.rickandmorty.api.RetrofitInstanceTMDB

class Repository {
    suspend fun getCharacters(pageNumber: Int) =
        RetrofitInstance.api.getCharacters(pageNumber)

    suspend fun getSeason(seasonNumber: Int, apiKey: String) =
        RetrofitInstanceTMDB.api.getSeason(seasonNumber, apiKey)

    suspend fun getSeasonAndEpisode(seasonNumber: Int, episodeNumber: Int, apiKey: String) =
        RetrofitInstanceTMDB.api.getSeasonAndEpisode(seasonNumber, episodeNumber, apiKey)

    suspend fun getEpisodesFromRickAndMortyAPI(listOfId : MutableList<Int>) =
        RetrofitInstance.apiEpisodes.getEpisodesFromRickAndMortyAPI(listOfId)
}