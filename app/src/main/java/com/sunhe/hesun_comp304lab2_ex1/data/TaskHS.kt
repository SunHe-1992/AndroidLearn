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
        if (_uiState.value == null || _uiState.value.count() == 0) {
            //for dev: show dummy data if no data
            val tasks = listOf(
                TaskHS(1, "Task 1", "Content 1", false),
                TaskHS(2, "Task 2", "Content 2", true),
                TaskHS(3, "Task 3", "Content 3", false),
                TaskHS(4, "Task 4", "Content 4", true),
                TaskHS(5, "Task 5", "Content 5", false)
            )
            _uiState.value = tasks
        }
    }

    fun getNextId(): Int {
        return _uiState.value.count() + 1
    }

    fun updateTaskDone(taskId: Int, isDone: Boolean) {

        _uiState.update { currentList ->
            currentList.map { task ->
                if (task.id == taskId) {
                    task.copy(done = isDone)
                } else {
                    task
                }
            }
        }
    }

    fun getTask(taskId: Int): TaskHS? {
        val cnt = _uiState.value.count()
        Log.d("TaskHSViewModel", "getTask count: $cnt")
        return _uiState.value.find { it.id == taskId }
    }

    fun updateTask(taskId: Int, newTitle: String, newContent: String) {
        _uiState.update { currentList ->
            currentList.map { task ->
                if (task.id == taskId) {
                    task.copy(title = newTitle, content = newContent)
                } else {
                    task
                }
            }
        }
    }

    fun createTask(newTitle: String, newContent: String) {
        _uiState.update { currentList ->
            currentList + TaskHS(getNextId(), newTitle, newContent, false)
        }
    }
}