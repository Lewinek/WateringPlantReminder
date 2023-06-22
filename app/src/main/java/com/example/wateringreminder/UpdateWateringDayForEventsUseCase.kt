package com.example.wateringreminder

import com.example.data_source.EventRepository
import com.example.data_source.local.Event
import java.time.LocalDate

class UpdateWateringDayForEventsUseCase(private val eventRepository: EventRepository) {

    suspend operator fun invoke() {
        val events = eventRepository.getEvents()
        val updatedEvents = updateOverdueWateringEvents(events)
        eventRepository.updateEvents(updatedEvents)
    }

    fun updateOverdueWateringEvents(events: List<Event>): List<Event> {
        return events.filter {
            it.wateringDate.isBefore(LocalDate.now()) && it.isWatered
        }.map {
            it.copy(
                wateringDate = LocalDate.now().plusDays(it.recurringInterval.toLong()),
                isWatered = false
            )
        }
    }
}