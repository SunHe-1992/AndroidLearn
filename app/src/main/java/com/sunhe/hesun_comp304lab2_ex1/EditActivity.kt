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
import androidx.compose.foundation.layout.safeDrawingPadding
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

// Activity for editing tasks
class EditActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Call the superclass's onCreate method
        val taskId = intent.getIntExtra("taskId", 0) // Retrieve the task ID from the intent
        enableEdgeToEdge() // Enable edge-to-edge layout for the activity
        setContent {
            Hesun_COMP304Lab2_Ex1Theme { // Apply the app's theme
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding -> // Set up the Scaffold layout
                    RefreshEditTask(
                        taskId, // Pass the task ID to the composable
                        onFinish = { finish() }, // Pass the callback to finish the activity
                        modifier = Modifier.padding(innerPadding) // Apply padding to the inner content
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) // Opt-in to experimental Material 3 APIs
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter") // Suppress warning for unused padding parameter
@Composable
fun RefreshEditTask(
    taskId: Int, // The ID of the task being edited
    onFinish: () -> Unit, // Callback to finish the activity
    modifier: Modifier = Modifier // Modifier for layout customization
) {
    // Get the ViewModel from DataManager
    val viewModel: TaskHSViewModel = DataManager.getInstance().getVM()
    // Retrieve the task from the ViewModel using the provided task ID
    val task = viewModel.getTask(taskId)

    // Log an error if the task is null
    if (task == null) {
        Log.d("EditActivity", "task is null id = $taskId")
        return // Exit if no task is found
    }

    val context = LocalContext.current // Get the current context
    // State variables for title, content, and checkbox state
    var title by remember { mutableStateOf(task.title) }
    var content by remember { mutableStateOf(task.content) }
    var checkedState by remember { mutableStateOf(task.done) }

    // Apply the app's theme for the UI
    Hesun_COMP304Lab2_Ex1Theme {
        Scaffold(
            modifier = Modifier.safeDrawingPadding(), // Fill the inner padding of the available size
            topBar = {
                TopAppBar( // Create a top app bar
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer, // Background color
                        titleContentColor = MaterialTheme.colorScheme.primary, // Title text color
                    ),
                    title = {
                        Text("Edit Task") // Title of the app bar
                    },
                    navigationIcon = { // Back button in the app bar
                        IconButton(onClick = onFinish) { // Callback to finish the activity
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back" // Content description for accessibility
                            )
                        }
                    }
                )
            },
            floatingActionButton = { // Floating action button for saving the task
                FloatingActionButton(
                    modifier = Modifier.semantics {
                        contentDescription =
                            "Edit Task Button" // Content description for accessibility
                    },
                    onClick = {
                        // Validate title and content before saving
                        var tip: String = ""
                        if (title.trim() == "" || content.trim() == "") {
                            tip = "Title or content is empty" // Error message if validation fails
                        } else {
                            // Save the updated task
                            viewModel.updateTask(context, taskId, title, content)
                        }
                        // Show toast message for validation feedback
                        if (tip != "") {
                            Toast.makeText(context, tip, Toast.LENGTH_SHORT).show()
                        } else {
                            // Navigate back to the previous screen
                            onFinish()
                        }
                    }) {
                    // Content of the floating action button
                    Row() {
                        Text("Save Task") // Button text
                        Icon(Icons.Default.Done, contentDescription = "Save Task") // Save icon
                    }
                }
            }
        ) { innerPadding -> // Padding for the inner content
            Column(
                modifier = Modifier
                    .safeDrawingPadding()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally, // Center contents horizontally
                verticalArrangement = Arrangement.Top // Align contents to the top
            ) {
                Row() { // Row for checkbox and title input
                    Checkbox(
                        checked = checkedState, // Checkbox state
                        onCheckedChange = {
                            // Update task's completion state
                            viewModel.updateTaskDone(context, task.id, it)
                            checkedState = it // Update local checkbox state
                            doButtonEffect(context) // Trigger button effect (vibration/sound)
                        }
                    )
                    OutlinedTextField(
                        value = title, // Current value of the title input
                        onValueChange = {
                            title = it // Update title state when user types
                        },
                        label = { Text("Title") }, // Label for the input field
                        modifier = Modifier.safeDrawingPadding() // Make input field fill width
                    )
                }
                Spacer(modifier = Modifier.height(16.dp)) // Space between inputs
                OutlinedTextField(
                    value = content, // Current value of the content input
                    onValueChange = {
                        content = it // Update content state when user types
                    },
                    label = { Text("Content") }, // Label for the input field
                    modifier = Modifier
                        .safeDrawingPadding() // Make input field fill width
                        .height(200.dp) // Set fixed height for content input
                )
            }
        }
    }
}

// Function to handle button effects like vibration and sound
@Suppress("DEPRECATION") // Suppress warnings for deprecated API usage
private fun doButtonEffect(context: Context) {
    // Get the Vibrator service for haptic feedback
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator // Use the new VibratorManager for API 31+
    } else {
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator // Fallback for older versions
    }

    // Vibrate the device for a short duration
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION") // Suppress deprecation warning for older API
        vibrator.vibrate(50) // Fallback for pre-Oreo devices
    }

    // Play a sound effect when the button is pressed
    val mediaPlayer = MediaPlayer.create(context, R.raw.sound_task) // Create MediaPlayer instance
    mediaPlayer.start() // Start playback
}
