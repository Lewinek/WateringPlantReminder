package com.example.data_source

import com.example.data_source.local.PlantCached
import com.example.data_source.local.PlantDao

interface PlantRepository {
    suspend fun insertPlant(plant: Plant)
    suspend fun getPlants(): List<Plant>
    suspend fun removePlant(plant: Plant)
}

class PlantRepositoryImpl(
    private val plantDao: PlantDao
) : PlantRepository {
    override suspend fun insertPlant(plant: Plant) {
        plantDao.insertPlant(PlantCached(plant))
    }

    override suspend fun getPlants(): List<Plant> {
        return plantDao.getPlants().map { it.toPlant() }
    }

    override suspend fun removePlant(plant: Plant) {
        plantDao.removePlant(plant = PlantCached(plant))
    }
}