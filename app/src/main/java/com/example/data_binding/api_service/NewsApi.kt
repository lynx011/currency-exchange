package com.example.data_binding.api_service

import com.example.data_binding.model.NewsModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun getArticle(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Header("apiKey") apiKey: String
    ) : Response<NewsModel>

    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"
        private var newsApi: NewsApi? = null
        fun getInstance(): NewsApi {
            if (newsApi == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                newsApi = retrofit.create(NewsApi::class.java)
            }
            return newsApi!!
        }
    }
}