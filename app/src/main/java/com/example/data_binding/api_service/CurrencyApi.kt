package com.example.data_binding.api_service

import com.example.data_binding.model.CurrencyModel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface CurrencyApi {

    @GET("currency_data/live")
    suspend fun getCurrency(
        @Query("base") base: String,
    ) : Response<Map<String,Any>>


    companion object {
        private const val API_KEY = "pkKiNeFj3sw7e0RyYi5acBKCES9755dZ"

        private val httpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
                    .header("apiKey", API_KEY)
                    .build()
                chain.proceed(newRequest)
            }
            .connectTimeout(20,TimeUnit.SECONDS)
            .readTimeout(20,TimeUnit.SECONDS)
            .build()

        private const val BASE_URL = "https://api.apilayer.com/"
        private var currenciesApi: CurrencyApi? = null
        fun getInstance() : CurrencyApi {
            if (currenciesApi == null){
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                currenciesApi = retrofit.create(CurrencyApi::class.java)
            }
            return currenciesApi!!
        }
    }
}