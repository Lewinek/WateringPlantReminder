package com.example.data_source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {

    @Query("SELECT * FROM `plant`")
    fun getPlants(): Flow<List<PlantCached>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlant( plantCached: PlantCached) : Long

    @Delete
    suspend fun removePlant(plant: PlantCached)

    @Query("SELECT * FROM plant WHERE id = :id")
    suspend fun getPlantById(id: Int): PlantCached
}