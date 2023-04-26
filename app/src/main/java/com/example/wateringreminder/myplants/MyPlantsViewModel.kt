package com.example.wateringreminder.myplants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_source.Plant
import com.example.data_source.PlantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyPlantsViewModel(
    private val repository: PlantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyPlantsUiState())
    val uiState: StateFlow<MyPlantsUiState> = _uiState.asStateFlow()

    init {
        getPlants()
    }

    fun getPlants() {
        viewModelScope.launch {
            _uiState.update { uiState -> uiState.copy(plants = repository.getPlants()) }
        }
    }

    fun removePlant(plant: Plant) {
        viewModelScope.launch { repository.removePlant(plant) }
    }
}