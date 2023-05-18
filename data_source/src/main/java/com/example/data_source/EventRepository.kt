package com.example.data_source

import com.example.data_source.local.Event
import com.example.data_source.local.EventDao

interface EventRepository {
    suspend fun insertEvent(event: Event)
    suspend fun getEvents(): List<Event>
    suspend fun updateEvent(event: Event)
}

class EventRepositoryImpl(
    private val eventDao: EventDao
) : EventRepository {
    override suspend fun insertEvent(event: Event) {
        eventDao.insertEvent(event)
    }

    override suspend fun getEvents(): List<Event> {
        return eventDao.getEvents()
    }

    override suspend fun updateEvent(event: Event) {
        eventDao.updateEvent(event)
    }
}
