package com.example.wateringreminder

import com.example.data_source.EventRepository
import java.time.LocalDate

class UpdateWateringDayForEventsUseCase(private val eventRepository: EventRepository) {

    suspend operator fun invoke() {
        val events = eventRepository.getEvents()
        events.filter {
            it.wateringDate.isBefore(LocalDate.now())
            it.isWatered
        }.map {
            it.copy(
                wateringDate = LocalDate.now().plusDays(it.recurringInterval.toLong()),
                isWatered = false
            )
        }
        eventRepository.updateEvents(events)
    }
}