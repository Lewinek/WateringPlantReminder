package com.example.wateringreminder.watering

import com.example.data_source.EventRepository
import com.example.data_source.local.Event
import kotlinx.coroutines.flow.*
import java.time.LocalDate

class GetPlantNotificationUseCase(private val repository: EventRepository) {
    private val rangeOfDays = 30

    operator fun invoke(): Flow<List<WaterThePlantNotification>> {
        return repository.getEvents()
            .flatMapConcat { events ->
                flow {
                    val resultList = mutableListOf<WaterThePlantNotification>()
                    for (event in events) {
                        val repeatedEvents = (1..(rangeOfDays / event.recurringInterval))
                            .map { i ->
                                event.copy(
                                    wateringDate = event.wateringDate.plusDays(event.recurringInterval.toLong() * i),
                                    isWatered = false
                                )
                            }.filter {
                                it.wateringDate.isAfter(LocalDate.now())
                            }
                        val filteredEvents = listOf(event) + repeatedEvents
                        val mappedNotifications = filteredEvents.map { mapToNotification(it) }
                        resultList.addAll(mappedNotifications)
                    }
                    emit(resultList)
                }
            }
    }

    private fun mapToNotification(event: Event): WaterThePlantNotification {
        return WaterThePlantNotification(
            eventId = event.id!!,
            plant = event.plantCached,
            date = event.wateringDate,
            isWatered = event.isWatered
        )
    }
}