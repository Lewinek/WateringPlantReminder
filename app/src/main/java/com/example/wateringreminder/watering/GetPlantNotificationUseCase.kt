package com.example.wateringreminder.watering

import com.example.data_source.EventRepository
import java.time.LocalDate

class GetPlantNotificationUseCase(private val repository: EventRepository) {
    private val rangeOfDays = 30

    suspend operator fun invoke(): List<WaterThePlantNotification> {
        return repository.getEvents()
            .flatMap { event ->
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
}