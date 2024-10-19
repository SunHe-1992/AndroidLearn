package com.sunhe.hesun_comp304lab2_ex1.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Data class representing a task.
 *
 * @property id The unique identifier of the task.
 * @property title The title of the task.
 * @property content The detailed content of the task.
 * @property done A boolean indicating whether the task is completed.
 */
data class TaskHS(
    val id: Int,
    val title: String,
    val content: String,
    val done: Boolean,
)

/**
 * ViewModel class for managing the UI state of the task list.
 *
 * This ViewModel handles loading, updating, and displaying task data.
 */
class TaskHSViewModel : ViewModel() {
    /**
     * Private mutable state flow for the list of tasks.
     */
    private val _uiState = MutableStateFlow<List<TaskHS>>(emptyList())

    /**
     * Public read-only state flow for the list of tasks, exposed to the UI.
     */
    val uiState: StateFlow<List<TaskHS>> = _uiState.asStateFlow()

    /**
     * Loads initial task data or dummy data if none exists.
     */
    fun showTask() {
        if (_uiState.value == null || _uiState.value.count() == 0) {
            // For development: Show dummy data if no data is loaded.
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

    /**
     * Generates the next unique ID for a new task.
     *
     * @return The next available task ID.
     */
    fun getNextId(): Int {
        return _uiState.value.count() + 1
    }

    /**
     * Updates the completion status of a task.
     *
     * @param context The application context.
     * @param taskId The ID of the task to update.
     * @param isDone The new completion status of the task.
     */
    fun updateTaskDone(context: Context, taskId: Int, isDone: Boolean) {
        _uiState.update { currentList ->
            currentList.map { task ->
                if (task.id == taskId) {
                    task.copy(done = isDone)
                } else {
                    task
                }
            }
        }
        // Save to file
        DataManager.getInstance().SaveToFile(context)
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param taskId The ID of the task to retrieve.
     * @return The task with the matching ID, or null if not found.
     */
    fun getTask(taskId: Int): TaskHS? {
        val cnt = _uiState.value.count()
        Log.d("TaskHSViewModel", "getTask count: $cnt")
        return _uiState.value.find { it.id == taskId }
    }

    /**
     * Updates the title and content of a task.
     *
     * @param context The application context.
     * @param taskId The ID of the task to update.
     * @param newTitle The new title of the task.
     * @param newContent The new content of the task.
     */
    fun updateTask(context: Context, taskId: Int, newTitle: String, newContent: String) {
        _uiState.update { currentList ->
            currentList.map { task ->
                if (task.id == taskId) {
                    task.copy(title = newTitle, content = newContent)
                } else {
                    task
                }
            }
        }
        // Save to file
        DataManager.getInstance().SaveToFile(context)
    }

    /**
     * Creates a new task and adds it to the task list.
     *
     * @param context The application context.
     * @param newTitle The title of the new task.
     * @param newContent The content of the new task.
     */
    fun createTask(context: Context, newTitle: String, newContent: String) {
        _uiState.update { currentList ->
            currentList + TaskHS(getNextId(), newTitle, newContent, false)
        }
        // Save to file
        DataManager.getInstance().SaveToFile(context)
    }

    /**
     * Gets the current list of tasks.
     *
     * @return A mutable list of tasks.
     */
    fun getTaskList(): MutableList<TaskHS> {
        return _uiState.value.toMutableList()
    }

    /**
     * Loads a list of tasks from an array.
     *
     * @param array The array of tasks to load.
     */
    fun loadTaskList(array: Array<TaskHS>) {
        _uiState.value = array.toList()
    }
}