package com.example.wateringreminder

import com.example.data_source.EventRepository
import java.time.LocalDate

class UpdateWateringDayForEvents(private val eventRepository: EventRepository) {

    suspend operator fun invoke() {
        val events = eventRepository.getEvents()
        events.filter {
            it.wateringDate.isBefore(LocalDate.now())
            it.isWatered
        }.map { it.copy(wateringDate = LocalDate.now().plusDays(it.recurringInterval.toLong())) }
        eventRepository.updateEvents(events)
    }
}