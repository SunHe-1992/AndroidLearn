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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sunhe.hesun_comp304lab2_ex1.EditActivity
import com.sunhe.hesun_comp304lab2_ex1.data.DataManager
import com.sunhe.hesun_comp304lab2_ex1.data.TaskHS
import com.sunhe.hesun_comp304lab2_ex1.data.TaskHSViewModel

@Composable
fun TaskList(modifier: Modifier, viewModel: TaskHSViewModel) {
    val context = LocalContext.current
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
                    task,
                    onNoteClick = {
                        val intent = Intent(context, EditActivity::class.java)
                        intent.putExtra("taskId", task.id)
                        context.startActivity(intent)
//                        Log.d("task", "click id" + task.id)
                    },
                )

            }
        }
    }
}

@Composable
fun TaskCard(_task: TaskHS, onNoteClick: (TaskHS) -> Unit) {
    val viewModel: TaskHSViewModel = DataManager.getInstance().getVM()
    val task = viewModel.getTask(_task.id)
    if (task == null) return
    var checkedState by remember { mutableStateOf(task.done) }
    checkedState = task.done
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onNoteClick(task) }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = {
                    viewModel.updateTaskDone(task.id, it)
                    checkedState = it
                }
            )
            Text(task.title, style = MaterialTheme.typography.headlineSmall)
//            Text("Done: ${task.done}")
        }
    }
}
