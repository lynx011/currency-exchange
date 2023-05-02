package com.example.data_binding.di

import android.content.Context
import androidx.room.Room
import com.example.data_binding.room.CurrencyDao
import com.example.data_binding.room.CurrencyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideCurrencyDatabase(@ApplicationContext context: Context) : CurrencyDatabase = Room.databaseBuilder(
        context.applicationContext,CurrencyDatabase::class.java,"currencies.DB"
    ).fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Singleton
    @Provides
    fun provideCurrencyDao(db: CurrencyDatabase) : CurrencyDao = db.currencyDao()
}