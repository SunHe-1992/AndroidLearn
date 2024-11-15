package com.hesun.comp304lab3_ex1.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hesun.comp304lab3_ex1.Views.Screen1
import com.hesun.comp304lab3_ex1.Views.Screen2
import kotlin.text.get


@Composable
fun MyNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavItem.Screen1.path
    ) {

        composable(NavItem.Screen1.path) {
//            val year = entery.savedStateHandle.get<String>("year")
              Screen1(navController,"2025")
        }

        composable(NavItem.Screen2.path)
        {
            //Screen2(navController, backStackEntery.arguments?.getString("value").toString() )
              Screen2(navController,"2021")
        }
    }
}
