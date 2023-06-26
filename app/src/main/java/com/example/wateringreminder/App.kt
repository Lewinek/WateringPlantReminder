package com.example.wateringreminder

import android.app.Application
import com.example.data_source.dataSourceModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class App : Application() {
    val scheduleNewDayTaskUseCase: ScheduleNewDayTaskUseCase by inject()

    override fun onCreate() {
        super.onCreate()
        initKoin()
        scheduleNewDayTaskUseCase()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            workManagerFactory()
            modules(appModule, dataSourceModule)
        }
    }
}