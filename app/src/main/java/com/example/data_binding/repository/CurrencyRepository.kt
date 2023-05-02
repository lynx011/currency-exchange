package com.example.data_binding.repository

import com.example.data_binding.api_service.CurrencyApi
import com.example.data_binding.model.CurrencyCode
import com.example.data_binding.model.CurrencyDetail
import com.example.data_binding.room.CurrencyDatabase
import javax.inject.Inject

class CurrencyRepository @Inject constructor(private val currencyApi: CurrencyApi,private val db: CurrencyDatabase) {

    suspend fun getCurrencies(date: String,source: String) = currencyApi.getCurrency(date = date, source = source)

    suspend fun getCountries() = currencyApi.getCurrencyCode()

    suspend fun getConvertAmount(from: String,to: String,amount: Int,date: String) = currencyApi.getConvertAmount(from,to,amount,date)

    suspend fun insertCurrencies(currencies: List<CurrencyDetail>) = db.currencyDao().insertCurrencies(currencies)

    suspend fun searchQuery(query: String) = db.currencyDao().matchQuery(query)

    fun getRoomCurrencies(date: String) = db.currencyDao().getCurrencies(date)

    fun deleteCurrency() = db.currencyDao().deleteCurrencies()

    suspend fun insertCodes(codes: List<CurrencyCode>) = db.currencyDao().insertCodes(codes)

    fun getRoomCodes() = db.currencyDao().getCodes()

    fun deleteCodes() = db.currencyDao().deleteCodes()

}