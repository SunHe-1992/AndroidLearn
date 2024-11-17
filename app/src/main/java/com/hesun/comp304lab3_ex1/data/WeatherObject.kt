package com.hesun.comp304lab3_ex1.data

/**
 * Represents the weather data for a specific location.
 *
 * @property main Contains core weather information like temperature and feels-like temperature.
 * @property weather A list of weather descriptions, including main description and icon.
 * @property name The name of the location.
 */
data class WeatherObject(
    val main: mObje,
    val weather: List<wObje>,
    val name: String
) {
}

/**
 * Represents a weather description.
 *
 * @property main The main weather condition (e.g., "Clear", "Clouds").
 * @property description A more detailed description of the weather.
 * @property icon The icon code for the weather condition.
 */
data class wObje(
    val main: String,
    val description: String,
    val icon: String
) {}

/**
 * Represents core weather information.
 *
 * @property temp The temperature in Kelvin.
 * @property feels_like The temperature felt by humans.
 */
data class mObje(
    var temp: Double,
    var feels_like: Double
) {}