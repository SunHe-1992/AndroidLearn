package com.hesun.comp304lab3_ex1.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun MyNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavItem.Call.path
    ) {

        composable(
            route = NavItem.Call.path
        ) {

        }

        composable(
            route = NavItem.Email.path
        ) {

        }

        composable(
            route = NavItem.Favorite.path
        ) {

        }

        composable(
            route = NavItem.Search.path
        ) {

        }
    }
}
