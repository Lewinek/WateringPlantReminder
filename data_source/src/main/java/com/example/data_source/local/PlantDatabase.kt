package com.example.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data_source.converters.LocalDateConverter

@Database(
    entities = [PlantCached::class, Event::class],
    version = 14
)
@TypeConverters(LocalDateConverter::class)
abstract class PlantDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao

    abstract fun eventDao(): EventDao
}