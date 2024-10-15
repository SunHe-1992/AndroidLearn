package com.sunhe.hesun_comp304lab2_ex1.data

import androidx.activity.viewModels

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
}