package com.example.week3project


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week3project.ui.theme.Week3ProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Counter()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "1233 $name!",
        modifier = modifier
    )
}

@Composable
fun Counter() {
    val context = LocalContext.current
    val counter = remember { mutableStateOf(0) }
    val pResource = painterResource(R.drawable.pic)
    val nameInput = remember {
        mutableStateOf("")
    }
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Gray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var txt = Text(text = "Hello ${counter.value}", fontSize = 30.sp);
        Row {
            Button(onClick = {
                counter.value += 1

            }) {
                Text(text = "button 1")
            }

            Button(onClick = {
                counter.value -= 1

                Log.d("Counter", "${counter.value}");
            }) {
                Text(text = "button 2")
            }


        }
    }
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Image(
            painter = pResource, contentDescription = "my image",
            Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Spacer(Modifier.height(10.dp))
        TextField(
            value = nameInput.value,
            onValueChange = {
                nameInput.value = it
            },

            )

//        FloatingActionButton(onClick = {
//            //navigate
//            val intent = Intent(context, SecondActivity::class.java)
//            intent.putExtra("counter", counter.value)
//            intent.putExtra("user name", nameInput.value)
//            context.startActivity(intent)
//        }) {
//            Text(text = "NEXT")
//        }

        FloatingActionButton(onClick = {
            //navigate
            val intent = Intent(context, CreateNoteActivity::class.java)

            context.startActivity(intent)
        }) {
            Text(text = "Create note")
        }

    }
}

