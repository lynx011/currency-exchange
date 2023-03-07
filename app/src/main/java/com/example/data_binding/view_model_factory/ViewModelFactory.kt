package com.example.data_binding.view_model_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data_binding.repository.NewsRepository
import com.example.data_binding.view_model.NewsViewModel

class ViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(repository) as T
    }
}