package com.example.wateringreminder.watering

import com.example.data_source.EventRepository

class GetPlantNotificationUseCase(private val repository: EventRepository) {

    val range: Int = 30
    val numbersOfDay: Int = 5
    val repeatedDay = range / numbersOfDay

    suspend operator fun invoke(): List<WaterThePlantNotification> {
        return repository.getEvents().flatMap { event ->
            val repeatedEvents = (1..repeatedDay).map { i ->
                event.copy(
                    wateringDate = event.wateringDate.plusDays(numbersOfDay.toLong() * i),
                    isWatered = false
                )
            }
            listOf(event) + repeatedEvents
        }.map {
            WaterThePlantNotification(
                eventId = it.id!!,
                plant = it.plantCached,
                date = it.wateringDate,
                isWatered = it.isWatered
            )
        }
    }
}