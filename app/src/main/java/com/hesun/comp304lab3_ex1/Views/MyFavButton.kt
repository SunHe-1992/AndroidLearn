package com.hesun.comp304lab3_ex1.Views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun MyFavButton() {
    FloatingActionButton(onClick = {
    }) {
        Icon(Icons.Default.Add, contentDescription = null)
    }


}