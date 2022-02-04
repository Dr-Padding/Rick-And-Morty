package com.drawing.rickandmorty.models.episodes


import com.drawing.rickandmorty.util.Resource

data class EpisodesScreenState(
    val data: Resource<Season> = Resource.Loading(),
    val response: Resource<Season> = Resource.Loading(),
    val listOfEpisodes: MutableList<Episode>? = mutableListOf(),
    val searchQuery: String? = null
)
