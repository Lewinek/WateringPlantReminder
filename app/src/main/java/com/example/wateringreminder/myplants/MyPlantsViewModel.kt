package com.example.wateringreminder.myplants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_source.PlantRepository
import kotlinx.coroutines.launch

class MyPlantsViewModel(
    private val repository: PlantRepository
) : ViewModel() {

    init {
        getPlants()
    }

    private fun getPlants() {
        viewModelScope.launch {
            val plants = repository.getPlants()
        }
    }
}