package com.hesun.comp304lab3_ex1.Views


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hesun.comp304lab3_ex1.Navigation.NavItem
import com.hesun.comp304lab3_ex1.ViewModel.citiesViewModel

@Composable
fun MyBottomBar(navController: NavController, cityVM: citiesViewModel) {
    val navItems = listOf(NavItem.Screen1, NavItem.Screen2, NavItem.ScreenSavedCities)

    NavigationBar {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = cityVM.naviIndex == index,
                onClick = {
                    cityVM.naviIndex = index
                    //Log.i item.path
                    Log.i("MyBottomBar", item.path)
                    navController.navigate(item.path) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(30.dp)
                    )


                },
                label = { Text(item.title) })
        }
    }

}