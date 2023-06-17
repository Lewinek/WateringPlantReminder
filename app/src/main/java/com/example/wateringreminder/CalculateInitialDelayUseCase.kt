package com.example.wateringreminder

import java.util.*
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlin.time.toJavaDuration

class CalculateInitialDelayUseCase {
    operator fun invoke() = calculateInitialDelay()

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
}