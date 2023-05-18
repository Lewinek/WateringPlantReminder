package com.example.data_source.local

import androidx.room.*
import java.time.LocalDate

@Entity(tableName = "event", indices = [androidx.room.Index(value = ["id"], unique = true)])
data class Event(
    @PrimaryKey
    val id: Int? = null,
    val startDate: LocalDate,
    val lastWaterDay: LocalDate,
    val recurringInterval: Int,
    @Embedded(prefix = "plant")
    val plantCached: PlantCached,
)

@JvmInline
value class RecurringInterval(
    val numbersOfDay: Int
)

class LocalDateConverter {
    @TypeConverter
    fun timeToString(time: LocalDate): String {
        return time.toString()
    }

    @TypeConverter
    fun stringToTime(string: String): LocalDate {
        return LocalDate.parse(string)
    }
}


