package com.example.wateringreminder.utils.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.example.wateringreminder.R
import com.example.wateringreminder.watering.WaterThePlantNotification

@Composable
fun List<WaterThePlantNotification>.displayRemainingPlantsToWater(): String {
    val listSize = this.count { !it.isWatered }
    return if (listSize == 0) {
        stringResource(R.string.label_done)
    } else {
        pluralStringResource(R.plurals.remaining_plants, listSize, listSize)
    }
}
