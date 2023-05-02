package com.example.data_binding.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data_binding.model.CurrencyCode
import com.example.data_binding.model.CurrencyDetail

@Database(entities = [CurrencyDetail::class,CurrencyCode::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase(){

    abstract fun currencyDao() : CurrencyDao
}