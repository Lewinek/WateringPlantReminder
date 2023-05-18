package com.example.wateringreminder

import com.example.wateringreminder.myplants.MyPlantsViewModel
import com.example.wateringreminder.plantcreator.PlantCreationViewModel
import com.example.wateringreminder.watering.GetPlantNotificationUseCase
import com.example.wateringreminder.watering.WateringViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { WateringViewModel(get(), get()) }
    viewModel { PlantCreationViewModel(get(), get()) }
    viewModel { MyPlantsViewModel(get()) }

    factory { GetPlantNotificationUseCase(get()) }
}