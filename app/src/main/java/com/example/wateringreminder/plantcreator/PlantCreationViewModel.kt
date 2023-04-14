package com.example.wateringreminder.plantcreator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_source.Plant
import com.example.data_source.PlantRepository
import kotlinx.coroutines.launch

class PlantCreationViewModel(
    private val plantRepository: PlantRepository
) : ViewModel() {

    private var plantName by mutableStateOf("")
    private var location by mutableStateOf("")
    var index = mutableStateOf(-1)

    fun createPlant() {
        viewModelScope.launch {
            plantRepository.insertPlant(Plant(name = plantName))
        }
    }

    fun updatePlantName(name: String) {
        plantName = name
    }

    fun updatePlantLocation(location: String) {
        this.location = location
    }

    fun updateIndex(index: Int) {
        this.index.value = index
    }
}