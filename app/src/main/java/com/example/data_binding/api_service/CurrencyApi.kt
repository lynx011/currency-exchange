package com.example.data_binding.api_service
import com.example.data_binding.model.ConvertModel
import com.example.data_binding.model.CountryCodeModel
import com.example.data_binding.model.CurrencyModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("currency_data/historical")
    suspend fun getCurrency(
        @Query("date") date: String,
        @Query("source") source: String
    ) : Response<CurrencyModel>

    @GET("currency_data/list")
    suspend fun getCurrencyCode() : Response<CountryCodeModel>

    @GET("currency_data/convert")
    suspend fun getConvertAmount(
        @Query("to") to: String,
        @Query("from") from: String,
        @Query("amount") amount: Int,
        @Query("date") date: String
    ) : ConvertModel

}