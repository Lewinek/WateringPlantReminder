package com.example.wateringreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
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
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()

        dueDate.set(Calendar.HOUR_OF_DAY, 18)
        dueDate.set(Calendar.MINUTE, 51)
        dueDate.set(Calendar.SECOND, 0)
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }

        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiff)

        val refreshCpnWork =
            PeriodicWorkRequest.Builder(UpdateEventWorker::class.java, 1, TimeUnit.DAYS)
                .setInitialDelay(minutes, TimeUnit.MINUTES)
                .addTag("myWorkManager")
                .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "myWorkManager",
                ExistingPeriodicWorkPolicy.UPDATE,
                refreshCpnWork
            )
    }
}