package com.example.wateringreminder.plantcreator

import com.example.data_source.EventRepository
import com.example.data_source.Plant
import com.example.data_source.PlantRepository
import com.example.data_source.local.PlantCached
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class CreatePlantUseCaseTest {

    @Test
    fun `GIVEN successful plant creation WHEN invoke THEN results should be true`() = runTest {
        val plantRepository = mockk<PlantRepository>()
        val eventRepository = mockk<EventRepository>()
        val givenId = 1L
        val plant = Plant(null, name = "Plant", location = "Location")
        coEvery { plantRepository.insertPlant(any()) } returns givenId
        coEvery { plantRepository.getPlantById(givenId.toInt()) } returns PlantCached(
            id = 1,
            name = "Plant",
            location = "Location"
        )
        coEvery { eventRepository.insertEvent(any()) } returns Unit

        val useCase = CreatePlantUseCase(eventRepository, plantRepository)

        val result = useCase.invoke(plant)

        result shouldBe true
    }
}