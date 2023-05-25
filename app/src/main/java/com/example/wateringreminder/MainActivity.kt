package com.example.wateringreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.wateringreminder.ui.theme.WateringReminderTheme
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleNewDayTask()
        setContent {
            WateringReminderTheme {
                MainScreen()
            }
        }
    }

    private fun scheduleNewDayTask() {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 14)
        calendar.set(Calendar.MINUTE, 33)
        calendar.set(Calendar.SECOND, 0)

        val currentTime = calendar.timeInMillis
        val timeDiff = currentTime - System.currentTimeMillis()

        val showMsgWorkRequest = OneTimeWorkRequestBuilder<UpdateEventWorker>()
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(this).enqueue(showMsgWorkRequest)
    }
}