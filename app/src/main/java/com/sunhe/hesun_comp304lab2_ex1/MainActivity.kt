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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sunhe.hesun_comp304lab2_ex1.data.DataManager
import com.sunhe.hesun_comp304lab2_ex1.data.TaskHSViewModel
import com.sunhe.hesun_comp304lab2_ex1.ui.theme.Hesun_COMP304Lab2_Ex1Theme
import kotlinx.coroutines.launch

//Home Activity
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataManager.getInstance().LoadFromFile(this)
        val viewModel: TaskHSViewModel = DataManager.getInstance().getVM()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    enableEdgeToEdge()
                    setContent {
                        HomeScreenDisplay(viewModel)
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenDisplay(viewModel: TaskHSViewModel) {
    val context = LocalContext.current
    Hesun_COMP304Lab2_Ex1Theme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Home Activity")
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.semantics {
                        contentDescription = "Create Task"
                    },
                    onClick = {
                        val intent = Intent(context, CreateTaskActivity::class.java)
                        context.startActivity(intent)
                    })
                {
                    Row() {
                        Text(text = "Create Task")
                        Icon(
                            Icons.Default.Create,
                            contentDescription = "Create Task"
                        )
                    }
                }
            },
        ) { innerPadding ->
            com.sunhe.hesun_comp304lab2_ex1.views.TaskList(
                modifier = Modifier.padding(innerPadding),
                viewModel
            )
        }
    }
}

