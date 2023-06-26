package com.example.wateringreminder.watering

import com.example.wateringreminder.R

data class WateringUiState(
    var showNameError: Boolean = false,
    var errorNameMsg: Int = R.string.empty_string,
    var plantName: String = "",
    var location: String = "",
    val daysIntervalForSelection: List<String> = listOf("mon", "fri", "sun"),
    var selectedDay: String? = null,
)
