package com.hesun.comp304lab3_ex1.RoomDB

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * Represents the database for storing city information.
 */
@androidx.room.Database(entities = [City::class], version = 1)
abstract class CityDataBase : RoomDatabase() {

    /**
     * Provides access to the CityDAO interface for interacting with city data.
     *
     * @return An instance of the CityDAO.
     */
    abstract fun getCityDao(): CityDAO

    // Multithreading with Singleton pattern

    /**
     * A singleton instance of the CityDataBase.
     */
    companion object {
        @Volatile
        private var INSTANCE: CityDataBase? = null

        /**
         * Retrieves an instance of the CityDataBase.
         *
         * This method uses a synchronized block to ensure thread-safety
         * and prevent race conditions when accessing the INSTANCE variable.
         *
         * @param context The application context required for building the database.
         * @return An instance of the CityDataBase.
         */
        fun getInstance(context: Context): CityDataBase {
            synchronized(this) {
                var inst = INSTANCE
                if (inst == null) {
                    inst = Room.databaseBuilder(
                        context,
                        CityDataBase::class.java,
                        "citiesDB"
                    ).build()
                }
                INSTANCE = inst
                return inst
            }
        }
    }
}