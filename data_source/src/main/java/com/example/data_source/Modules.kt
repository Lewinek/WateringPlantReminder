package com.example.data_source

import androidx.room.Room
import com.example.data_source.local.PlantDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataSourceModule = module {

    single {
        Room.databaseBuilder(androidContext(), PlantDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<PlantDatabase>().plantDao() }
    single { get<PlantDatabase>().eventDao() }

    factory<PlantRepository> { PlantRepositoryImpl(get()) }
    factory<EventRepository> { EventRepositoryImpl(get()) }
}