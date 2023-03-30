package com.example.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PlantCached::class],
    version = 1
)
abstract class PlantDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao
}