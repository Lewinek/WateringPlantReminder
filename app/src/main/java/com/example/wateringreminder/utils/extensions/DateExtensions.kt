package com.example.wateringreminder.utils.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.wateringreminder.R
import com.example.wateringreminder.utils.constants.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

val formatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern(DateFormat.FORMATTER).withLocale(Locale.ENGLISH)

@Composable
fun LocalDate.toDateDisplay(): String =
    if (isToday()) stringResource(R.string.label_water_day) else format(formatter)

fun LocalDate.isToday() = isEqual(LocalDate.now())
