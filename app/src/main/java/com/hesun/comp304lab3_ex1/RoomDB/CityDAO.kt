package com.hesun.comp304lab3_ex1.RoomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface CityDAO {

    @Query("select * from City")
    suspend fun getAllCities(): List<City>

    @Insert
    suspend fun insertCityToDB(c: City);

    @Delete
    suspend fun deleteCity(c: City);

    @Query("select * from City where city LIKE :cityName ")
    suspend fun getCityNamed(cityName: String): List<City>

    @Update
    suspend fun updateCity(CityToUpdate: City)

    // Delete all cities
    @Query("DELETE FROM City")
    suspend fun deleteAllCities()

    // Get single city by exact name match
    @Query("SELECT * FROM City WHERE city = :cityName")
    suspend fun getCityByExactName(cityName: String): City?
}