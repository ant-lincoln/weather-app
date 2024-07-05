package com.seumelhorcaminho.produtividade.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.seumelhorcaminho.produtividade.weather.api.NetworkResponse
import com.seumelhorcaminho.produtividade.weather.model.WeatherModel
import com.seumelhorcaminho.produtividade.weather.viewmodel.WeatherViewModel


@Composable
fun WeatherMain(viewModel: WeatherViewModel) {

    var city by remember {
        mutableStateOf("")
    }

    val weatherResult = viewModel.weatherResult.observeAsState()

    val keyboardController = LocalSoftwareKeyboardController.current


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimCloud()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = city,
                onValueChange = {
                    city = it
                },
                label = {
                    Text(text = "Your city")
                }
            )
            IconButton(onClick = {
                viewModel.getData(city)
                keyboardController?.hide()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        }

        when (val result = weatherResult.value) {
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }

            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }

            is NetworkResponse.Success -> {
                (WeatherDetails(data = result.data))
            }

            null -> {}
        }
    }
}

@Composable
fun AnimCloud() {
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedCloud(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun WeatherDetails(data: WeatherModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location icon",
                modifier = Modifier.size(40.dp)
            )
            Text(text = data.location.name, fontSize = 30.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = data.location.country, fontSize = 18.sp, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${data.current.temp_c} ºC",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        AsyncImage(
            modifier = Modifier.size(160.dp),
            model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),
            contentDescription = "Icon Weather"
        )
        Text(
            text = data.current.condition.text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal(
                        icon = R.drawable.ic_humidity,
                        key = "Humidity",
                        value = data.current.humidity
                    )
                    WeatherKeyVal(
                        icon = R.drawable.ic_wind,
                        key = "Wind Speed",
                        value = data.current.wind_kph + " Km/h"
                    )
                    WeatherKeyVal(icon = R.drawable.ic_uv, key = "UV", value = data.current.uv)
                }

            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            WeatherKeyVal(
                icon = null,
                key = data.current.last_updated.split(" ")[0],
                value = ""
            )
            WeatherKeyVal(
                icon = null,
                key = data.current.last_updated.split(" ")[1],
                value = ""
            )
        }
    }
}

@Composable
fun WeatherKeyVal(key: String, value: String, icon: Int?) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon?.let { painterResource(id = it) }?.let {
            Image(
                painter = it,
                contentDescription = "Weather Icon",
                modifier = Modifier.size(24.dp)
            )
        }
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = key, fontWeight = FontWeight.SemiBold, color = Color.Gray)
    }
}


