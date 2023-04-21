package com.example.wateringreminder

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_source.Plant
import com.example.data_source.PlantRepository
import kotlinx.coroutines.launch

class WateringViewModel(
    private val plantsRepository: PlantRepository
) : ViewModel() {

    private val _state = mutableStateOf(PlantsState())
    val state: State<PlantsState> = _state

    init {
        addPlants()
        getPlants()
    }

    private fun addPlants() {
        viewModelScope.launch {
            plantsRepository.insertPlant(Plant(name = "Irys"))
            plantsRepository.insertPlant(Plant(name = "Bratek"))
            plantsRepository.insertPlant(Plant(name = "Dracena"))
            plantsRepository.insertPlant(Plant(name = "Sukulent"))
        }
    }

    private fun getPlants() {
        viewModelScope.launch {
            _state.value = state.value.copy(plants = plantsRepository.getPlants())
        }
    }
}

data class PlantsState(
    val plants: List<Plant> = emptyList()
)