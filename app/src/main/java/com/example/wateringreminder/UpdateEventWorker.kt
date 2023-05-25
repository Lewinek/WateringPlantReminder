package com.example.wateringreminder

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class UpdateEventWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        updateEvents()
        return Result.success()
    }

    private fun updateEvents() {
    }
}