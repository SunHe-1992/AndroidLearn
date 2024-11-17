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
    /**
     * A list of navigation items to be displayed in the bottom navigation bar.
     */
    val navItems = listOf(NavItem.Screen1, NavItem.Screen2, NavItem.ScreenSavedCities)

    NavigationBar {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                /**
                 * Indicates whether the current item is selected.
                 */
                selected = cityVM.naviIndex == index,
                /**
                 * Handles the click event for the navigation item.
                 * Updates the navigation index, logs the navigation path, and navigates to the corresponding screen.
                 */
                onClick = {
                    cityVM.naviIndex = index
                    Log.i("MyBottomBar", item.path)
                    navController.navigate(item.path) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                /**
                 * Displays the icon for the navigation item.
                 */
                icon = {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(30.dp)
                    )
                },
                /**
                 * Displays the label for the navigation item.
                 */
                label = { Text(item.title) })
        }
    }
}