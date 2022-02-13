package com.drawing.rickandmorty.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drawing.rickandmorty.repository.Repository
import java.lang.RuntimeException

class ViewModelProviderFactory(val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            ViewModelPersonages::class.java -> ViewModelPersonages(repository) as T
            ViewModelEpisodes::class.java -> ViewModelEpisodes(repository) as T
            else -> throw RuntimeException()
        }
    }
}