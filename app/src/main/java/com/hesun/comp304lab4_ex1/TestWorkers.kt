package com.hesun.comp304lab4_ex1


import android.content.Context
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import kotlin.random.Random


class TestWorker(
    private val appContext: Context,
    private val param: WorkerParameters
) :
    CoroutineWorker(appContext, param) {

    override suspend fun doWork(): Result {
        var done = 0;
        done++;
        val randomNumber = Random.nextInt(1, 101)
        val originalString = "HeSun" + randomNumber
        val md5Hash = originalString.md5()
        var toastStr = "WorkManager " + originalString + "  md5:" + md5Hash
        Log.i("LogOwnersWorkers", toastStr)
        withContext(Dispatchers.Main) {
            Toast.makeText(applicationContext, toastStr, Toast.LENGTH_SHORT).show()
        }
        if (done == 1) {
            return Result.success()
        } else
            return Result.failure()
    }

    fun String.md5(): String {
        return MessageDigest.getInstance("MD5")
            .digest(toByteArray())
            .joinToString("") { "%02x".format(it) }
    }


}