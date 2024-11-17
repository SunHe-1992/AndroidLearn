package com.hesun.comp304lab3_ex1.ViewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hesun.comp304lab3_ex1.RoomDB.City
import kotlinx.coroutines.launch
class citiesViewModel(private val repository: AppRepository) : ViewModel() {

    /**
     * The currently selected city name.
     */
    var cityName by mutableStateOf("Beijing, BJ, China")

    /**
     * A list of suggested cities based on user input.
     */
    var cities by mutableStateOf<List<String>>(emptyList())
        private set

    /**
     * The current navigation index.
     */
    var naviIndex by mutableStateOf(0)

    /**
     * A list of cities stored in the database.
     */
    var dbcities by mutableStateOf<List<City>>(emptyList())
        private set

    /**
     * Initializes the ViewModel and fetches initial data.
     */
    init {
        viewModelScope.launch {
            // Fetch initial city suggestions
            val fetchCities = repository.getCities("")
            cities = fetchCities

            // Fetch cities from the database
            val citiesFromDB = repository.getCitiesFromDB()
            dbcities = citiesFromDB
        }
    }

    /**
     * Fetches city suggestions based on a user-provided search term.
     *
     * @param userTerm The user's search term.
     * @return A list of city suggestions matching the search term.
     */
    fun getCities(userTerm: String): List<String> {
        viewModelScope.launch {
            val fetchCities = repository.getCities(userTerm)
            cities = fetchCities
        }
        return cities
    }

    /**
     * Fetches all cities from the database.
     *
     * @return A list of all cities stored in the database.
     */
    fun getDBCities(): List<City> {
        viewModelScope.launch {
            val dbfetchCities = repository.getCitiesFromDB()
            dbcities = dbfetchCities
        }
        return dbcities
    }

    /**
     * Inserts a new city into the database.
     *
     * @param c The city to be inserted.
     */
    fun insertToDB(c: City) {
        viewModelScope.launch {
            repository.insertCity(c)
            // Update the list of database cities after insertion
            val dbfetchCities = repository.getCitiesFromDB()
            dbcities = dbfetchCities
        }
    }

    /**
     * Updates an existing city in the database.
     *
     * @param newCity The updated city information.
     */
    fun update(newCity: City) {
        viewModelScope.launch {
            repository.update(newCity)
            // Update the list of database cities after update
            val dbfetchCities = repository.getCitiesFromDB()
            dbcities = dbfetchCities
        }
    }

    /**
     * Deletes a city from the database and updates the `cityName` if necessary.
     *
     * @param name The name of the city to be deleted.
     */
    fun deleteOneCity(name: String) {
        var needReset = false
        if (name == cityName) {
            needReset = true
        }
        viewModelScope.launch {
            repository.deleteCity(name)
            // Update the list of database cities after deletion
            val dbfetchCities = repository.getCitiesFromDB()
            dbcities = dbfetchCities
            if (needReset) {
                if (dbcities.count() > 0) {
                    cityName = dbcities.first().cityName
                } else {
                    cityName = "Beijing, BJ, China"
                }
            }
        }
    }

    /**
     * Deletes all cities from the database.
     */
    fun deleteAllCities() {
        viewModelScope.launch {
            repository.deleteAllCities()
            // Update the list of database cities after deletion
            val dbfetchCities = repository.getCitiesFromDB()
            dbcities = dbfetchCities
        }
    }

    /**
     * Clears the list of suggested cities.
     */
    fun clearCities() {
        cities = emptyList()
    }
}