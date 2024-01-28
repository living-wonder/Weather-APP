package com.example.weather.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by mac on 27/01/24.
 */
object ApiClient {
    var retrofit: Retrofit?=null
    fun getinstance():Retrofit{
        val builder=Retrofit.Builder().baseUrl("https://api.openweathermap.org").addConverterFactory(
            GsonConverterFactory.create()).build()
        retrofit = builder
        return retrofit!!
    }
}