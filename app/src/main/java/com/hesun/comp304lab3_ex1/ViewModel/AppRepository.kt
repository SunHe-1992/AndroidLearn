package com.hesun.comp304lab3_ex1.ViewModel

import com.hesun.comp304lab3_ex1.RoomDB.City
import com.hesun.comp304lab3_ex1.RoomDB.CityDAO
import com.hesun.comp304lab3_ex1.data.RetrofitClass
import com.hesun.comp304lab3_ex1.data.WeatherObject


class AppRepository(private val cityDao: CityDAO) {

    private val apiService = RetrofitClass.api
    private val weatherApiService = RetrofitClass.weatherApi

    suspend fun getCities(query: String): List<String> {
        return apiService.getCities(query)
    }

    suspend fun getWeather(city: String): WeatherObject? {
        //old key "071c3ffca10be01d334505630d2c1a9c"
        return weatherApiService.getWeather(city, "d12c846d30c2d560601ec94bea759617", "metric")
    }

    suspend fun getCitiesFromDB(): List<City> {
        return cityDao.getAllCities()
    }

    suspend fun insertCity(c: City) {
        if (searchCityNameInDB(c.cityName) == null) //avoid saving duplicate cities
            cityDao.insertCityToDB(c)
        else {
            //log city is already in db
            println("city is already in db")
        }
    }

    suspend fun deleteCity(c: City) {
        cityDao.deleteCity(c)
    }

    suspend fun searchForCityInDB(term: String): List<City> {
        return cityDao.getCityNamed(term)
    }

    suspend fun searchCityNameInDB(term: String): City? {
        return cityDao.getCityByExactName(term)
    }

    suspend fun update(newCity: City) {
        return cityDao.updateCity(newCity)// 44, Toronto, ON, Canda
    }

    suspend fun deleteAllCities() {
        return cityDao.deleteAllCities()
    }

}