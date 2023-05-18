package com.example.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [PlantCached::class, Event::class],
    version = 9
)
@TypeConverters(LocalDateConverter::class)
abstract class PlantDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao

    abstract fun eventDao(): EventDao
}