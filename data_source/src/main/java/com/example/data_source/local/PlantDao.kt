package com.example.data_source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {

    @Query("SELECT * FROM plantCached")
    fun getPlants(): Flow<List<PlantCached>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plantCached: PlantCached)
}