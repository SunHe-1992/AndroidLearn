package com.hesun.comp304lab3_ex1.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClass {

private const val baseURL = "http://gd.geobytes.com/"
    private const val weatherBaseURL = "https://api.openweathermap.org/data/2.5/"
    val api: APIInterface by lazy{
      val retrofit =  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseURL)
            .build()
        retrofit.create(APIInterface::class.java)
    }

    val weatherApi : APIInterface by lazy {
        val retrofit =  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(weatherBaseURL)
            .build()
        retrofit.create(APIInterface::class.java)
    }
}