package com.example.wateringreminder.compose.plantcreator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_source.Plant
import com.example.data_source.PlantRepository
import kotlinx.coroutines.launch

class PlantCreationViewModel(
    private val plantRepository: PlantRepository
) : ViewModel() {

    fun createPlant(name: String) {
        viewModelScope.launch {
            plantRepository.insertPlant(Plant(name = name))
        }
    }
}