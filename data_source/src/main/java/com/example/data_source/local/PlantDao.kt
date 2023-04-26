package com.example.data_source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {

    @Query("SELECT * FROM `plant`")
    suspend fun getPlants(): List<PlantCached>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlant(vararg plantCached: PlantCached)

    @Delete
    suspend fun removePlant(plant: PlantCached)
}