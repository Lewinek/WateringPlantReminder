package com.example.wateringreminder

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class UpdateEventWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {
    private val updateWateringDayForEvents: UpdateWateringDayForEvents by inject()
    override suspend fun doWork(): Result {
        withContext(Dispatchers.IO) {
            updateWateringDayForEvents()
        }
        return Result.success()
    }
}