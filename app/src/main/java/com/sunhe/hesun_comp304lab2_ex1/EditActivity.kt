package com.sunhe.hesun_comp304lab2_ex1

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.sunhe.hesun_comp304lab2_ex1.data.DataManager
import com.sunhe.hesun_comp304lab2_ex1.data.TaskHSViewModel
import com.sunhe.hesun_comp304lab2_ex1.ui.theme.Hesun_COMP304Lab2_Ex1Theme


class EditActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val taskId = intent.getIntExtra("taskId", 0)
        enableEdgeToEdge()
        setContent {
            Hesun_COMP304Lab2_Ex1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RefreshEditTask(
                        taskId,
                        onFinish = { finish() }, // Pass the callback here
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RefreshEditTask(
    taskId: Int,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: TaskHSViewModel = DataManager.getInstance().getVM()
    val task = viewModel.getTask(taskId)
    if (task == null) {
        Log.d("EditActivity", "task is null id = $taskId")
        return
    }
    val context = LocalContext.current
    var title by remember { mutableStateOf(task.title) }
    var content by remember { mutableStateOf(task.content) }
    var checkedState by remember { mutableStateOf(task.done) }
    Hesun_COMP304Lab2_Ex1Theme {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Edit Task")
                    },
                    navigationIcon = {
                        IconButton(onClick = onFinish) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            },
            floatingActionButton = {

                FloatingActionButton(
                    modifier = Modifier.semantics {
                        contentDescription = "Edit Task Button"
                    },

                    onClick = {
                        //  Save the task using title and content
                        var tip: String = ""
                        if (title.trim() == "" || content.trim() == "") {
                            tip = "Title or content is empty";
                        } else {
                            //save data
                            viewModel.updateTask(context, taskId, title, content)
                        }
                        if (tip != "") {
                            Toast.makeText(context, tip, Toast.LENGTH_SHORT).show()
                        } else {//  Navigate back to the main screen
                            onFinish()
                        }

                    }) {
                    Row() {
                        Text("Save Task")
                        Icon(Icons.Default.Done, contentDescription = "Save Task")
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Text(
                    text = "Edit Task",
                    modifier = modifier
                )
                Row() {
                    Checkbox(
                        checked = checkedState,
                        onCheckedChange = {
                            viewModel.updateTaskDone(context, task.id, it)
                            checkedState = it
                            doButtonEffect(context)
                        }
                    )
                    OutlinedTextField(
                        value = title,
                        onValueChange = {
                            title = it
                        },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
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


            }
        }
    }
}

@Suppress("DEPRECATION")
private fun doButtonEffect(context: Context) {
    // Get the Vibrator service
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }
    // Vibrate the device
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(50)
    }

    //play sound
    val mediaPlayer = MediaPlayer.create(context, R.raw.sound_task)
    mediaPlayer.start()

}