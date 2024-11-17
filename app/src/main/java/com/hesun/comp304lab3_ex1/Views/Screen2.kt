package com.hesun.comp304lab3_ex1.Views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hesun.comp304lab3_ex1.ViewModel.WeatherViewModel
import com.hesun.comp304lab3_ex1.ViewModel.citiesViewModel
import com.hesun.comp304lab3_ex1.ui.theme.Hesun_COMP304Lab3_Ex1Theme


@Composable
fun Screen2(
    innerPadding: PaddingValues,
    navController: NavController,
    weatherVM: WeatherViewModel,
    cityVM: citiesViewModel,
    cityName: String
) {
    cityVM.naviIndex = 1
    var strCity = cityName
    if (strCity.isNullOrEmpty())
        strCity = cityVM.cityName

    weatherVM.getWeather(strCity)

    Hesun_COMP304Lab3_Ex1Theme {
        if (weatherVM.weatherO != null) {
            var weatherData = weatherVM.weatherO
            if (weatherData != null) {
                var icon =
                    "https://openweathermap.org/img/wn/${weatherData.weather.get(0).icon}@2x.png"
                WeatherScreen(
                    innerPadding,
                    weatherData.name, weatherData.main.temp.toString(),
                    weatherData.main.feels_like.toString(),
                    icon
                )
            }
        }
    }
}


@Composable
fun WeatherScreen(
    innerPadding: PaddingValues,
    cityName: String,
    temperature: String,
    feelsLike: String,
    weatherIconUrl: String
) {
    Box(
        modifier = Modifier
            .padding(innerPadding)
            .safeDrawingPadding()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp)),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // City Name
                Text(
                    text = cityName,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Weather Icon
                AsyncImage(
                    model = weatherIconUrl,
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(120.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Temperature
                Text(
                    text = "$temperature°",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(8.dp))


                // Additional Weather Details Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        WeatherDetailItem(
                            label = "Temperature",
                            value = "$temperature°"
                        )
                        WeatherDetailItem(
                            label = "Feels Like",
                            value = "$feelsLike°"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WeatherDetailItem(
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}