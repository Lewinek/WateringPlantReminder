package com.example.data_source

import com.example.data_source.local.Event
import com.example.data_source.local.EventDao
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    suspend fun insertEvent(event: Event)
    fun getEvents(): Flow<List<Event>>
}

class EventRepositoryImpl(
    private val eventDao: EventDao
) : EventRepository {
    override suspend fun insertEvent(event: Event) {
        eventDao.insertEvent(event)
    }

    override fun getEvents(): Flow<List<Event>> {
        return eventDao.getEvents()
    }
}
