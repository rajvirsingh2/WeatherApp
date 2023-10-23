package com.example.weatherapp

import com.example.weatherapp.Data.WeatherApp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {
    @GET("weather")
    fun getWeather(
        @Query("q") city: String,
        @Query("appid") appid: String,
        @Query("units") units: String
    ): Call<WeatherApp>
}