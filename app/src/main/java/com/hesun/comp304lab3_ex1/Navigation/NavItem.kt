package com.hesun.comp304lab3_ex1.Navigation

import  com.hesun.comp304lab3_ex1.R

sealed class NavItem(var route: String) {

    // search city
    object Screen1 : Item("screen1", "Screen1", R.drawable.search) {

    }

    //weather screen
    object Screen2 : Item("screen2", "Screen2", R.drawable.weather_icon) {
        fun createRoute(valueToPass: String): String {
            return "Screen2/$valueToPass"
        }
    }

    // saved cities list
    object ScreenSavedCities : Item("ScreenSavedCities", "Cities", R.drawable.city)
}

