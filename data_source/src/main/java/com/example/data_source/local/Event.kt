package com.example.data_source.local

import androidx.room.*
import java.time.LocalDate

@Entity(tableName = "event", indices = [Index(value = ["id"], unique = true)])
data class Event(
    @PrimaryKey
    val id: Int? = null,
    val wateringDate: LocalDate,
    val isWatered: Boolean,
    val recurringInterval: Int,
    @Embedded(prefix = "plant")
    val plantCached: PlantCached,
)

@JvmInline
value class RecurringInterval(
    val numbersOfDay: Int
)


