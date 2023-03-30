package com.example.data_source.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {

    @Query("SELECT * FROM plantCached")
    fun getPlants(): Flow<List<PlantCached>>
}