package com.example.data_source.local

import androidx.room.Database

@Database(
    entities = [PlantCached::class],
    version = 1
)
abstract class PlantDatabase {
    abstract val plantDao: PlantDao
}