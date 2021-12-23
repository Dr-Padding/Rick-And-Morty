package com.drawing.rickandmorty.models


import androidx.recyclerview.widget.LinearLayoutManager
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.util.Resource

data class PersonagesScreenState(
    val data: Resource<AllCharactersResponse> = Resource.Loading(),
    val recyclerViewType: Int = 1,
    val toggle: Boolean = false,
    val totalCharacterCount: Int = 826,
    val searchQuery: String? = null,
    val response: Resource<AllCharactersResponse> = Resource.Loading(),
    val burgerMenuImage: Int = R.drawable.ic_list_view
)
