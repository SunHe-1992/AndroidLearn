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
            //show no cities saved

            Text(text = "No cities saved", modifier = Modifier.padding(innerPadding))

        }

        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        )
        {

            items(cityVM.dbcities.size) { index ->
                val item = cityVM.dbcities.get(index)
                SavedCityRenderer(item.cityName, index, onDeleteClick = {
                    cityVM.deleteOneCity(item.cityName)

                }, onConfirmClick = {
                    cityVM.cityName = item.cityName
                    navController.navigate(NavItem.Screen2.createRoute(cityVM.cityName)) {
                        launchSingleTop = true
                    }
                }
                )
            }
        }
    }
}


@Composable
private fun SavedCityRenderer(
    cityName: String,
    index: Int,
    onDeleteClick: (Int) -> Unit,
    onConfirmClick: (Int) -> Unit
) {
    if (cityName.length < 4) {
        return
    }
    val nameList = CityNameSplit(cityName)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable {
                onConfirmClick(index)
            }
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
                    onDeleteClick(index)
                },
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete this",
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