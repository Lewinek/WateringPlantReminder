package com.example.wateringreminder

import com.example.data_source.EventRepository
import com.example.data_source.local.Event
import java.time.LocalDate

class UpdateWateringDayForEvents(private val eventRepository: EventRepository) {

    suspend operator fun invoke() {
        val events: List<Event> = eventRepository.getEventsToUpdate().filter {
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