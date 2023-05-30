package com.example.wateringreminder

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.*
import java.util.concurrent.TimeUnit

class ScheduleNewDayTaskUseCase(private val workManager: WorkManager) : WorkEnqueue {
    operator fun invoke() {
        val refreshCpnWork = PeriodicWorkRequest.Builder(
            UpdateEventWorker::class.java, 1, TimeUnit.DAYS)
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MINUTES)
            .addTag("myWorkManager")
            .build()

        enqueue(refreshCpnWork)
    }

    fun calculateInitialDelay(): Long {
        val dueDate = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 20)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.HOUR_OF_DAY, 24)
            }
        }

        return TimeUnit.MILLISECONDS.toMinutes(dueDate.timeInMillis - System.currentTimeMillis())
    }

    override fun enqueue(workRequest: PeriodicWorkRequest) {
        workManager.enqueueUniquePeriodicWork(
            "myWorkManager",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}

interface WorkEnqueue {
    fun enqueue(workRequest: PeriodicWorkRequest)
}