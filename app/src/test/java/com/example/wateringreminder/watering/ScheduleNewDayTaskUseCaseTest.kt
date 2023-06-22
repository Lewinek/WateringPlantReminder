package com.example.wateringreminder.watering

import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.wateringreminder.CalculateInitialDelayUseCase
import com.example.wateringreminder.ScheduleNewDayTaskUseCase
import io.kotest.matchers.collections.shouldContain
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.time.Duration

class ScheduleNewDayTaskUseCaseTest {
    @Test
    fun `GIVEN workManager WHEN invoke useCase THAN enqueue work request`() {
        val workManager = mockk<WorkManager>(relaxed = true)
        val calculateInitialDelayUseCase = mockk<CalculateInitialDelayUseCase>() {
            every { this@mockk.invoke() } returns Duration.ofMillis(0L)
        }

        val useCase = ScheduleNewDayTaskUseCase(workManager, calculateInitialDelayUseCase)

        useCase.invoke()

        verify {
            workManager.enqueue(withArg<OneTimeWorkRequest> {
                it.tags shouldContain "myWorkManager"
            })
        }
    }
}