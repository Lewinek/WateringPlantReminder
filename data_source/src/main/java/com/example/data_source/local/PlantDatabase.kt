package com.example.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PlantCached::class, Event::class],
    version = 8
)
abstract class PlantDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao

    abstract fun eventDao(): EventDao
}