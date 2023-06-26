package com.example.wateringreminder

import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager


class ScheduleNewDayTaskUseCase(
    private val workManager: WorkManager,
    private val calculateInitialDelayUseCase: CalculateInitialDelayUseCase
) : WorkEnqueue {
    operator fun invoke() {
        val workRequest = OneTimeWorkRequestBuilder<UpdateEventWorker>()
            .setInitialDelay(calculateInitialDelayUseCase())
            .addTag("myWorkManager")
            .build()
        workManager.enqueue(workRequest)
    }

    override fun enqueue(workRequest: OneTimeWorkRequest) {
        workManager.enqueue(workRequest)
    }
}

interface WorkEnqueue {
    fun enqueue(workRequest: OneTimeWorkRequest)
}