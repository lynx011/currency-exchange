package com.example.data_binding

import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var text = MutableLiveData("Hello")

    fun updateText() = viewModelScope.launch {
        text.value = "updated data"
    }
}