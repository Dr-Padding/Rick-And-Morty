package com.drawing.rickandmorty.models

import com.drawing.rickandmorty.util.Resource

data class PersonagesScreenState(
    val data: Resource<AllCharactersResponse> = Resource.Loading(),
    val recyclerViewType: Int = 1,
    val totalCharacterCount: Int = 200,
    val searchQuery: String? = null,
    val response: Resource<AllCharactersResponse> = Resource.Loading()
)
