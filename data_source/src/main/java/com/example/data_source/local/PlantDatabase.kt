package com.example.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [PlantCached::class, Event::class],
    version = 8
)
@TypeConverters(LocalDateConverter::class)
abstract class PlantDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao

    abstract fun eventDao(): EventDao
}