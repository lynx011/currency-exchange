package com.example.data_binding.di
import com.example.data_binding.api_service.CurrencyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val API_KEY = "fYnghXRX0nWBAksikNLaGR9MmcUHuYIJ"
    private const val BASE_URL = "https://api.apilayer.com/"

    @Singleton
    @Provides
    fun provideHttpClient() : OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor { chain ->
            val request = chain.request()
            val newRequest = request.newBuilder()
                .addHeader("apiKey", API_KEY)
                .build()
            chain.proceed(newRequest)
        }
        .connectTimeout(60,TimeUnit.SECONDS)
        .readTimeout(60,TimeUnit.SECONDS)
        .writeTimeout(60,TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) : CurrencyApi = retrofit.create(CurrencyApi::class.java)
}