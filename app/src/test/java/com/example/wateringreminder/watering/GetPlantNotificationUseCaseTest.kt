package com.example.wateringreminder.watering

import com.example.data_source.EventRepositoryImpl
import com.example.data_source.local.Event
import com.example.data_source.local.PlantCached
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.time.LocalDate

class GetPlantNotificationUseCaseTest() {

    fun generateEvents(): List<Event> {
        val events = mutableListOf<Event>()
        val currentDate = LocalDate.now()

        for (i in 1..10) {
            val event = Event(
                id = i,
                wateringDate = currentDate.plusDays(i.toLong()),
                isWatered = false,
                recurringInterval = 7,
                plantCached = PlantCached(
                    id = i,
                    name = "Plant $i",
                    location = "Location $i"
                )
            )
            events.add(event)
        }
        return events
    }

    @Test
    fun `GIVEN zero events in repository WHEN invoke usecase THEN return zero notifications`() =
        runTest {

            val repository = mockk<EventRepositoryImpl>() {
                coEvery {
                    getEvents()
                } returns emptyList()
            }
            val useCase: GetPlantNotificationUseCase = GetPlantNotificationUseCase(repository)
            val events = useCase()

            events.size shouldBe 0
        }
}