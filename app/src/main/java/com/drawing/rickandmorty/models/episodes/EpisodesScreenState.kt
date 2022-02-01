package com.drawing.rickandmorty.models.episodes



import com.drawing.rickandmorty.models.episodes_from_rick_and_morty_api.AllEpisodesResponse
import com.drawing.rickandmorty.util.Resource

data class EpisodesScreenState(
    val data: Resource<Season> = Resource.Loading(),
    val response: Resource<Season> = Resource.Loading(),
//    val dataFromRickAndMortyAPI: Resource<AllEpisodesResponse> = Resource.Loading(),
//    val responseFromRickAndMortyAPI: Resource<AllEpisodesResponse> = Resource.Loading(),
    val listOfEpisodes: MutableList<Episode>? = mutableListOf(),
    val seasonNumber: Int = 1,
    val episodeNumber: Int = 1,
    val searchQuery: String? = null
)
