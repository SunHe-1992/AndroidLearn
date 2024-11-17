package com.hesun.comp304lab3_ex1.Views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hesun.comp304lab3_ex1.Navigation.NavItem
import com.hesun.comp304lab3_ex1.ViewModel.citiesViewModel
import com.hesun.comp304lab3_ex1.ui.theme.Hesun_COMP304Lab3_Ex1Theme

@Composable
fun ScreenSavedCities(
    innerPadding: PaddingValues,
    navController: NavController,
    cityVM: citiesViewModel
) {
    cityVM.naviIndex = 2

    Hesun_COMP304Lab3_Ex1Theme {
        if (cityVM.dbcities.size == 0) {
            // Display a message if no cities are saved
            Text(text = "No cities saved", modifier = Modifier.padding(innerPadding))
        } else {
            // Display a list of saved cities
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(cityVM.dbcities.size) { index ->
                    val item = cityVM.dbcities[index]
                    SavedCityRenderer(
                        item.cityName, index, onDeleteClick = {
                            cityVM.deleteOneCity(item.cityName)
                        },
                        onConfirmClick = {
                            cityVM.cityName = item.cityName
                            navController.navigate(NavItem.Screen2.createRoute(cityVM.cityName)) {
                                launchSingleTop = true
                            }
                        })
                }
            }
        }
    }
}


/**
 * Renders a single saved city in the list.
 *
 * @param cityName The name of the city to display.
 * @param index The index of the city in the list.
 * @param onDeleteClick A callback function to handle the delete click event.
 * @param onConfirmClick A callback function to handle the confirm click event.
 */
@Composable
private fun SavedCityRenderer(
    cityName: String,
    index: Int,
    onDeleteClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    if (cityName.length < 4) {
        return
    }
    val nameList = CityNameSplit(cityName)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onConfirmClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(nameList.first(), style = MaterialTheme.typography.labelSmall)
                Text(
                    nameList[1], style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            Icon(
                modifier = Modifier.clickable { onDeleteClick() },
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete this city",
            )
        }
    }
}

// Splits a city name into two parts: the city name and the region/country.
private fun CityNameSplit(cityName: String): List<String> {
    val splitted = cityName.split(',')
    val name1 = splitted.first()
    val name2 = cityName.replace(name1, "").substring(2)
    return listOf(name1, name2)
}