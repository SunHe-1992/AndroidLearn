package com.hesun.comp304lab3_ex1.Views


import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hesun.comp304lab3_ex1.RoomDB.City
import com.hesun.comp304lab3_ex1.ViewModel.citiesViewModel


@Composable
fun Screen1(navController: NavController, myViewModel: citiesViewModel, year: String?) {

    var value by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Screen 1 ")

        year?.let {
            Text("Year From Screen 2 $year")
        }
        OutlinedTextField(
            value = value,
            onValueChange = { value = it },
            label = { Text("Save this content") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            myViewModel.insertToDB(City(Math.random().toInt(), value.trim()))
        }) { Text("save to db") }

        Button(onClick = {
            myViewModel.deleteOneCity(City(Math.random().toInt(), value.trim()))
        }) { Text("delete from db") }

        Button(onClick = {
            myViewModel.deleteAllCities()
        }) { Text("delete all cities") }

        Button(onClick = {
            var cityList = myViewModel.getDBCities()
            //log citiList
            cityList.forEach {
                Log.d("city", it.toString())
            }

        }) { Text("read db") }
    }

}