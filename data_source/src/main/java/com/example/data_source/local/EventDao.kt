package com.example.data_source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Query("SELECT * FROM `event`")
    suspend fun getEvents(): List<Event>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvent(vararg event: Event)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateEvent(vararg event: Event)

    @Update
    suspend fun updateEvents(events: List<Event>)
}