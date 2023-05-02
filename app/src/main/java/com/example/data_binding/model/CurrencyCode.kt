package com.example.data_binding.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "codes")
data class CurrencyCode(
    @PrimaryKey(autoGenerate = true)
    val countryId: Int = 0,
    val codes: String,
    val countries: String
)