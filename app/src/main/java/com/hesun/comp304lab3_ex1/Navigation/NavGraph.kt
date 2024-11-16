package com.hesun.comp304lab3_ex1.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hesun.comp304lab3_ex1.ViewModel.citiesViewModel
import com.hesun.comp304lab3_ex1.Views.Screen1
import com.hesun.comp304lab3_ex1.Views.Screen2


@Composable
fun MyNavGraph(navController: NavHostController, myViewModel: citiesViewModel) {
    NavHost(
        navController = navController,
        startDestination = NavItem.Screen1.path
    ) {

        composable(NavItem.Screen1.path) {
//            val year = entery.savedStateHandle.get<String>("year")
            Screen1(navController, myViewModel, "2025")
        }

        composable(NavItem.Screen2.path)
        {
            //Screen2(navController, backStackEntery.arguments?.getString("value").toString() )
            Screen2(navController, myViewModel, "2021")
        }
    }
}
