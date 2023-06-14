package com.example.wateringreminder

import com.example.data_source.EventRepository
import com.example.data_source.local.Event
import kotlinx.coroutines.flow.*
import java.time.LocalDate

class UpdateWateringDayForEvents(private val eventRepository: EventRepository) {

    suspend operator fun invoke() {
        val events: Flow<List<Event>> = eventRepository.getEvents().flatMapMerge { events ->
            flow {
                emit(events.filter {
                    it.wateringDate.isBefore(LocalDate.now())
                    it.isWatered
                }.map {
                    it.copy(
                        wateringDate = LocalDate.now().plusDays(it.recurringInterval.toLong()),
                        isWatered = false
                    )
                })
            }
        }
        events.collect { events -> eventRepository.updateEvents(events) }
    }
}