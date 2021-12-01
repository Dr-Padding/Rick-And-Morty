package com.drawing.rickandmorty.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.models.AllCharactersResponse
import com.drawing.rickandmorty.models.PersonagesScreenState
import com.drawing.rickandmorty.repository.Repository
import com.drawing.rickandmorty.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ViewModelPersonages(val repository: Repository) : ViewModel() {

    private val _charactersLiveData = MutableLiveData<PersonagesScreenState>().also { it.value = PersonagesScreenState() }
    val charactersLiveData : LiveData<PersonagesScreenState> = _charactersLiveData

    val charactersPage = 1

    init {
        getCharacters()
    }

    fun getCharacters() = viewModelScope.launch {
        _charactersLiveData.postValue(_charactersLiveData.value?.copy(data = Resource.Loading()))
        val response = repository.getCharacters(charactersPage)
        _charactersLiveData.postValue(_charactersLiveData.value?.copy(response = handleCharactersResponse(response)))
    }

    private fun handleCharactersResponse(response: Response<AllCharactersResponse>) : Resource<AllCharactersResponse> {
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())

    }

    fun switchRecyclerViewType(recyclerViewType: Int) = viewModelScope.launch {
        if (recyclerViewType == 1){
            _charactersLiveData.postValue(_charactersLiveData.value?.copy(
                recyclerViewType = recyclerViewType,
                burgerMenuImage = R.drawable.ic_list_view,
                toggle = false
            ))
        }else{
            _charactersLiveData.postValue(_charactersLiveData.value?.copy(
                recyclerViewType = recyclerViewType,
                burgerMenuImage = R.drawable.ic_grid_view,
                toggle = true
            ))
        }
    }

}