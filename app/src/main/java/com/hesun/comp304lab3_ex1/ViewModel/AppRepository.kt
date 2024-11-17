package com.hesun.comp304lab3_ex1.ViewModel

import com.hesun.comp304lab3_ex1.RoomDB.City
import com.hesun.comp304lab3_ex1.RoomDB.CityDAO
import com.hesun.comp304lab3_ex1.data.RetrofitClass
import com.hesun.comp304lab3_ex1.data.WeatherObject


/**
 * Repository class for handling data operations related to cities and weather.
 *
 * This class acts as a single source of truth for data, interacting with both the network APIs and the local database.
 */
class AppRepository(private val cityDao: CityDAO) {

    /**
     * Reference to the API service for fetching city suggestions.
     */
    private val apiService = RetrofitClass.api

    /**
     * Reference to the API service for fetching weather information.
     */
    private val weatherApiService = RetrofitClass.weatherApi

    /**
     * Fetches a list of city suggestions based on the provided query.
     *
     * @param query The search query for city suggestions.
     * @return A list of city suggestions.
     */
    suspend fun getCities(query: String): List<String> {
        return apiService.getCities(query)
    }

    /**
     * Fetches weather information for a specific city.
     *
     * @param city The name of the city to fetch weather data for.
     * @return The weather information for the city, or null if not found.
     */
    suspend fun getWeather(city: String): WeatherObject? {
        return weatherApiService.getWeather(city, "d12c846d30c2d560601ec94bea759617", "metric")
    }

    /**
     * Retrieves all cities stored in the database.
     *
     * @return A list of all cities stored in the database.
     */
    suspend fun getCitiesFromDB(): List<City> {
        return cityDao.getAllCities()
    }

    /**
     * Inserts a new city into the database, avoiding duplicates.
     *
     * @param c The city to be inserted.
     */
    suspend fun insertCity(c: City) {
        if (searchCityNameInDB(c.cityName) == null) { // Avoid saving duplicate cities
            cityDao.insertCityToDB(c)
        } else {
            // Log that the city is already in the database
            println("City is already in the database")
        }
    }

    /**
     * Deletes a city from the database.
     *
     * @param name The name of the city to be deleted.
     */
    suspend fun deleteCity(name: String) {
        cityDao.deleteCityByName(name)
    }

    /**
     * Searches for cities in the database based on a search term.
     *
     * @param term The search term to filter cities by.
     * @return A list of cities matching the search term.
     */
    suspend fun searchForCityInDB(term: String): List<City> {
        return cityDao.getCityNamed(term)
    }

    /**
     * Searches for a city in the database by its exact name.
     *
     * @param term The exact name of the city to search for.
     * @return The city with the exact name, or null if not found.
     */
    suspend fun searchCityNameInDB(term: String): City? {
        return cityDao.getCityByExactName(term)
    }

    /**
     * Updates an existing city in the database.
     *
     * @param newCity The updated city information.
     */
    suspend fun update(newCity: City) {
        cityDao.updateCity(newCity)
    }

    /**
     * Deletes all cities from the database.
     */
    suspend fun deleteAllCities() {
        cityDao.deleteAllCities()
    }
}