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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sunhe.hesun_comp304lab2_ex1.data.DataManager
import com.sunhe.hesun_comp304lab2_ex1.data.TaskHSViewModel
import com.sunhe.hesun_comp304lab2_ex1.ui.theme.Hesun_COMP304Lab2_Ex1Theme

class CreateTaskActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Hesun_COMP304Lab2_Ex1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RefreshCreateTask(

                        onFinish = { finish() }, // Pass the callback here
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RefreshCreateTask(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val viewModel: TaskHSViewModel = DataManager.getInstance().getVM()
    Scaffold(
        floatingActionButton = {

            FloatingActionButton(onClick = {
                //  Save the task using title and content
                var tip: String = ""
                if (title.trim() == "" || content.trim() == "") {
                    tip = "Title or content is empty";
                } else {
                    //save data
                    viewModel.createTask(title, content)
                    onFinish()
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
                text = "Create Task",
                modifier = modifier
            )
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
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
