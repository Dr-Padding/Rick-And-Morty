package com.drawing.rickandmorty.repository



import android.util.Log
import com.drawing.rickandmorty.api.RetrofitInstance
import com.drawing.rickandmorty.api.RetrofitInstanceTMDB
import com.drawing.rickandmorty.models.episodes.Episode
import com.drawing.rickandmorty.util.Constants.Companion.API_KEY
import com.drawing.rickandmorty.util.Resource
import kotlin.properties.Delegates


class Repository {

    var cachedTMDBEpisodes: MutableList<Episode>? = null

    suspend fun getAllEpisodesFromTMDB(){
        if(cachedTMDBEpisodes == null) {
            cachedTMDBEpisodes = mutableListOf()
        }

        var seasonNumber = 1
        while (seasonNumber <= 5) {
            val response = RetrofitInstanceTMDB.api.getSeason(seasonNumber, API_KEY)
            if (response.isSuccessful) {
                var episodes = response.body()?.episodes
                if (episodes != null) {
                    cachedTMDBEpisodes?.addAll(episodes)
                }
            }
            seasonNumber++
        }
    }

    suspend fun getCharacters(pageNumber: Int) =
        RetrofitInstance.api.getCharacters(pageNumber)

    suspend fun getSeason(seasonNumber: Int, apiKey: String) =
        RetrofitInstanceTMDB.api.getSeason(seasonNumber, apiKey)



    suspend fun getEpisodesInWhichCharacterAppearedFromRickAndMortyAPI(
        listOfId : MutableList<Int>
    ) : Resource<MutableList<Episode>> {
        getAllEpisodesFromTMDB()

        var episodesInWhichCharacterAppeared : MutableList<Episode>? = null

        if(episodesInWhichCharacterAppeared == null) {
            episodesInWhichCharacterAppeared = mutableListOf()
        }

        var episodes = RetrofitInstance.apiEpisodes.getEpisodesFromRickAndMortyAPI(listOfId)

        if(episodes.isSuccessful){
            episodes.body()?.let { listOfEpisodes ->
                for (i in listOfEpisodes) {
                    //The code of the each episode
                    var episodeCode = i.episode
                    episodeCode = episodeCode.filter { it.isDigit() }
                    val seasonNumber = episodeCode.subSequence(0, 2) as String
                    val episodeNumber = episodeCode.subSequence(2, 4) as String
                    val s = seasonNumber.toInt()
                    val e = episodeNumber.toInt()

                    episodesInWhichCharacterAppeared.addAll(
                        cachedTMDBEpisodes!!.filter { episode -> episode.season_number == s && episode.episode_number == e }
                    )
                }
            }
            return Resource.Success(episodesInWhichCharacterAppeared)
        }
        return Resource.Error(episodes.message())
        //return episodesInWhichCharacterAppeared
    }
}