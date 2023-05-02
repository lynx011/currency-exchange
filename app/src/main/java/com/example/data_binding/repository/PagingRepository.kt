package com.example.data_binding.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.data_binding.model.CurrencyCode
import com.example.data_binding.model.CurrencyDetail
import com.example.data_binding.room.CurrencyDao
import com.example.data_binding.room.CurrencyDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PagingRepository @Inject constructor(private val db: CurrencyDatabase) {
    fun getData(date: String): LiveData<PagingData<CurrencyDetail>> {
        return Pager(
            config = PagingConfig(pageSize = 30)
        ){
            db.currencyDao().getCurrencies(date)
        }.liveData
    }

//    fun getCodes(): LiveData<PagingData<CurrencyCode>> {
//        return Pager(
//            config = PagingConfig(pageSize = 30)
//        ){
//            db.currencyDao().getCodes()
//        }.liveData
//    }
}