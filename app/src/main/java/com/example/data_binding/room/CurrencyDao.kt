package com.example.data_binding.room

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data_binding.model.CurrencyCode
import com.example.data_binding.model.CurrencyDetail

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies: List<CurrencyDetail>)

    @Query("SELECT * FROM currencies WHERE rateOnDate = :date GROUP BY countryCode")
    fun getCurrencies(date: String) : PagingSource<Int,CurrencyDetail>

    @Query("SELECT * FROM currencies WHERE countryCode LIKE '%' || :query")
    fun matchQuery(query: String) : LiveData<CurrencyDetail>

    @Query("DELETE FROM currencies")
    fun deleteCurrencies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCodes(codes: List<CurrencyCode>)

    @Query("SELECT * FROM codes GROUP BY codes")
    fun getCodes() : LiveData<List<CurrencyCode>>

    @Query("DELETE FROM codes")
    fun deleteCodes()

}