package com.drawing.rickandmorty.models.episodes


import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.util.Resource

data class EpisodesScreenState(
    val data: Resource<Season> = Resource.Loading(),
    val response: Resource<Season> = Resource.Loading(),
    val seasonNumber: Int = 1,
    val searchQuery: String? = null
)
