package com.example.data_binding.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class CurrencyDetail(
    @PrimaryKey(autoGenerate = true)
    val currencyId: Int = 0,
    val countryCode: String,
    val exchangeRate: Double,
    var rateOnDate : String
)
