package com.sunhe.hesun_comp304lab2_ex1.data

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import com.google.gson.Gson

class DataManager {

    var vm: TaskHSViewModel = TaskHSViewModel()

    companion object {
        private var instance: DataManager? = null

        fun getInstance(): DataManager {
            if (instance == null) {
                instance = DataManager()
            }
            return instance!!
        }
    }

    fun getVM(): TaskHSViewModel {
        if (vm == null)
            vm = TaskHSViewModel()
        return vm

    }

    private val fileName = "user_tasks.txt"
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

    fun LoadFromFile(context: Context) {

        val file = File(context!!.filesDir, fileName)
        if (!file.exists()) {

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