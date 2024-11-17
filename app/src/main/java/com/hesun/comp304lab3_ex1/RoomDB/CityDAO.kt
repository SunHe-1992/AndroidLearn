package com.hesun.comp304lab3_ex1.RoomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface CityDAO {

    /**
     * Retrieves all cities from the database.
     *
     * @return A list of all cities.
     */
    @Query("select * from City")
    suspend fun getAllCities(): List<City>

    /**
     * Inserts a new city into the database.
     *
     * @param c The city to be inserted.
     */
    @Insert
    suspend fun insertCityToDB(c: City);

    /**
     * Deletes a city from the database.
     *
     * @param c The city to be deleted.
     */
    @Delete
    suspend fun deleteCity(c: City);

    /**
     * Retrieves cities whose names contain the given search term.
     *
     * @param cityName The search term to filter cities by.
     * @return A list of cities matching the search term.
     */
    @Query("select * from City where city LIKE :cityName ")
    suspend fun getCityNamed(cityName: String): List<City>

    /**
     * Updates an existing city in the database.
     *
     * @param CityToUpdate The city with updated information.
     */
    @Update
    suspend fun updateCity(CityToUpdate: City)

    /**
     * Deletes all cities from the database.
     */
    @Query("DELETE FROM City")
    suspend fun deleteAllCities()

    /**
     * Retrieves a single city by its exact name.
     *
     * @param cityName The exact name of the city to retrieve.
     * @return The city with the specified name, or null if not found.
     */
    @Query("SELECT * FROM City WHERE city = :cityName")
    suspend fun getCityByExactName(cityName: String): City?

    /**
     * Deletes a city by its exact name.
     *
     * @param cityName The exact name of the city to delete.
     */
    @Query("DELETE FROM City WHERE city = :cityName")
    suspend fun deleteCityByName(cityName: String)
}