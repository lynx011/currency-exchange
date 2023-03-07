package com.example.data_binding.model

data class NewsModel(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)