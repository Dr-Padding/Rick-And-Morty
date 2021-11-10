package com.drawing.rickandmorty.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drawing.rickandmorty.models.AllCharactersResponse
import com.drawing.rickandmorty.repository.Repository
import com.drawing.rickandmorty.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ViewModelPersonages(val repository: Repository) : ViewModel() {

    data class PersonagesScreenState(
        val data: Resource<AllCharactersResponse> = Resource.Loading(),
        val recyclerViewType: Int = 1
    )

    val charactersLiveData: MutableLiveData<Resource<AllCharactersResponse>> = MutableLiveData()

    val charactersPage = 1

    init {
        getCharacters()
    }

    fun getCharacters() = viewModelScope.launch {
        charactersLiveData.postValue(Resource.Loading())
        val response = repository.getCharacters(charactersPage)
        charactersLiveData.postValue(handleCharactersResponse(response))
    }


    private fun handleCharactersResponse(response: Response<AllCharactersResponse>) : Resource<AllCharactersResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())

    }

}