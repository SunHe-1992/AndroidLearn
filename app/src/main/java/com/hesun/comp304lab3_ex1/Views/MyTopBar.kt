package com.hesun.comp304lab3_ex1.Views


import android.app.SearchManager
import android.content.Intent
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(){
    val showMenu = remember { mutableStateOf(false) }
    var cnx = LocalContext.current
    val nameInput = remember { mutableStateOf("") }

    TopAppBar(
        title = { Text(text = "He Sun Weather") } ,
        navigationIcon = {
            IconButton(onClick = {
                Toast.makeText(cnx, "It is my First Menu", Toast.LENGTH_LONG).show()
            }) {
                Icon(Icons.Default.Menu, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = {
                Toast.makeText(cnx, "This is the setting", Toast.LENGTH_LONG).show()
            }) {
                Icon(Icons.Default.Settings, contentDescription = null)
            }
            IconButton(onClick = {
                var intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                    putExtra(SearchManager.QUERY, "iphone 16" )
                }
                cnx.startActivity(intent)

            }) {
                Icon(Icons.Default.Search, contentDescription = null)
            }
            IconButton(onClick = {
                // show the menu
                showMenu.value = true
            }) {
                Icon(Icons.Default.MoreVert, contentDescription = null)
            }

        })
}




