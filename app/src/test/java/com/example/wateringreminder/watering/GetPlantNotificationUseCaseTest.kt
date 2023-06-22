@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.wateringreminder.watering

import com.example.data_source.EventRepositoryImpl
import com.example.data_source.local.Event
import com.example.data_source.local.PlantCached
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.time.LocalDate

class GetPlantNotificationUseCaseTest {

    private val rangeOfDays = 30

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

    private fun generateNotifications(): List<WaterThePlantNotification> {
        return generateEvents().flatMap { event ->
            val repeatedEvents = (1..(rangeOfDays / event.recurringInterval))
                .map { i ->
                    event.copy(
                        wateringDate = event.wateringDate.plusDays(event.recurringInterval.toLong() * i),
                        isWatered = false
                    )
                }
            listOf(event) + repeatedEvents.filter { it.wateringDate.isAfter(LocalDate.now()) }
        }
            .map { event ->
                WaterThePlantNotification(
                    eventId = event.id!!,
                    plant = event.plantCached,
                    date = event.wateringDate,
                    isWatered = event.isWatered
                )
            }
    }

    @Test
    fun `GIVEN zero events in repository WHEN invoke useCase THEN return zero notifications`() =
        runTest {

            val repository = mockk<EventRepositoryImpl> {
                coEvery {
                    getEvents()
                } returns emptyList()
            }
            val useCase = GetPlantNotificationUseCase(repository)
            val events = useCase()

            events.size shouldBe 0
        }

    @Test
    fun `GIVEN event with date earlier than range of days from today WHEN invoke useCase THEN return notification without repeated events`() =
        runTest {
            val repository = mockk<EventRepositoryImpl> {
                coEvery {
                    getEvents()
                } returns listOf(
                    Event(
                        id = 1,
                        wateringDate = LocalDate.parse("2023-03-29"),
                        isWatered = false,
                        recurringInterval = 7,
                        plantCached = PlantCached(
                            id = 2,
                            name = "Plant",
                            location = "Location"
                        )
                    )
                )
            }

            val useCase = GetPlantNotificationUseCase(repository)
            val events = useCase()

            events.size shouldBe 1
        }

    @Test
    fun `GIVEN event with date before today WHEN invoke useCase THEN return notification with repeated events`() =
        runTest {
            val repository = mockk<EventRepositoryImpl> {
                coEvery {
                    getEvents()
                } returns listOf(
                    Event(
                        id = 1,
                        wateringDate = LocalDate.parse("2023-05-28"),
                        isWatered = false,
                        recurringInterval = 7,
                        plantCached = PlantCached(
                            id = 2,
                            name = "Plant",
                            location = "Location"
                        )
                    )
                )
            }

            val useCase = GetPlantNotificationUseCase(repository)
            val events = useCase()

            events.size shouldBe 5
        }

    @Test
    fun `GIVEN event with date later than range of days from today WHEN invoke useCase THEN return notification without repeated events`() =
        runTest {
            val repository = mockk<EventRepositoryImpl> {
                coEvery {
                    getEvents()
                } returns listOf(
                    Event(
                        id = 1,
                        wateringDate = LocalDate.parse("2023-07-29"),
                        isWatered = false,
                        recurringInterval = 7,
                        plantCached = PlantCached(
                            id = 2,
                            name = "Plant",
                            location = "Location"
                        )
                    )
                )
            }

            val useCase = GetPlantNotificationUseCase(repository)
            val events = useCase()

            events.size shouldBe 5
        }

    @Test
    fun `GIVEN event with date after today WHEN invoke useCase THEN return notification with repeated events`() =
        runTest {
            val repository = mockk<EventRepositoryImpl> {
                coEvery {
                    getEvents()
                } returns listOf(
                    Event(
                        id = 1,
                        wateringDate = LocalDate.parse("2023-05-30"),
                        isWatered = false,
                        recurringInterval = 7,
                        plantCached = PlantCached(
                            id = 2,
                            name = "Plant",
                            location = "Location"
                        )
                    )
                )
            }

            val useCase = GetPlantNotificationUseCase(repository)
            val events = useCase()

            events.size shouldBe 5
        }

    @Test
    fun `GIVEN events in repository WHEN invoke useCase THEN return notifications`() = runTest {
        val repository = mockk<EventRepositoryImpl> {
            coEvery {
                getEvents()
            } returns generateEvents()
        }

        val useCase = GetPlantNotificationUseCase(repository)
        val events = useCase()

        events.size shouldBe generateNotifications().size
    }
}