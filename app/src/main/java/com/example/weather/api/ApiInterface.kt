package com.example.weather.api

import com.example.weather.model.Coordinates
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by mac on 27/01/24.
 */interface ApiInterface {

    @GET("/data/2.5/weather?")
    fun getCoordinate(@Query("q") q: String, @Query("appid") appid: String): Call<Coordinates>
}
