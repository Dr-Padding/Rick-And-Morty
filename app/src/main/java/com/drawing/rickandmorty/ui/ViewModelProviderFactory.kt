package com.drawing.rickandmorty.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drawing.rickandmorty.repository.Repository

class ViewModelProviderFactory(val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return com.drawing.rickandmorty.ui.ViewModel(repository) as T
    }
}