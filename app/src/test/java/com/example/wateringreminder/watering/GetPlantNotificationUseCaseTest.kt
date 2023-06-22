@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.wateringreminder.watering

import com.example.data_source.EventRepositoryImpl
import com.example.data_source.local.Event
import com.example.data_source.local.PlantCached
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

class GetPlantNotificationUseCaseTest {

    private val rangeOfDays: Long = 30
    private fun generateEvents(): Flow<List<Event>> {
        val currentDate = LocalDate.now()
        val events = (1..5).map { i ->
            Event(
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
        }
        return flowOf(events)
    }

    @Test
    fun `GIVEN events WHEN invoke THEN getEvents should be called`() {

        val fixture = Fixture().withEventsList()

        fixture.sut.invoke()

        fixture.verifyGetPlantsTriggered()
    }

    @Test
    fun `GIVEN zero events in repository WHEN invoke useCase THEN return zero notifications`() =
        runTest {
            //Prepare
            val fixture = Fixture().withEmptyList()

            //When
            val notifications = fixture.sut.invoke().toList().flatten()

            //Then
            notifications.size shouldBe 0
        }

    @Test
    fun `GIVEN event WHEN invoke useCase THEN return notification with repeated events`() =
        runTest {
            //Prepare
            val fixture = Fixture().withSingleEvent()
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

            //When
            val notifications = fixture.sut.invoke().toList().flatten()

            //Then
            Assertions.assertEquals(expectedNotifications, notifications)
        }

    @Test
    fun `GIVEN event with date earlier than range of days from today WHEN invoke useCase THEN return notification without repeated events`() =
        runTest {
            //Prepare
            val fixture =
                Fixture().withSingleEvent(wateringDate = LocalDate.now().minusDays(rangeOfDays))

            //When
            val notifications = fixture.sut.invoke().toList().flatten()

            //Then
            notifications.size shouldBe 1
        }

    @Test
    fun `GIVEN event with date before today WHEN invoke useCase THEN return notification with repeated events`() =
        runTest {
            //Prepare
            val fixture = Fixture().withSingleEvent(wateringDate = LocalDate.now().minusDays(2))

            //When
            val notifications = fixture.sut.invoke().toList().flatten()

            //Then
            notifications.size shouldBe 5
        }

    @Test
    fun `GIVEN event with date later than range of days from today WHEN invoke useCase THEN return notification without repeated events`() =
        runTest {
            //Prepare
            val fixture =
                Fixture().withSingleEvent(wateringDate = LocalDate.now().plusDays(rangeOfDays + 1))

            //When
            val notifications = fixture.sut.invoke().toList().flatten()

            //Then
            notifications.size shouldBe 1
        }

    @Test
    fun `GIVEN event with date after today WHEN invoke useCase THEN return notifications with repeated events`() =
        runTest {
            //Prepare
            val fixture = Fixture().withSingleEvent(wateringDate = LocalDate.now().plusDays(2))

            //When
            val notifications = fixture.sut.invoke().toList().flatten()

            //Then
            notifications.size shouldBe 4
        }

    @Test
    fun `GIVEN events in repository WHEN invoke useCase THEN return notifications`() =
        runTest {
            //Prepare
            val fixture = Fixture().withEventsList()

            //When
            val notifications = fixture.sut.invoke().toList().flatten()

            //Then
            notifications.size shouldBe 21
        }

    inner class Fixture {
        private val repository = mockk<EventRepositoryImpl>()

        val sut = GetPlantNotificationUseCase(repository)

        fun withEventsList() = apply {
            coEvery { repository.getEvents() } returns generateEvents()
        }

        fun withSingleEvent(wateringDate: LocalDate = LocalDate.now(), isWatered: Boolean = false) =
            apply {
                coEvery { repository.getEvents() } returns flowOf(
                    listOf(
                        Event(
                            id = 1,
                            wateringDate = wateringDate,
                            isWatered = isWatered,
                            recurringInterval = 7,
                            plantCached = PlantCached(
                                id = 1,
                                name = "Plant",
                                location = "Location"
                            )
                        )
                    )
                )
            }

        fun withEmptyList() = apply {
            coEvery { repository.getEvents() } returns flowOf(emptyList())
        }

        fun verifyGetPlantsTriggered() = coVerify { repository.getEvents() }
    }
}