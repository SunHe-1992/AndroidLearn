package com.sunhe.hesun_comp304lab2_ex1.views


import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sunhe.hesun_comp304lab2_ex1.data.TaskHS
import com.sunhe.hesun_comp304lab2_ex1.data.TaskHSViewModel

@Composable
fun TaskList(modifier: Modifier, viewModel: TaskHSViewModel) {
    viewModel.showTask()
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = modifier
    ) {
        items(uiState) { task ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TaskCard(
                    task, onNoteClick = {
//                    val intent = Intent(context, EditNoteActivity::class.java)
//                    intent.putExtra("noteIndex", note.index)
//                    context.startActivity(intent)
                        Log.d("task", "click id" + task.id)
                        Log.d("task", "is done" + task.done)

                    },
                    viewModel
                )

            }
        }
    }
}

@Composable
fun TaskCard(task: TaskHS, onNoteClick: (TaskHS) -> Unit, viewModel: TaskHSViewModel) {
    var isChecked by remember { mutableStateOf(task.done) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onNoteClick(task) }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    viewModel.updateTaskDone(task.id, it)

                    Log.d("task", "is done" + task.done)
                }
            )
            Text(task.title, style = MaterialTheme.typography.headlineSmall)
//            Text("Done: ${task.done}")
        }
    }
}
