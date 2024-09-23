package com.example.week3project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val timer = intent.getIntExtra("counter", 0)
            val name = intent.getStringExtra("user name")
            myFirstTable(timer, name.toString())
        }
    }

}

@Composable
fun myFirstTable(timer: Int, name: String) {
    val names = listOf(
        "Android",
        "Kotlin",
        "Java",
        "Android",
        "Kotlin",
        "Java",
        "Android",
        "Kotlin",
        "Java",
        "Android",
        "Kotlin",
        "Java",
    )
    val pResource = painterResource(R.drawable.pic)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text("Hello $name")
        LazyColumn {
            items(names)
            {
                Text("User Name $it")
                Image(
                    painter = pResource, contentDescription = "my image",
                    Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}