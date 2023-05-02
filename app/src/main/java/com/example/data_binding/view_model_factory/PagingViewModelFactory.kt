package com.example.data_binding.view_model_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data_binding.repository.PagingRepository
import com.example.data_binding.view_model.PagingViewModel

class PagingViewModelFactory(private val repository: PagingRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PagingViewModel(repository) as T
    }
}