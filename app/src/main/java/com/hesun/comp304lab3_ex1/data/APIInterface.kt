package com.hesun.comp304lab3_ex1.data

import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    /**
     * Fetches a list of city names based on the provided query string.
     *
     * @param q The query string to search for.
     * @return A list of city names matching the query.
     */
    @GET("AutoCompleteCity")
    suspend fun getCities(@Query("q") q: String): List<String>

    /**
     * Fetches weather information for a specific city.
     *
     * @param q The city name to fetch weather data for.
     * @param key The API key for the weather service.
     * @param units The units of measurement for the weather data (e.g., metric, imperial).
     * @return A `WeatherObject` containing the weather information.
     */
    @GET("weather?")
    suspend fun getWeather(
        @Query("q") q: String,
        @Query("appid") key: String,
        @Query("units") units: String
    ): WeatherObject
}