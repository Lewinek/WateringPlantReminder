package com.example.wateringreminder

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.*
import java.util.concurrent.TimeUnit

class ScheduleNewDayTaskUseCase() {
    operator fun invoke() {
        val refreshCpnWork =
            PeriodicWorkRequest.Builder(UpdateEventWorker::class.java, 1, TimeUnit.DAYS)
                .setInitialDelay(calculateInitialDelay(), TimeUnit.MINUTES)
                .addTag("myWorkManager")
                .build()

        WorkManager.getInstance()
            .enqueueUniquePeriodicWork(
                "myWorkManager",
                ExistingPeriodicWorkPolicy.UPDATE,
                refreshCpnWork
            )
    }

    private fun calculateInitialDelay(): Long {
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()

        dueDate.set(Calendar.HOUR_OF_DAY, 18)
        dueDate.set(Calendar.MINUTE, 51)
        dueDate.set(Calendar.SECOND, 0)
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }

        return TimeUnit.MILLISECONDS.toMinutes(dueDate.timeInMillis - currentDate.timeInMillis)
    }
}