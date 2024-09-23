package com.example.week3project

import android.content.Intent
import android.os.Bundle
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

class EditNoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val noteIndex = intent.getIntExtra("noteIndex", 0)
            Week3ProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RefreshEditNote(
                        noteIndex,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun RefreshEditNote(idx: Int, modifier: Modifier = Modifier) {

    var curNote = NoteManager.getInstance().getNote(idx)
    val context = LocalContext.current
    var title by remember { mutableStateOf(curNote.title) }
    var content by remember { mutableStateOf(curNote.content) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Edit Note",
            modifier = modifier
        )
        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
            },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = content,
            onValueChange = {
                content = it
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
                curNote.title = title
                curNote.content = content
                val noteInst = NoteManager.getInstance()
                noteInst.EditNote(context, title, content, idx);

            }
            if (tip != "") {
                Toast.makeText(context, tip, Toast.LENGTH_SHORT).show()
            } else {
                //  Navigate back to the main screen
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }
        }) {
            Text("Save and back")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            //  Navigate back to the main screen
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }) {
            Text(text = "Discard and back")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            NoteManager.getInstance().DeleteNote(context, idx)
            //  Navigate back to the main screen
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }) {
            Text(text = "Delete and back")
        }
    }
}
