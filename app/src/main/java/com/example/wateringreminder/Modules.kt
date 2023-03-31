package com.example.wateringreminder

import com.example.wateringreminder.plantcreator.PlantCreationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { PlantsViewModel(get()) }
    viewModel { PlantCreationViewModel(get()) }
}