package com.seumelhorcaminho.produtividade.weather.api

import com.seumelhorcaminho.produtividade.weather.model.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("current.json")
    suspend fun getWeather(
        @Query("key") apikey: String,
        @Query("q") city: String
    ) : Response<WeatherModel>

}