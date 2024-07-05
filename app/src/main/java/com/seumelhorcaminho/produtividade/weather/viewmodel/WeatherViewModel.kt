package com.seumelhorcaminho.produtividade.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seumelhorcaminho.produtividade.weather.api.Constants
import com.seumelhorcaminho.produtividade.weather.api.NetworkResponse
import com.seumelhorcaminho.produtividade.weather.api.RetrofitInstance
import com.seumelhorcaminho.produtividade.weather.model.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult : LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(city: String) {
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(Constants.API_KEY, city)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _weatherResult.value = NetworkResponse.Error("Erro")
                }
            } catch (e: Exception) {
                _weatherResult.value = NetworkResponse.Error("Erro")
            }

        }

    }

}