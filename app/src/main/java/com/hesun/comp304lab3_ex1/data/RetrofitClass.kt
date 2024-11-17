package com.hesun.comp304lab3_ex1.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClass {

    /** The base URL for the Geobytes API endpoints. */
    private const val baseURL = "http://gd.geobytes.com/"

    /** The base URL for the OpenWeatherMap API endpoints. */
    private const val weatherBaseURL = "https://api.openweathermap.org/data/2.5/"

    /**
     * A lazy-initialized instance of the APIInterface for Geobytes API calls.
     * This uses GsonConverterFactory for JSON serialization/deserialization.
     */
    val api: APIInterface by lazy {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseURL)
            .build()
        retrofit.create(APIInterface::class.java)
    }

    /**
     * A lazy-initialized instance of the APIInterface for OpenWeatherMap API calls.
     * This uses GsonConverterFactory for JSON serialization/deserialization with a different base URL.
     */
    val weatherApi: APIInterface by lazy {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(weatherBaseURL)
            .build()
        retrofit.create(APIInterface::class.java)
    }
}