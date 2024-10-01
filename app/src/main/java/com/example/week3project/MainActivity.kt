package com.example.week3project


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NoteManager.getInstance().LoadFromFile(this)
        enableEdgeToEdge()
        setContent {
            HomeScreenDisplay()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i("note", "onResume")
        enableEdgeToEdge()
        setContent {
            HomeScreenDisplay()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenDisplay() {
    val context = LocalContext.current
    val counter = remember { mutableStateOf(0) }

    var notesData =
        NoteManager.getInstance().getNotes()
    var noteState = remember { mutableStateListOf<Note>() }
    noteState.addAll(notesData)

    fun updateNote() {
        notesData =
            NoteManager.getInstance().getNotes()
        noteState.clear()
        noteState.addAll(notesData)
    }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L) // delay 1 second
            // update the data
            updateNote()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Main Activity ${counter.value}")
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val intent = Intent(context, CreateNoteActivity::class.java)
                    context.startActivity(intent)
                    //test code
//                    counter.value++
                })
            {
                Row() {
                    Text(text = "Create Note")
                    Icon(Icons.Default.Create, contentDescription = "Create Note")
                }
            }
        }
    ) { innerPadding ->

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(noteState) { note ->
                NoteCard(note, onNoteClick = {
                    val intent = Intent(context, EditNoteActivity::class.java)
                    intent.putExtra("noteIndex", note.index)
                    context.startActivity(intent)
                    Log.d("note", "click index" + note.index)
                })
            }
        }
        FloatingActionButton(onClick = {
            //navigate
            NoteManager.getInstance().deleteAll()
            notesData.clear()
        }) {
            Text(text = "Delete all notes")
        }
    }
}

@Composable
fun NoteCard(note: Note, onNoteClick: (Note) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onNoteClick(note) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(note.title, style = MaterialTheme.typography.headlineSmall)
            Text(
                note.content.take(50) + if (note.content.length > 50) "..." else "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
