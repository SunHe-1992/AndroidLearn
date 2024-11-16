package com.hesun.comp304lab3_ex1.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search

sealed class NavItem {
    object Screen1 : Item("screen1", "Screen1", Icons.Default.Search) {

    }

    object Screen2 : Item("screen2", "Screen2", Icons.Default.Email) {
        // Screen2 ===> Screen2/{value}
//        fun createRoute(valueToPass: String): String {
//            return "Screen2/$valueToPass"
//        }
    }
}

