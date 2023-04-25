package com.example.wateringreminder.myplants

import androidx.lifecycle.ViewModel
import com.example.data_source.PlantRepository

class MyPlantsViewModel(
    private val repository: PlantRepository
) : ViewModel() {
}