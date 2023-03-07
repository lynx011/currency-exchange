package com.example.data_binding.repository

import com.example.data_binding.api_service.NewsApi

class NewsRepository(private val newsApi: NewsApi) {

    suspend fun getArticle() = newsApi.getArticle(q = "nft", page = 3, apiKey = "c4809605dd6a44a2a8493f96a198acd7")
}