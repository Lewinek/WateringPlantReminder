package com.example.data_source

import com.example.data_source.local.PlantCached
import com.example.data_source.local.PlantDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface PlantRepository {
    suspend fun insertPlant(plant: Plant)
    suspend fun getPlants(): Flow<List<Plant>>
}

class PlantRepositoryImpl(
    private val plantDao: PlantDao
) : PlantRepository {
    override suspend fun insertPlant(plant: Plant) {
        plantDao.insertPlant(PlantCached(plant))
    }

    override suspend fun getPlants(): Flow<List<Plant>> {
        return plantDao.getPlants().map { list -> list.map { it.toPlant() } }
    }
}