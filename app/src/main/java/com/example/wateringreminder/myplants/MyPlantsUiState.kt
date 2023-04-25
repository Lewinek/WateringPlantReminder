package com.example.wateringreminder.myplants

import com.example.data_source.Plant

data class MyPlantsUiState(
    val plants: List<Plant> = emptyList()
)
