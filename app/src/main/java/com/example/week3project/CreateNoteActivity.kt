package com.example.week3project

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.week3project.ui.theme.Week3ProjectTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.platform.LocalContext

class CreateNoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week3ProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting2(
                        name = "create note",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

var inputTitle: String = ""
var inputContent: String = ""

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    inputTitle = ""
    inputContent = ""


    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Create Note",
            modifier = modifier
        )
        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                inputTitle = it
            },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = content,
            onValueChange = {
                content = it
                inputContent = it
            },
            label = { Text("Content") },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // TODO: Save the note using title and content
//                onSave()

//    Log.d("NoteManager", "Name: " + noteInst.name);
            var tip: String = ""
            if (title == "" || content == "") {
                tip = "Title or content is empty";
            } else {
                val noteInst = NoteManager.getInstance()
                noteInst.SaveNote(context, title, content);

            }
            if (tip != "") {
                Toast.makeText(context, tip, Toast.LENGTH_SHORT)
                    .show()
            }
        }) {
            Text("Save Note")
        }
    }

}
 