package com.drawing.rickandmorty.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drawing.rickandmorty.models.episodes.EpisodesScreenState
import com.drawing.rickandmorty.models.episodes.Season
import com.drawing.rickandmorty.models.episodes_from_rick_and_morty_api.AllEpisodesResponse
import com.drawing.rickandmorty.repository.Repository
import com.drawing.rickandmorty.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ViewModelEpisodes(val repository: Repository) : ViewModel() {

    private val _episodesLiveData =
        MutableLiveData<EpisodesScreenState>().also { it.value = EpisodesScreenState() }
    val episodesLiveData : LiveData<EpisodesScreenState> = _episodesLiveData

    var episodesResponse : Season? = null

    var episodesFromRickAndMortyAPI : AllEpisodesResponse? = null

    fun getSeason(seasonNumber: Int, apiKey : String) = viewModelScope.launch {
        _episodesLiveData.postValue(_episodesLiveData.value?.copy(data = Resource.Loading()))
        val response = repository.getSeason(seasonNumber, apiKey)
        _episodesLiveData.postValue(_episodesLiveData.value?.copy(response = handleEpisodesResponse(response)))
    }

    fun getSeasonAndEpisode(seasonNumber: Int, episodeNumber: Int, apiKey: String) = viewModelScope.launch {
        _episodesLiveData.postValue(_episodesLiveData.value?.copy(data = Resource.Loading()))
        val response = repository.getSeasonAndEpisode(seasonNumber, episodeNumber, apiKey)
        _episodesLiveData.postValue(_episodesLiveData.value?.copy(response = handleEpisodesResponse(response)))
    }

    fun getEpisodesFromRickAndMortyAPI(listOfId: MutableList<Int>) = viewModelScope.launch {
        _episodesLiveData.postValue(_episodesLiveData.value?.copy(
            dataFromRickAndMortyAPI = Resource.Loading())
        )
        val response = repository.getEpisodesFromRickAndMortyAPI(listOfId)
        _episodesLiveData.postValue(_episodesLiveData.value?.copy(
            responseFromRickAndMortyAPI = handleEpisodesResponseFromRickAndMortyAPI(response))
        )
    }


    private fun handleEpisodesResponse(response: Response<Season>): Resource<Season> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                episodesResponse = if (episodesResponse == null) {
                    resultResponse
                } else {
                    resultResponse
                }
                return Resource.Success(episodesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleEpisodesResponseFromRickAndMortyAPI(response: Response<AllEpisodesResponse>): Resource<AllEpisodesResponse> {
        if (response.isSuccessful) {
            response.body()?.let { allEpisodesResponse ->
//                episodesFromRickAndMortyAPI = if (episodesFromRickAndMortyAPI == null) {
//                    allEpisodesResponse
//                } else {
//                    allEpisodesResponse
//                }
                return Resource.Success( allEpisodesResponse)
            }
        }
        return Resource.Error(response.message())
    }

}