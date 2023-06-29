package com.example.wateringreminder.plantcreator

import com.example.data_source.EventRepository
import com.example.data_source.Plant
import com.example.data_source.PlantRepository
import com.example.data_source.local.Event
import com.example.data_source.local.PlantCached
import java.time.LocalDate

class CreatePlantUseCase(
    private val eventRepository: EventRepository,
    private val plantRepository: PlantRepository,
) {

    suspend operator fun invoke(plant: Plant): Boolean {
        val plantId = plantRepository.insertPlant(plant)
        return if (plantId != -1L) {
            val plantCached = plantRepository.getPlantById(plantId.toInt())
            eventRepository.insertEvent(createEvent(plantCached))
            true
        } else {
            false
        }
    }

    private fun createEvent(plant: PlantCached): Event {
        return Event(
            wateringDate = LocalDate.now(),
            recurringInterval = 1,
            plantCached = plant,
            isWatered = false
        )
    }
}
