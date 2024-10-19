package com.sunhe.hesun_comp304lab2_ex1

import android.annotation.SuppressLint
import android.os.Bundle
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


/**
 * Activity for creating a new task.
 */
class CreateTaskActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enable edge-to-edge display for a modern look.
        enableEdgeToEdge()
        setContent {
            // Apply the app's theme.
            Hesun_COMP304Lab2_Ex1Theme {
                // Use Scaffold to provide a basic layout structure.
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // RefreshCreateTask composable for creating a new task.
                    RefreshCreateTask(
                        onFinish = { finish() }, // Callback to finish the activity.
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/**
 * Composable function for creating a new task.
 *
 * @param onFinish Callback to be executed when the task creation is finished.
 * @param modifier Modifier for the RefreshCreateTask layout.
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RefreshCreateTask(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    // State variables for task title and content.
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    // Get the TaskHSViewModel instance.
    val viewModel: TaskHSViewModel = DataManager.getInstance().getVM()

    // Apply the app's theme.
    Hesun_COMP304Lab2_Ex1Theme {
        // Use Scaffold to provide a basic layout structure.
        Scaffold(
            modifier = Modifier.safeDrawingPadding(), // Fill the inner padding of the available size
            topBar = {
                // TopAppBar for the screen title and navigation.
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        // Text to display the screen title.
                        Text("Create Task")
                    },
                    navigationIcon = {
                        // IconButton for the back navigation.
                        IconButton(onClick = onFinish) {
                            // Icon to display the back arrow.
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                    },
                )
            },
            floatingActionButton = {
                // FloatingActionButton to save the new task.
                FloatingActionButton(
                    modifier = Modifier.semantics {
                        contentDescription = "Create Task Button"
                    },
                    onClick = {
                        // Check if title or content is empty.
                        var tip: String = ""
                        if (title.trim() == "" || content.trim() == "") {
                            tip = "Title or content is empty"
                        } else {
                            // Save the task data using the ViewModel.
                            viewModel.createTask(context, title, content)
                            // Finish the activity and navigate back.
                            onFinish()
                        }
                        // Display a toast message if title or content is empty.
                        if (tip != "") {
                            Toast.makeText(context, tip, Toast.LENGTH_SHORT).show()
                        } else {
                            // Finish the activity and navigate back.
                            onFinish()
                        }
                    },
                ) {
                    // Row to display the save task icon and text.
                    Row() {
                        Text("Save Task")
                        Icon(Icons.Default.Done, contentDescription = "Save Task")
                    }
                }
            },
        ) { innerPadding ->
            // Column to arrange the input fields.
            Column(
                modifier = Modifier
                    .safeDrawingPadding()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                // OutlinedTextField for entering the task title.
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.safeDrawingPadding(),
                )
                // Spacer to add vertical space between elements.
                Spacer(modifier = Modifier.height(16.dp))
                // OutlinedTextField for entering the task content.
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") },
                    modifier = Modifier
                        .safeDrawingPadding()
                        .height(200.dp),
                )
            }
        }
    }
}