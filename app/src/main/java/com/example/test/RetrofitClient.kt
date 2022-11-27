package com.example.test

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    private var retrofit: Retrofit? = null // Создаем пустой объект ретрофита для  последующей настройки
    var baseUrl = "" // Создаем базовый url

    fun getClient(baseUrl: String): Retrofit{
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        RetrofitClient.baseUrl = baseUrl
        return retrofit!!
    }
}