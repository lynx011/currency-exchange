package com.example.data_binding.repository

import com.example.data_binding.api_service.CurrencyApi
import com.example.data_binding.model.Quotes
import com.example.data_binding.room.CurrencyDatabase

class CurrencyRepository(private val currencyApi: CurrencyApi,private val db: CurrencyDatabase) {

    suspend fun getCurrencies() = currencyApi.getCurrency(base = "USD")

    suspend fun insertCurrencies(currencies: List<Quotes>) = db.currencyDao().insertCurrencies(currencies)

    fun getCurrency() = db.currencyDao().getCurrencies()

    fun deleteCurrency() = db.currencyDao().deleteCurrencies()
}