package com.sunhe.hesun_comp304lab2_ex1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sunhe.hesun_comp304lab2_ex1.data.DataManager
import com.sunhe.hesun_comp304lab2_ex1.data.TaskHSViewModel
import com.sunhe.hesun_comp304lab2_ex1.ui.theme.Hesun_COMP304Lab2_Ex1Theme
import kotlinx.coroutines.launch

// MainActivity class that serves as the entry point for the application
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class) // Opt-in to use experimental APIs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Call the superclass's onCreate method
        DataManager.getInstance().LoadFromFile(this) // Load data from file using DataManager
        val viewModel: TaskHSViewModel =
            DataManager.getInstance().getVM() // Get the ViewModel instance

        // Launch a coroutine to collect UI state from the ViewModel
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) { // Only collect when the lifecycle is started
                viewModel.uiState.collect {
                    enableEdgeToEdge() // Enable edge-to-edge display for the activity
                    setContent {
                        HomeScreenDisplay(viewModel) // Set the content of the activity to HomeScreenDisplay
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter") // Suppress warning for unused padding parameter
@OptIn(ExperimentalMaterial3Api::class) // Opt-in to use experimental APIs
@Composable
fun HomeScreenDisplay(viewModel: TaskHSViewModel) {
    val context = LocalContext.current // Get the current context
    Hesun_COMP304Lab2_Ex1Theme { // Apply the app's theme
        Scaffold(
            modifier = Modifier.safeDrawingPadding(), // Fill the inner padding of the available size
            topBar = { // Define the top app bar
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer, // Set the background color
                        titleContentColor = MaterialTheme.colorScheme.primary, // Set the title color
                    ),
                    title = {
                        Text("Home Activity") // Set the title text
                    }
                )
            },
            floatingActionButton = { // Define the floating action button
                FloatingActionButton(
                    modifier = Modifier.semantics {
                        contentDescription =
                            "Create Task" // Set the content description for accessibility
                    },
                    onClick = {
                        val intent = Intent(
                            context,
                            CreateTaskActivity::class.java
                        ) // Create an intent to start CreateTaskActivity
                        context.startActivity(intent) // Start the new activity
                    })
                {
                    Row() { // Define the layout for the button content
                        Text(text = "Create Task") // Text displayed in the button
                        Icon(
                            Icons.Default.Create, // Icon for the button
                            contentDescription = "Create Task" // Set the content description for the icon
                        )
                    }
                }
            },
        ) { innerPadding -> // Provide padding for the content inside the Scaffold
            com.sunhe.hesun_comp304lab2_ex1.views.TaskList(
                modifier = Modifier.padding(innerPadding), // Apply padding to TaskList
                viewModel // Pass the ViewModel to TaskList
            )
        }
    }
}
