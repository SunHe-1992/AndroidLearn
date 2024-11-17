package com.hesun.comp304lab3_ex1.Navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hesun.comp304lab3_ex1.ViewModel.WeatherViewModel
import com.hesun.comp304lab3_ex1.ViewModel.citiesViewModel
import com.hesun.comp304lab3_ex1.Views.Screen1
import com.hesun.comp304lab3_ex1.Views.Screen2


@Composable
fun MyNavGraph(
    innerPadding: PaddingValues,
    navController: NavHostController,
    cityVM: citiesViewModel,
    weatherVM: WeatherViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavItem.Screen1.path
    ) {

        composable(NavItem.Screen1.path) {
            Screen1(innerPadding, navController, cityVM)
        }
        composable(NavItem.Screen2.path) {
            Screen2(innerPadding, navController, weatherVM, cityVM, "")
        }
        composable(
            NavItem.Screen2.path.plus("/{value}"),
            arguments = listOf(navArgument("value") {
                type = NavType.StringType
            })
        )
        { backStackEntery ->
            Screen2(
                innerPadding,
                navController,
                weatherVM,
                cityVM,
                backStackEntery.arguments?.getString("value").toString()
            )
        }
    }
}
