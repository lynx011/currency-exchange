package com.example.data_binding.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_binding.model.Article
import com.example.data_binding.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    val articleList = MutableLiveData<List<Article>?>()

    init {
        getArticle()
    }

    private fun getArticle() = CoroutineScope(Dispatchers.IO).launch {
        val response = repository.getArticle().body()?.articles
           withContext(Dispatchers.Main){
               if (response != null) {
                       articleList.value = response
               }
           }
    }
}