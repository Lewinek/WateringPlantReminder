package com.example.wateringreminder.watering

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
            plantsRepository.getPlants().collect {
                _state.value = state.value.copy(plants = it)
            }
        }
    }

    private fun getPlantsThatNeedWatering(){
        viewModelScope.launch {

        }
    }
}

data class PlantsState(
    val plants: List<Plant> = emptyList()
)