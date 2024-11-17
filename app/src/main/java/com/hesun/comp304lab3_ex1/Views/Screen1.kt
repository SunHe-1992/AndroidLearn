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

/*screen for city search*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen1(
    innerPadding: PaddingValues,
    navController: NavController,
    cityVM: citiesViewModel
) {

    var searchText by remember { mutableStateOf("") }
    var selectedIndex by remember { mutableStateOf(-1) }
    val keyboardController = LocalSoftwareKeyboardController.current

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
                //prompt text
                placeholder = { Text("Enter a city name") },
                shape = RoundedCornerShape(15),
                leadingIcon = {
                    if (searchText.isNotEmpty())
                        Icon(
                            modifier = Modifier.clickable {
                                searchText = ""
                                cityVM.clearCities()
                            },
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close icon"
                        )
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


            //display List<string> in a LazyColumn, LazyColumn elements interval space is 4.dp
            if (cityVM.cities.size > 0) {
                LazyColumn(
                    modifier = Modifier
                        .safeDrawingPadding()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                )
                {
                    items(cityVM.cities.size) { index ->
                        CityRenderer(cityVM.cities.get(index),
                            index,
                            onItemClick = {
                                if (selectedIndex != index) {
                                    selectedIndex = index
                                    searchText = cityVM.cities[index]
                                    cityVM.clearCities()
                                    cityVM.cityName = searchText
                                } else {
                                    selectedIndex = -1
                                }
                            })
                    }


                }
            }

            Button(onClick = {
                cityVM.insertToDB(City(Math.random().toInt(), searchText.trim()))
            }) { Text("save to db") }

            Button(onClick = {
                cityVM.deleteOneCity(searchText.trim())
            }) { Text("delete from db") }

            Button(onClick = {
                cityVM.deleteAllCities()
            }) { Text("delete all cities") }

            Button(onClick = {
                var cityList = cityVM.getDBCities()
                //log citiList
                cityList.forEach {
                    Log.d("city", it.toString())
                }

            }) { Text("read db") }

            Button(onClick = {
                cityVM.cityName = searchText.trim()
                navController.navigate(NavItem.Screen2.createRoute(cityVM.cityName))
                cityVM.insertToDB(City(Math.random().toInt(), cityVM.cityName))
            }) { Text("show weather of this city") }


        }
    }
}


@Composable
private fun CityRenderer(cityName: String, index: Int, onItemClick: (Int) -> Unit) {
    if (cityName.length < 4) {
        return
    }
    val nameList = CityNameSplit(cityName)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onItemClick(index) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        )
        {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(nameList.first(), style = MaterialTheme.typography.labelSmall)

                //this text font color is gray
                Text(
                    nameList[1], style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            Icon(
                modifier = Modifier.clickable {
                    onItemClick(index)
                },
                imageVector = Icons.Default.Done,
                contentDescription = "Use this city",
            )
        }

    }
}

//sample:input  "Beijing, BJ, China"  output ["Beijing", " BJ, China"]
private fun CityNameSplit(cityName: String): List<String> {

    var splitted = cityName.split(',')
    var name1 = splitted.first()
    var name2 = cityName.replace(name1, "")
    //name2 remove first 2 characters
    name2 = name2.substring(2)
    return listOf(name1, name2)
}