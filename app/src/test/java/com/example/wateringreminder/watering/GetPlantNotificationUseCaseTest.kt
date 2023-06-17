@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.wateringreminder.watering

import com.example.data_source.EventRepositoryImpl
import com.example.data_source.local.Event
import com.example.data_source.local.PlantCached
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class GetPlantNotificationUseCaseTest {

    private val rangeOfDays: Long = 30
    private fun generateEvents(): Flow<List<Event>> {
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
        return flowOf(events)
    }

    @Test
    fun `GIVEN zero events in repository WHEN invoke useCase THEN return zero notifications`() =
        runTest {
            val repository = mockk<EventRepositoryImpl> {
                coEvery {
                    getEvents()
                } returns flowOf(emptyList())
            }
            val useCase = GetPlantNotificationUseCase(repository)
            val events = useCase().toList().flatten()

            events.size shouldBe 0
        }

    @Test
    fun `GIVEN event WHEN invoke useCase THEN return  notification with repeated events`() =
        runTest {
            val events = listOf(
                Event(
                    id = 1,
                    wateringDate = LocalDate.now(),
                    isWatered = false,
                    recurringInterval = 7,
                    plantCached = PlantCached(
                        id = 1,
                        name = "Plant",
                        location = "Location"
                    )
                )
            )

            val repository = mockk<EventRepositoryImpl> {
                coEvery {
                    getEvents()
                } returns flowOf(events)
            }

            val useCase = GetPlantNotificationUseCase(repository)
            val result = useCase.invoke().toList().flatten()

            val expectedNotifications = listOf(
                WaterThePlantNotification(
                    eventId = 1,
                    plant = PlantCached(id = 1, name = "Plant", location = "Location"),
                    date = LocalDate.now(),
                    isWatered = false
                ),
                WaterThePlantNotification(
                    eventId = 1,
                    plant = PlantCached(id = 1, name = "Plant", location = "Location"),
                    date = LocalDate.now().plusDays(7),
                    isWatered = false
                ),
                WaterThePlantNotification(
                    eventId = 1,
                    plant = PlantCached(id = 1, name = "Plant", location = "Location"),
                    date = LocalDate.now().plusDays(14),
                    isWatered = false
                ),
                WaterThePlantNotification(
                    eventId = 1,
                    plant = PlantCached(id = 1, name = "Plant", location = "Location"),
                    date = LocalDate.now().plusDays(21),
                    isWatered = false
                ),
                WaterThePlantNotification(
                    eventId = 1,
                    plant = PlantCached(id = 1, name = "Plant", location = "Location"),
                    date = LocalDate.now().plusDays(28),
                    isWatered = false
                )
            )

            assertEquals(expectedNotifications, result)
        }

    @Test
    fun `GIVEN event with date earlier than range of days from today WHEN invoke useCase THEN return notification without repeated events`() =
        runTest {
            val repository = mockk<EventRepositoryImpl> {
                coEvery {
                    getEvents()
                } returns flowOf(
                    listOf(
                        Event(
                            id = 1,
                            wateringDate = LocalDate.now().minusDays(rangeOfDays),
                            isWatered = false,
                            recurringInterval = 7,
                            plantCached = PlantCached(
                                id = 2,
                                name = "Plant",
                                location = "Location"
                            )
                        )
                    )
                )
            }

            val useCase = GetPlantNotificationUseCase(repository)
            val events = useCase().toList().flatten()

            events.size shouldBe 1
        }

    @Test
    fun `GIVEN event with date before today WHEN invoke useCase THEN return notification with repeated events`() =
        runTest {
            val repository = mockk<EventRepositoryImpl> {
                coEvery {
                    getEvents()
                } returns flowOf(
                    listOf(
                        Event(
                            id = 1,
                            wateringDate = LocalDate.now().minusDays(2),
                            isWatered = false,
                            recurringInterval = 7,
                            plantCached = PlantCached(
                                id = 2,
                                name = "Plant",
                                location = "Location"
                            )
                        )
                    )
                )
            }

            val useCase = GetPlantNotificationUseCase(repository)
            val events = useCase().toList().flatten()

            events.size shouldBe 5
        }

    @Test
    fun `GIVEN event with date later than range of days from today WHEN invoke useCase THEN return notification without repeated events`() =
        runTest {
            val repository = mockk<EventRepositoryImpl> {
                coEvery {
                    getEvents()
                } returns flowOf(
                    listOf(
                        Event(
                            id = 1,
                            wateringDate = LocalDate.now().plusDays(rangeOfDays),
                            isWatered = false,
                            recurringInterval = 7,
                            plantCached = PlantCached(
                                id = 2,
                                name = "Plant",
                                location = "Location"
                            )
                        )
                    )
                )
            }

            val useCase = GetPlantNotificationUseCase(repository)
            val events = useCase().toList().flatten()

            events.size shouldBe 5
        }

    @Test
    fun `GIVEN event with date after today WHEN invoke useCase THEN return notification with repeated events`() =
        runTest {
            val repository = mockk<EventRepositoryImpl> {
                coEvery {
                    getEvents()
                } returns flowOf(
                    listOf(
                        Event(
                            id = 1,
                            wateringDate = LocalDate.now().plusDays(5),
                            isWatered = false,
                            recurringInterval = 7,
                            plantCached = PlantCached(
                                id = 2,
                                name = "Plant",
                                location = "Location"
                            )
                        )
                    )
                )
            }

            val useCase = GetPlantNotificationUseCase(repository)
            val events = useCase().toList().flatten()

            events.size shouldBe 5
        }

    @Test
    fun `GIVEN events in repository WHEN invoke useCase THEN return notifications`() =
        runTest {
            val repository = mockk<EventRepositoryImpl> {
                coEvery {
                    getEvents()
                } returns generateEvents()
            }

            val useCase = GetPlantNotificationUseCase(repository)
            val events = useCase().toList().flatten()

            events.size shouldBe 25
        }
}