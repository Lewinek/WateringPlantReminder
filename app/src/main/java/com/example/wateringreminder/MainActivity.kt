package com.example.wateringreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.wateringreminder.ui.theme.WateringReminderTheme
import org.koin.android.ext.android.inject

class MainActivity() : ComponentActivity() {
    val scheduleNewDayTaskUseCase: ScheduleNewDayTaskUseCase by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleNewDayTaskUseCase()
        setContent {
            WateringReminderTheme {
                MainScreen()
            }
        }
    }
}