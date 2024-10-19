package com.sunhe.hesun_comp304lab2_ex1.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream

/**
 * DataManager class responsible for managing and persisting task data.
 * It handles saving and loading tasks to/from a file using Gson for serialization.
 */
class DataManager {

    /**
     * Instance of TaskHSViewModel to access and manage task data.
     */
    var vm: TaskHSViewModel = TaskHSViewModel()

    companion object {
        /**
         * Singleton instance of DataManager.
         */
        private var instance: DataManager? = null

        /**
         * Gets the singleton instance of DataManager.
         * @return The DataManager instance.
         */
        fun getInstance(): DataManager {
            if (instance == null) {
                instance = DataManager()
            }
            return instance!!
        }
    }

    /**
     * Gets the TaskHSViewModel instance.
     * @return The TaskHSViewModel instance.
     */
    fun getVM(): TaskHSViewModel {
        if (vm == null)
            vm = TaskHSViewModel()
        return vm

    }

    /**
     * The name of the file used to store task data.
     */
    private val fileName = "user_tasks.txt"

    /**
     * Saves the current task list to a file.
     * @param context The application context.
     */
    fun SaveToFile(context: Context) {
        val gson = Gson()

        var dataList = getVM().getTaskList();
        val jsonString = gson.toJson(dataList)
        Log.d("DataManager", "SaveToFile" + jsonString);
        val file = File(context.filesDir, fileName)
        if (!file.exists()) {
            file.createNewFile()
        }
        val data = jsonString
        val fos = FileOutputStream(file)
        fos.write(data.toByteArray())
        fos.close()
    }

    /**
     * Loads the task list from a file.
     * @param context The application context.
     */
    fun LoadFromFile(context: Context) {

        val file = File(context!!.filesDir, fileName)
        if (!file.exists()) {
            // File does not exist, no action needed.
        } else {
            var allText = file.readText()
            Log.d("DataManager", "LoadFromFile:" + allText);
            //all text to json:
            val gson = Gson()
            val taskArray: Array<TaskHS> = gson.fromJson(allText, Array<TaskHS>::class.java)
            getVM().loadTaskList(taskArray)
        }
    }
}