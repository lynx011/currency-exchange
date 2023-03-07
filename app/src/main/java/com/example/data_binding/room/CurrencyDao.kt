package com.example.data_binding.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data_binding.model.Quotes

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies: List<Quotes>)

    @Query("SELECT * FROM currencies")
    fun getCurrencies() : LiveData<List<Quotes>>

    @Query("DELETE FROM currencies")
    fun deleteCurrencies()
}