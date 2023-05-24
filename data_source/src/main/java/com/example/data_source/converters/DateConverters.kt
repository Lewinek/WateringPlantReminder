package com.example.data_source.converters

import androidx.room.TypeConverter
import java.time.LocalDate

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