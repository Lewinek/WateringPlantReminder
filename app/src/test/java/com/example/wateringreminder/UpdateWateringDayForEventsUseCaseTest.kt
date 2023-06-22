package com.example.wateringreminder

import com.example.data_source.EventRepositoryImpl
import com.example.data_source.local.Event
import com.example.data_source.local.PlantCached
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class UpdateWateringDayForEventsUseCaseTest {

    fun generateEvents(): List<Event> {
        val events = mutableListOf<Event>()
        val currentDate = LocalDate.now()

        for (i in 1..5) {
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
    fun `GIVEN events WHEN invoke THEN getEvents should be called`() = runTest {
        val repository = mockk<EventRepositoryImpl>()
        coEvery { repository.getEventsToUpdate() } returns generateEvents()
        coEvery { repository.updateEvents(any()) } returns Unit

        val useCase = UpdateWateringDayForEventsUseCase(repository)
        useCase()

        coVerify { repository.getEventsToUpdate() }
    }

    @Test
    fun `GIVEN events WHEN invoke THEN updateEvents should be called`() = runTest {
        val repository = mockk<EventRepositoryImpl>()
        coEvery { repository.getEventsToUpdate() } returns generateEvents()
        coEvery { repository.updateEvents(any()) } returns Unit

        val useCase = UpdateWateringDayForEventsUseCase(repository)
        useCase()

        coVerify { repository.updateEvents(any()) }
    }

    @Test
    fun `GIVEN events WHEN invoke THEN updateEvents should be called with corrected arguments`() =
        runTest {
            // Arrange
            val events = listOf(
                Event(
                    id = 1,
                    wateringDate = LocalDate.parse("2023-03-29"),
                    isWatered = true,
                    recurringInterval = 7,
                    plantCached = PlantCached(
                        id = 2,
                        name = "Plant",
                        location = "Location"
                    )
                ),
                Event(
                    id = 2,
                    wateringDate = LocalDate.parse("2023-06-20"),
                    isWatered = true,
                    recurringInterval = 7,
                    plantCached = PlantCached(
                        id = 2,
                        name = "Plant",
                        location = "Location"
                    )
                )
            )

            val updatedEvents = listOf(
                Event(
                    id = 1,
                    wateringDate = LocalDate.parse("2023-06-28"),
                    isWatered = false,
                    recurringInterval = 7,
                    plantCached = PlantCached(
                        id = 2,
                        name = "Plant",
                        location = "Location"
                    )
                ),
                Event(
                    id = 2,
                    wateringDate = LocalDate.parse("2023-06-28"),
                    isWatered = false,
                    recurringInterval = 7,
                    plantCached = PlantCached(
                        id = 2,
                        name = "Plant",
                        location = "Location"
                    )
                )
            )

            val repository = mockk<EventRepositoryImpl>()
            coEvery { repository.getEventsToUpdate() } returns events

            val slot = slot<List<Event>>()
            coEvery { repository.updateEvents(capture(slot)) } just Runs

            val useCase = UpdateWateringDayForEventsUseCase(repository)

            // Act
            useCase()

            // Assert
            coVerify { repository.updateEvents(any()) }
            assertEquals(updatedEvents, slot.captured)
        }

    @Test
    fun `GIVEN zero events WHEN invoke THEN updateEvents should be called`() = runTest {
        val repository = mockk<EventRepositoryImpl>()
        coEvery { repository.getEventsToUpdate() } returns emptyList()
        coEvery { repository.updateEvents(any()) } returns Unit

        val useCase = UpdateWateringDayForEventsUseCase(repository)
        useCase()

        coVerify { repository.updateEvents(any()) }
    }

    @Test
    fun `GIVEN events WHEN invoke THEN updateWateringInfo should return updated arguments`() =
        runTest {
            val events = listOf(
                Event(
                    id = 1,
                    wateringDate = LocalDate.parse("2023-03-29"),
                    isWatered = true,
                    recurringInterval = 7,
                    plantCached = PlantCached(
                        id = 2,
                        name = "Plant",
                        location = "Location"
                    )
                ),
                Event(
                    id = 2,
                    wateringDate = LocalDate.parse("2023-06-23"),
                    isWatered = false,
                    recurringInterval = 7,
                    plantCached = PlantCached(
                        id = 2,
                        name = "Plant",
                        location = "Location"
                    )
                )
            )
            val expectedList = listOf(
                Event(
                    id = 1,
                    wateringDate = LocalDate.parse("2023-06-29"),
                    isWatered = false,
                    recurringInterval = 7,
                    plantCached = PlantCached(
                        id = 2,
                        name = "Plant",
                        location = "Location"
                    )
                )
            )
            val repository = mockk<EventRepositoryImpl>()
            coEvery { repository.getEventsToUpdate() } returns events
            val useCase = UpdateWateringDayForEventsUseCase(repository)
            val updatedList = useCase.updateOverdueWateringEvents(repository.getEventsToUpdate())

            assertEquals(updatedList, expectedList)
        }
}