package com.drawing.rickandmorty.repository



import android.util.Log
import com.drawing.rickandmorty.api.RetrofitInstance
import com.drawing.rickandmorty.api.RetrofitInstanceTMDB
import com.drawing.rickandmorty.models.episodes.Episode
import com.drawing.rickandmorty.util.Constants.Companion.API_KEY


class Repository {

    var cachedTMDBEpisodes: MutableList<Episode>? = null
    var episodesInWhichCharacterAppeared : MutableList<Episode>? = null

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



    suspend fun getEpisodesInWhichCharacterAppearedFromRickAndMortyAPI(listOfId : MutableList<Int>) : MutableList<Episode>? {
        getAllEpisodesFromTMDB()
        var episodes = RetrofitInstance.apiEpisodes.getEpisodesFromRickAndMortyAPI(listOfId)


        Log.d("episodes", episodes.toString())


        if(episodes.isSuccessful){
            episodes.body()?.results.let { listOfEpisodes ->

                Log.d("list", listOfEpisodes.toString())

                if (listOfEpisodes != null) {
                        for (i in listOfEpisodes){
                            //The code of the each episode
                            var episodeCode = i.episode

                            //Log.d("code", episodeCode)

                            episodeCode = episodeCode.filter {it.isDigit()}
                            val seasonNumber = episodeCode.subSequence(0, 2) as String
                            val episodeNumber = episodeCode.subSequence(2, 4) as String
                            val s = seasonNumber.toInt()
                            val e = episodeNumber.toInt()

                            if(episodesInWhichCharacterAppeared == null) {
                                episodesInWhichCharacterAppeared = mutableListOf()
                            }

                            for (i in cachedTMDBEpisodes!!){
                                if (i.season_number == s && i.episode_number == e){
                                    episodesInWhichCharacterAppeared!!.add(i)
                                }
                            }
                        }
                }
            }
        }
        return episodesInWhichCharacterAppeared
    }
}