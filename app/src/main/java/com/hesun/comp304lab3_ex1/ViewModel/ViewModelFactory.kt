package com.hesun.comp304lab3_ex1.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val repository: AppRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(citiesViewModel::class.java)) {
            return citiesViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }
}


class WeatherViewModelFactory(private val repository: AppRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }
}