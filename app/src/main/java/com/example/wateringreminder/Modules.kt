package com.example.wateringreminder

import androidx.work.WorkManager
import com.example.wateringreminder.myplants.MyPlantsViewModel
import com.example.wateringreminder.plantcreator.PlantCreationViewModel
import com.example.wateringreminder.watering.GetPlantNotificationUseCase
import com.example.wateringreminder.watering.WateringViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val appModule = module {
    viewModel { WateringViewModel(get(), get()) }
    viewModel { PlantCreationViewModel(get(), get()) }
    viewModel { MyPlantsViewModel(get(), get()) }

    factory { GetPlantNotificationUseCase(get()) }
    factory { ScheduleNewDayTaskUseCase(get(), get()) }
    factory { UpdateWateringDayForEventsUseCase(get()) }
    factory { CalculateInitialDelayUseCase() }

    worker { UpdateEventWorker(androidContext(), get()) }
    single { WorkManager.getInstance(get()) }
}