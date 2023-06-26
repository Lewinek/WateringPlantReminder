package com.example.wateringreminder.myplants

import com.example.data_source.EventRepositoryImpl
import com.example.data_source.Plant
import com.example.data_source.PlantRepositoryImpl
import com.example.wateringreminder.ViewModelTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MyPlantsViewModelTest : ViewModelTest() {

    @Test
    fun `GIVEN plants are successfully retrieved WHEN getPlants() is called THEN update uiState with retrieved plants`() =
        runTest {
            //Prepare
            val expectedPlantList = listOf(
                Plant(id = 1, name = "Szyszka", location = "kitchen")
            )
            val fixture = Fixture().withPlantList()
            val sut = MyPlantsViewModel(fixture.plantRepository, fixture.eventRepository)

            //When
            sut.getPlants()

            //Then
            val uiStateValue = sut.uiState.value
            assertEquals(expectedPlantList, uiStateValue.plants)
        }

    @Test
    fun `GIVEN empty plant list WHEN getPlants() is called THEN update uiState with retrieved plants`() =
        runTest {
            //Prepare
            val expectedPlantList = emptyList<Plant>()
            val fixture = Fixture().withEmptyPlantList()
            val sut = MyPlantsViewModel(fixture.plantRepository, fixture.eventRepository)

            //When
            sut.getPlants()

            //Then
            val uiStateValue = sut.uiState.value
            assertEquals(expectedPlantList, uiStateValue.plants)
        }

    @Test
    fun `GIVEN plant and corresponding event WHEN removePlant() is called THEN remove plant and event`() =
        runTest {
            //Prepare
            val fixture = Fixture()
            val sut = MyPlantsViewModel(fixture.plantRepository, fixture.eventRepository)
            val plant = Plant(1, "Plant 1")
            coEvery { fixture.plantRepository.removePlant(any()) } returns Unit
            coEvery { fixture.eventRepository.removeEvent(any()) } returns Unit

            //When
            sut.removePlant(plant)

            //Then
            coVerify { fixture.plantRepository.removePlant(plant) }
            coVerify { plant.id?.let { fixture.eventRepository.removeEvent(it) } }
        }

    @Test
    fun `GIVEN a plant without an ID WHEN removePlant() is called THEN only remove plant, do not remove event`() =
        runTest {
            //Prepare
            val fixture = Fixture()
            val sut = MyPlantsViewModel(fixture.plantRepository, fixture.eventRepository)
            val plant = Plant(name = "Kasztan")
            coEvery { fixture.plantRepository.removePlant(any()) } returns Unit
            coEvery { fixture.eventRepository.removeEvent(any()) } returns Unit

            //When
            sut.removePlant(plant)

            //Then
            coVerify { fixture.plantRepository.removePlant(plant) }
            coVerify(exactly = 0) { fixture.eventRepository.removeEvent(any()) }
        }

    inner class Fixture {
        val eventRepository = mockk<EventRepositoryImpl>()
        val plantRepository = mockk<PlantRepositoryImpl>()

        fun withPlantList() = apply {
            coEvery { plantRepository.getPlants() } returns flowOf(
                listOf(
                    Plant(id = 1, name = "Szyszka", location = "kitchen")
                )
            )
        }

        fun withEmptyPlantList() = apply {
            coEvery { plantRepository.getPlants() } returns flowOf(emptyList())
        }
    }
}