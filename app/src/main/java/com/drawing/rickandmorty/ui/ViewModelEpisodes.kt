package com.drawing.rickandmorty.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drawing.rickandmorty.models.episodes.EpisodesScreenState
import com.drawing.rickandmorty.models.episodes.Season
import com.drawing.rickandmorty.repository.Repository
import com.drawing.rickandmorty.util.Constants.Companion.API_KEY
import com.drawing.rickandmorty.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ViewModelEpisodes(val repository: Repository) : ViewModel() {

    private val _episodesLiveData =
        MutableLiveData<EpisodesScreenState>().also { it.value = EpisodesScreenState() }
    val episodesLiveData : LiveData<EpisodesScreenState> = _episodesLiveData

    var episodesResponse : Season? = null

    var seasonNumber = 2

    init {
        getSeason()
    }

    fun getSeason() = viewModelScope.launch {
        _episodesLiveData.postValue(_episodesLiveData.value?.copy(data = Resource.Loading()))
        val response = repository.getSeason(seasonNumber, API_KEY)
        _episodesLiveData.postValue(
            _episodesLiveData.value?.copy(
                response = handleEpisodesResponse(response)
            )
        )
    }


    private fun handleEpisodesResponse(response: Response<Season>): Resource<Season> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                //charactersPage++
                if (episodesResponse == null) {
                    episodesResponse = resultResponse
                } else {
//                    val oldEpisodes = episodesResponse?.episodes
//                    val newCharacters = resultResponse.episodes
//                    oldCharacters?.addAll(newCharacters)
                }
                return Resource.Success(episodesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}