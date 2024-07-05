package com.seumelhorcaminho.produtividade.weather.model

import com.seumelhorcaminho.produtividade.weather.api.Current
import com.seumelhorcaminho.produtividade.weather.api.Location

data class WeatherModel(
    val current: Current,
    val location: Location
)