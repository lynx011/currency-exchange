package com.example.data_binding.view_model_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data_binding.repository.CurrencyRepository
import com.example.data_binding.view_model.CurrencyViewModel

class CurrencyViewModelFactory(private val currencyRepository: CurrencyRepository) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrencyViewModel(currencyRepository) as T
    }
}