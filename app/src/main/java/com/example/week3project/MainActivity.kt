package com.example.week3project


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NoteManager.getInstance().LoadFromFile(this)
        enableEdgeToEdge()
        setContent {
            HomeScreenDisplay()
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
fun HomeScreenDisplay() {
    val context = LocalContext.current
    val counter = remember { mutableStateOf(0) }
    val pResource = painterResource(R.drawable.pic)
    val nameInput = remember {
        mutableStateOf("")
    }

//        FloatingActionButton(onClick = {
//            //navigate
//            val intent = Intent(context, SecondActivity::class.java)
//            intent.putExtra("counter", counter.value)
//            intent.putExtra("user name", nameInput.value)
//            context.startActivity(intent)
//        }) {
//            Text(text = "NEXT")
//        }
    val notesData =
        NoteManager.getInstance().getNotes() // Replace with your actual note retrieval logic
    var notes by remember { mutableStateOf(mutableListOf<Note>()) }
    notes.addAll(notesData)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
    ) {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(notes) { note ->
                NoteCard(note, onNoteClick = {
                    val intent = Intent(context, EditNoteActivity::class.java)
                    intent.putExtra("noteIndex", note.index)
                    context.startActivity(intent)
                    Log.d("note", "click index" + note.index)
                })
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
//        FloatingActionButton(onClick = {
//            //navigate
//            NoteManager.getInstance().deleteAll()
//            notesData.clear()
//            notes.clear()
//        }) {
//            Text(text = "Delete all notes")
//        }
        Spacer(modifier = Modifier.height(16.dp))

        FloatingActionButton(onClick = {
            //navigate
            val intent = Intent(context, CreateNoteActivity::class.java)
            context.startActivity(intent)
        }) {
            Text(text = "Create note")
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
