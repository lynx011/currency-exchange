package com.example.data_binding.model

data class CurrencyModel(
    val date: String,
    val historical: Boolean,
    val quotes: Map<String,Double>,
    val source: String,
    val success: Boolean,
    val timestamp: Int
)