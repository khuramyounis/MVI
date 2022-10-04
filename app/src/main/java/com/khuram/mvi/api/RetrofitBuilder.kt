package com.khuram.mvi.api

import com.khuram.mvi.util.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {

    private const val BASE_URL = "https://api.thedogapi.com/v1/"

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(client)
    }

    val apiService: ApiService by lazy {
        retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }

    private val client = OkHttpClient
        .Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader(
                    "api-key",
                    "live_ZjAu4iqSFC7ugeedvpUFqmOmQsp2XgAH3ds86CHGCpZdqXNiuTm14Fm5lbOSLvqf"
                )
                .build()
            chain.proceed(request)
        }
        .build()
}
