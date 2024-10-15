package com.sunhe.hesun_comp304lab2_ex1.data

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class TaskHS(
    val id: Int,
    val title: String,
    val content: String,
    val done: Boolean,
)

class TaskHSViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<List<TaskHS>>(emptyList())
    val uiState: StateFlow<List<TaskHS>> = _uiState.asStateFlow()

    // Handle business logic
    fun showTask() {
        val tasks = listOf(
            TaskHS(1, "Task 1", "Content 1", false),
            TaskHS(2, "Task 2", "Content 2", true),
            TaskHS(3, "Task 3", "Content 3", false),
            TaskHS(4, "Task 4", "Content 4", true),
            TaskHS(5, "Task 5", "Content 5", false)
        )
        _uiState.value = tasks
    }

    fun updateTaskDone(taskId: Int, isDone: Boolean) {

        _uiState.update { currentList ->
            currentList.map { task ->
                if (task.id == taskId) {
                    Log.d("TaskHSViewModel", "Task with ID $taskId updated to done: $isDone")
                    task.copy(done = isDone)
                } else {
                    task
                }
            }
        }
    }
}