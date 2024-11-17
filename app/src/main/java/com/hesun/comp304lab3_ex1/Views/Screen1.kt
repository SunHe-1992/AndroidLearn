package com.hesun.comp304lab3_ex1.Views


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hesun.comp304lab3_ex1.Navigation.NavItem
import com.hesun.comp304lab3_ex1.RoomDB.City
import com.hesun.comp304lab3_ex1.ViewModel.citiesViewModel
import com.hesun.comp304lab3_ex1.ui.theme.Hesun_COMP304Lab3_Ex1Theme

/*
 * Screen for searching cities.
 *
 * This screen allows users to input a city name, view suggested cities, and select a city to view its weather.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen1(
    innerPadding: PaddingValues,
    navController: NavController,
    cityVM: citiesViewModel
) {

    cityVM.naviIndex = 0
    var searchText by remember { mutableStateOf("") }
    var selectedIndex by remember { mutableStateOf(-1) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isDebug = false

    Hesun_COMP304Lab3_Ex1Theme {
        Column(
            modifier = Modifier
                .safeDrawingPadding()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth(),
                value = searchText,
                onValueChange = { searchText = it },
                // Prompt text for the text field
                placeholder = { Text("Enter a city name") },
                shape = RoundedCornerShape(15),
                leadingIcon = {
                    if (searchText.isNotEmpty()) {
                        Icon(
                            modifier = Modifier.clickable {
                                searchText = ""
                                cityVM.clearCities()
                            },
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear search text"
                        )
                    }
                },
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        Button(onClick = {
                            cityVM.getCities(searchText)
                            keyboardController?.hide()
                        }) { Text("Search City") }
                    }
                },
            )

            // Display a list of suggested cities in a LazyColumn
            if (cityVM.cities.size > 0) {
                LazyColumn(
                    modifier = Modifier
                        .safeDrawingPadding()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(cityVM.cities.size) { index ->
                        CityRenderer(cityVM.cities.get(index), index) {
                            if (selectedIndex != index) {
                                selectedIndex = index
                                searchText = cityVM.cities[index]
                                cityVM.clearCities()
                                cityVM.cityName = searchText
                            } else {
                                selectedIndex = -1
                            }
                        }
                    }
                }
            }

            // Debug buttons for database operations (optional, can be removed in production)
            if (isDebug) {
                Button(onClick = {
                    cityVM.insertToDB(City(Math.random().toInt(), searchText.trim()))
                }) { Text("Save to DB") }

                Button(onClick = {
                    cityVM.deleteOneCity(searchText.trim())
                }) { Text("Delete from DB") }

                Button(onClick = {
                    cityVM.deleteAllCities()
                }) { Text("Delete All Cities") }

                Button(onClick = {
                    val cityList = cityVM.getDBCities()
                    cityList.forEach {
                        Log.d("city", it.toString())
                    }
                }) { Text("Read DB") }
            }

            Button(onClick = {
                cityVM.cityName = searchText.trim()
                navController.navigate(NavItem.Screen2.createRoute(cityVM.cityName)) {
                    launchSingleTop = true
                }
                cityVM.insertToDB(City(Math.random().toInt(), cityVM.cityName))
            }) { Text("Show Weather of This City") }
        }
    }
}

/**
 * Renders a single city suggestion in the list.
 *
 * @param cityName The name of the city to display.
 * @param index The index of the city in the list.
 * @param onItemClick A callback function to handle the click event.
 */
@Composable
private fun CityRenderer(cityName: String, index: Int, onItemClick: () -> Unit) {
    if (cityName.length < 4) {
        return
    }
    val nameList = CityNameSplit(cityName)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onItemClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(nameList.first(), style = MaterialTheme.typography.labelSmall)

                // Display the city's region and country in a smaller font size and gray color
                Text(
                    nameList[1], style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            Icon(
                modifier = Modifier.clickable { onItemClick() },
                imageVector = Icons.Default.Done,
                contentDescription = "Select this city",
            )
        }
    }
}

/**
 * Splits a city name into two parts: the city name and the region/country.
 *
 * @param cityName The full city name.
 * @return A list containing the city name and the region/country.
 */
private fun CityNameSplit(cityName: String): List<String> {
    val splitted = cityName.split(',')
    val name1 = splitted.first()
    val name2 = cityName.replace(name1, "").substring(2)
    return listOf(name1, name2)
}