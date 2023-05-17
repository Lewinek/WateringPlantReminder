package com.example.data_source

import com.example.data_source.local.PlantCached
import com.example.data_source.local.PlantDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface PlantRepository {
    suspend fun insertPlant(plant: Plant): Long
    fun getPlants(): Flow<List<Plant>>
    suspend fun removePlant(plant: Plant)
    suspend fun getPlantById(id: Int): PlantCached
}

class PlantRepositoryImpl(
    private val plantDao: PlantDao
) : PlantRepository {
    override suspend fun insertPlant(plant: Plant) : Long{
        return plantDao.insertPlant(PlantCached(plant))
    }

    override fun getPlants(): Flow<List<Plant>> {
        return plantDao.getPlants().map { it -> it.map { it.toPlant() } }
    }

    override suspend fun removePlant(plant: Plant) {
        plantDao.removePlant(plant = PlantCached(plant))
    }

    override suspend fun getPlantById(id: Int): PlantCached {
        return plantDao.getPlantById(id)
    }
}