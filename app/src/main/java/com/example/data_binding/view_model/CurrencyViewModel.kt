package com.example.data_binding.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_binding.model.Quotes
import com.example.data_binding.repository.CurrencyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyViewModel(private val repository: CurrencyRepository) : ViewModel() {

    val currenciesLiveData = MutableLiveData<List<Quotes>>()

    fun getCurrencies() = CoroutineScope(Dispatchers.IO).launch {
        val response = repository.getCurrencies().body()
        withContext(Dispatchers.Default){
            if(response != null){
                currenciesLiveData.postValue(response)
            }
        }
    }

    val currenciesData = viewModelScope.launch {
        repository.getCurrency()
    }

    fun insertCurrencies(currencies: List<Quotes>) = viewModelScope.launch {
        repository.insertCurrencies(currencies)
    }

    fun deleteArticle() = viewModelScope.launch {
        repository.deleteCurrency()
    }
}