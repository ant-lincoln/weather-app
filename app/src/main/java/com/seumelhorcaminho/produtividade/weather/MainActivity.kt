package com.seumelhorcaminho.produtividade.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.seumelhorcaminho.produtividade.weather.viewmodel.WeatherViewModel


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        setContent {
            WeatherMain(weatherViewModel)
        }
    }
}

