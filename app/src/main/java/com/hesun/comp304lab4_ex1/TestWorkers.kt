package com.hesun.comp304lab4_ex1

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import kotlin.random.Random

/**
 * A custom background worker class using Android WorkManager
 * This class performs a simple task of generating a random number,
 * creating a string, and calculating its MD5 hash
 *
 * @param appContext The application context
 * @param param WorkManager parameters for the background task
 */
class TestWorker(
    private val appContext: Context,
    private val param: WorkerParameters
) : CoroutineWorker(appContext, param) {

    /**
     * Main background work method executed by WorkManager
     *
     * @return Result indicating success or failure of the background task
     */
    override suspend fun doWork(): Result {
        // Counter to track task completion (though only used once in this implementation)
        var done = 0
        done++

        // Generate a random integer between 1 and 100
        val randomNumber = Random.nextInt(1, 101)

        // Create an original string by concatenating a fixed string with the random number
        val originalString = "HeSun" + randomNumber

        // Calculate MD5 hash of the original string
        val md5Hash = originalString.md5()

        // Prepare a log and toast message with the original string and its MD5 hash
        var toastStr = "WorkManager " + originalString + "  md5:" + md5Hash

        // Log the message to logcat for debugging purposes
        Log.i("LogOwnersWorkers", toastStr)

        // Show a toast message on the main thread to provide user feedback
        withContext(Dispatchers.Main) {
            Toast.makeText(applicationContext, toastStr, Toast.LENGTH_SHORT).show()
        }

        // Return success if the task counter is 1, otherwise return failure
        // Note: This logic will always return success in the current implementation
        if (done == 1) {
            return Result.success()
        } else
            return Result.failure()
    }

    /**
     * Extension function to calculate MD5 hash of a string
     *
     * @return MD5 hash of the string as a hexadecimal string
     */
    fun String.md5(): String {
        // Use MessageDigest to generate MD5 hash
        return MessageDigest.getInstance("MD5")
            .digest(toByteArray())
            // Convert the byte array to a hexadecimal string
            .joinToString("") { "%02x".format(it) }
    }
}