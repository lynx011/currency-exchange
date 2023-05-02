package com.example.data_binding.view_model
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data_binding.model.CurrencyCode
import com.example.data_binding.model.CurrencyDetail
import com.example.data_binding.repository.PagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PagingViewModel @Inject constructor(private val repository: PagingRepository) : ViewModel() {

    fun currenciesData(date: String): LiveData<PagingData<CurrencyDetail>> = repository.getData(date).cachedIn(viewModelScope)
//    val codeData: LiveData<PagingData<CurrencyCode>> = repository.getCodes().cachedIn(viewModelScope)
}