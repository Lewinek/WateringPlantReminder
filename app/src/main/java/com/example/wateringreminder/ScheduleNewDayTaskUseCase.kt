package com.example.wateringreminder

import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.*
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlin.time.toJavaDuration


class ScheduleNewDayTaskUseCase(private val workManager: WorkManager) : WorkEnqueue {
    operator fun invoke() {
        val workRequest = OneTimeWorkRequestBuilder<UpdateEventWorker>()
            .setInitialDelay(calculateInitialDelay())
            .addTag("myWorkManager")
            .build()

        enqueue(workRequest)
    }

    private fun calculateInitialDelay(): java.time.Duration {
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 5)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }
        return (dueDate.timeInMillis - currentDate.timeInMillis).toDuration(DurationUnit.MILLISECONDS)
            .toJavaDuration()
    }

    override fun enqueue(workRequest: OneTimeWorkRequest) {
        workManager.enqueue(workRequest)
    }
}

interface WorkEnqueue {
    fun enqueue(workRequest: OneTimeWorkRequest)
}