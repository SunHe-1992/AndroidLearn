package com.hesun.comp304lab3_ex1.Views

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
import com.hesun.comp304lab3_ex1.ViewModel.citiesViewModel

@Composable
fun Screen2(navController: NavController,myViewModel: citiesViewModel, valueFromScreen1: String){

    var year by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxWidth().padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Screen 2 $valueFromScreen1")


        OutlinedTextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Value to screen 2") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            navController.previousBackStackEntry?.savedStateHandle?.set("year", year)
            navController.popBackStack()

        }) { Text("Go to Screen 1") }
    }

}