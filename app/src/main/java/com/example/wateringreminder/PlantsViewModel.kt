package com.example.wateringreminder

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_source.Plant
import com.example.data_source.PlantRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PlantsViewModel(
    private val plantsRepository: PlantRepository
) : ViewModel() {

    private val _state = mutableStateOf(PlantsState())
    val state: State<PlantsState> = _state

    private var getPlantsJob: Job? = null

    init {
        getPlants()
    }

    private fun getPlants() {
        getPlantsJob?.cancel()
        getPlantsJob = viewModelScope.launch {
            plantsRepository.getPlants()
                .onEach { plants ->
                    _state.value = state.value.copy(plants = plants)
                }
        }
    }
}

data class PlantsState(
    val plants: List<Plant> = emptyList()
)