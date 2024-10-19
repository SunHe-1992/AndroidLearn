package com.sunhe.hesun_comp304lab2_ex1.views

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.sunhe.hesun_comp304lab2_ex1.R
import com.sunhe.hesun_comp304lab2_ex1.data.DataManager
import com.sunhe.hesun_comp304lab2_ex1.data.TaskHS
import com.sunhe.hesun_comp304lab2_ex1.data.TaskHSViewModel

/**
 * Composable function to display a list of tasks.
 *
 * @param modifier Modifier for the TaskList layout.
 * @param viewModel The TaskHSViewModel instance to access task data.
 */
@Composable
fun TaskList(modifier: Modifier, viewModel: TaskHSViewModel) {
    val context = LocalContext.current
    // Get the UI state from the ViewModel using collectAsState.
    val uiState by viewModel.uiState.collectAsState()

    // LazyColumn to display the list of tasks efficiently.
    LazyColumn(
        modifier = modifier
    ) {
        items(uiState) { task ->
            // Row to display each task item with spacing.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                // TaskCard composable to display individual task details.
                TaskCard(
                    context,
                    task,
                    // Callback when a task is clicked, navigating to EditActivity.
                    onNoteClick = {
                        val intent = Intent(context, EditActivity::class.java)
                        intent.putExtra("taskId", task.id)
                        context.startActivity(intent)
                    },
                )
            }
        }
    }
}

/**
 * Composable function to display a single task card.
 *
 * @param context The application context.
 * @param _task The initial task data.
 * @param onNoteClick Callback when the task card is clicked.
 */
@Composable
fun TaskCard(context: Context, _task: TaskHS, onNoteClick: (TaskHS) -> Unit) {
    val viewModel: TaskHSViewModel = DataManager.getInstance().getVM()
    val task = viewModel.getTask(_task.id)
    if (task == null) return // Handle case where task is not found.

    // State to manage the checked state of the checkbox.
    var checkedState by remember { mutableStateOf(task.done) }
    checkedState = task.done

    // Card composable to display the task details within a card layout.
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onNoteClick(task) },
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            // Checkbox to indicate task completion status.
            Checkbox(
                checked = checkedState,
                onCheckedChange = {
                    viewModel.updateTaskDone(context, task.id, it)
                    checkedState = it
                    doButtonEffect(context) // Trigger sound and vibration.
                }
            )
            // Text to display the task title.
            Text(task.title, style = MaterialTheme.typography.headlineSmall)
        }
    }
}

/**
 * Function to trigger sound and vibration effects.
 *
 * @param context The application context.
 * @suppress DEPRECATION Suppresses deprecation warnings for older vibration APIs.
 */
@Suppress("DEPRECATION")
private fun doButtonEffect(context: Context) {
    // Get the Vibrator service for haptic feedback.
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    // Vibrate the device for a short duration.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(50)
    }

    // Play a sound effect using MediaPlayer.
    val mediaPlayer = MediaPlayer.create(context, R.raw.sound_task)
    mediaPlayer.start()
}