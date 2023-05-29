package com.example.wateringreminder

import android.content.Context
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.*
import java.util.concurrent.TimeUnit

class ScheduleNewDayTaskUseCase(private val context: Context, ) {
    operator fun invoke() {
        val refreshCpnWork =
            PeriodicWorkRequest.Builder(UpdateEventWorker::class.java, 1, TimeUnit.DAYS)
                .setInitialDelay(calculateInitialDelay(), TimeUnit.MINUTES)
                .addTag("myWorkManager")
                .build()

        WorkManager.getInstance(context).enqueue(refreshCpnWork)
    }

    private fun calculateInitialDelay(): Long {
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()

        dueDate.set(Calendar.HOUR_OF_DAY, 8)
        dueDate.set(Calendar.MINUTE, 20)
        dueDate.set(Calendar.SECOND, 0)
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }

        return TimeUnit.MILLISECONDS.toMinutes(dueDate.timeInMillis - currentDate.timeInMillis)
    }
}