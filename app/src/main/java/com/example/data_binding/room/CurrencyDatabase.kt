package com.example.data_binding.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data_binding.model.Quotes

@Database(entities = [Quotes::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase(){

    abstract fun currencyDao() : CurrencyDao

    companion object {
        @Volatile
        private var db_instance: CurrencyDatabase? = null

        @Synchronized
        fun getInstance(context: Context): CurrencyDatabase {
            if (db_instance == null){
                db_instance = Room.databaseBuilder(
                    context.applicationContext,
                    CurrencyDatabase::class.java,
                    "currencies.db"
                ).build()

            }
            return db_instance as CurrencyDatabase
        }
    }
}