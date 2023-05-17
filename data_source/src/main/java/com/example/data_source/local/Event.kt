package com.example.data_source.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "event")
data class Event(
    @PrimaryKey
    val id: Int? = null,
    val startDate: LocalDate,
    val recurringInterval: RecurringInterval,
    val plantCached: PlantCached,
)

@JvmInline
value class RecurringInterval(
    val numbersOfDay: Int
)


